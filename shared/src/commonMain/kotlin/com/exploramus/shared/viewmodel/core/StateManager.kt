package com.exploramus.shared.viewmodel.core

import com.exploramus.core.common.logging.Log
import com.exploramus.data.repository.Repository
import com.exploramus.shared.viewmodel.appstate.AppEnvironment
import com.exploramus.shared.viewmodel.appstate.AppStartupState
import com.exploramus.shared.viewmodel.screens.settings.builder.SettingsBuilder
import com.exploramus.shared.viewmodel.screens.settings.builder.SettingsCategory
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.reflect.KClass

interface ScreenState

class StateManager(repo: Repository) {
    val screenStatesMap : MutableMap<URI,MutableStateFlow<ScreenState>> = mutableMapOf() // map of screen states currently in memory
    val screenScopesMap : MutableMap<URI,CoroutineScope> = mutableMapOf() // map of coroutine scopes associated to current screen states
    val screenIdentifiersMap : MutableMap<URI,ScreenIdentifier> = mutableMapOf() // map of screen identifiers currently in memory

    val level1Backstack: MutableList<ScreenIdentifier> = mutableListOf() // list elements are only NavigationLevel1 screenIdentifiers
    val currentVerticalBackstack: MutableList<ScreenIdentifier> = mutableListOf() // list elements are the screenIdentifiers of the current vertical backstack
    val verticalNavigationLevels : MutableMap<URI,MutableMap<Int, ScreenIdentifier>> = mutableMapOf() // the first map key is the NavigationLevel1 screenIdentifier URI, the second map key is the NavigationLevel numbers
    
    val currentLevel1ScreenIdentifier: ScreenIdentifier?
        get() = level1Backstack.lastOrNull()
        
    val currentScreenIdentifier: ScreenIdentifier
        get() = currentVerticalBackstack.last()

    val currentVerticalNavigationLevelsMap : MutableMap<Int, ScreenIdentifier>
        get() {
            val lastId = level1Backstack.lastOrNull()
            if (lastId == null) return mutableMapOf()
            // Return existing map or create and return a new one into the levels storage
            return verticalNavigationLevels.getOrPut(lastId.URI) { mutableMapOf() }
        }

    val dataRepository = repo

    private val _appStartupState = MutableStateFlow<AppStartupState>(AppStartupState.Loading)
    val appStartupState = _appStartupState.asStateFlow()

    private val _appEnvironment = MutableStateFlow(AppEnvironment())
    val appEnvironment = _appEnvironment.asStateFlow()

    val settingsManager = SettingsManager(this)
    val appScope = CoroutineScope(Job() + Dispatchers.Main)

    fun runInScreenScope (block: suspend () -> Unit) {
        screenScopesMap[currentScreenIdentifier.URI]?.launch {
            block()
        }
    }

    fun updateStartupState(state: AppStartupState) {
        _appStartupState.value = state
    }

    fun updateAppEnvironment(appEnvironment: AppEnvironment) {
        _appEnvironment.value = appEnvironment
    }

    fun initScreen(screenIdentifier: ScreenIdentifier) {
        val screenInitSettings = screenIdentifier.getScreenInitSettings(this)
        var isFirstInit = false

        if (screenInitSettings.clearStateCacheWhenScreenIsRemovedFromBackstack) {
            if (!isInTheStatesMap(screenIdentifier)) {
                val screenState = screenInitSettings.initState(screenIdentifier)
                screenStatesMap[screenIdentifier.URI] = MutableStateFlow(screenState)
                screenIdentifiersMap[screenIdentifier.URI] = screenIdentifier
                isFirstInit = true
            }
        } else {
            if (screenStatesMap[screenIdentifier.URI] == null) {
                val screenState = screenInitSettings.initState(screenIdentifier)
                screenStatesMap[screenIdentifier.URI] = MutableStateFlow(screenState)
                screenIdentifiersMap[screenIdentifier.URI] = screenIdentifier
                isFirstInit = true
            }
        }

        if (screenScopesMap[screenIdentifier.URI] == null) {
            val screenScope = CoroutineScope(Job() + Dispatchers.Main)
            screenScopesMap[screenIdentifier.URI] = screenScope
        }

        // Trigger callOnInit if it's the first time OR if requested on every navigation
        if (isFirstInit || screenInitSettings.callOnInitAtEachNavigation == CallOnInitValues.CALL_BEFORE_SHOWING_SCREEN) {
            runCallOnInit(screenIdentifier, screenInitSettings)
        }
    }

    fun runCallOnInit(screenIdentifier: ScreenIdentifier, screenInitSettings: ScreenInitSettings) {
        screenScopesMap[screenIdentifier.URI]?.launch {
            screenInitSettings.callOnInit(this@StateManager)
        }
    }

    fun removeScreen(screenIdentifier: ScreenIdentifier) {
        val screenInitSettings = screenIdentifier.getScreenInitSettings(this)
        if (screenInitSettings.clearStateCacheWhenScreenIsRemovedFromBackstack) {
            screenStatesMap.remove(screenIdentifier.URI)
            screenIdentifiersMap.remove(screenIdentifier.URI)
        }
        screenScopesMap[screenIdentifier.URI]?.cancel()
        screenScopesMap.remove(screenIdentifier.URI)
    }

    fun cancelScreenScopes() {
        screenScopesMap.forEach {
            it.value.cancel()
        }
        screenScopesMap.clear()
    }

    fun reinitScreenScopes() : List<ScreenIdentifier> {
        val reinitializedScreens = mutableListOf<ScreenIdentifier>()
        screenStatesMap.forEach {
            if (screenScopesMap[it.key] == null) {
                val screenScope = CoroutineScope(Job() + Dispatchers.Main)
                screenScopesMap[it.key] = screenScope
                val screenIdentifier = screenIdentifiersMap[it.key]
                if (screenIdentifier == null) {
                    Log.d("reinitScreenScopes: no cached ScreenIdentifier for ${it.key}, falling back to URI parsing which may lose params")
                }
                val finalScreenIdentifier = screenIdentifier ?: ScreenIdentifier.getByURI(it.key)
                if (finalScreenIdentifier != null) {
                    reinitializedScreens.add(finalScreenIdentifier)
                }
            }
        }
        return reinitializedScreens
    }

    fun <T: ScreenState> updateScreen(stateClass: KClass<T>, update: (T) -> T) {
        val (_, stateFlow) = findScreenState(stateClass) ?: return
        val currentState = stateFlow.value as? T ?: return
        stateFlow.value = update(currentState)
    }

    fun <T: ScreenState> getScreenState(stateClass: KClass<T>) : T? {
        return findScreenState(stateClass)?.second?.value as? T
    }

    private fun <T: ScreenState> findScreenState(stateClass: KClass<T>): Pair<URI, MutableStateFlow<ScreenState>>? {
        val currentScreenIdentifier = currentVerticalBackstack.lastOrNull()
        if (currentScreenIdentifier != null) {
            val stateFlow = screenStatesMap[currentScreenIdentifier.URI]
            val screenState = stateFlow?.value as? T
            if (screenState != null) {
                return currentScreenIdentifier.URI to stateFlow
            }
        }

        screenStatesMap.forEach { (uri, stateFlow) ->
            val screenState = stateFlow.value as? T
            if (screenState != null) return uri to stateFlow
        }

        return null
    }

    fun isInTheStatesMap(screenIdentifier: ScreenIdentifier) : Boolean {
        return screenStatesMap.containsKey(screenIdentifier.URI)
    }

}

class SettingsManager(val stateManager: StateManager) {
    private val settingsBuilder = SettingsBuilder(stateManager.dataRepository)
    private val _settings = MutableStateFlow(settingsBuilder.buildCategories())
    val settings = _settings.asStateFlow()

    fun getCategories() = settings.value

    fun setSettingsState(categories: List<SettingsCategory>) {
        _settings.value = categories
    }

    fun updateSettingsState() {
        _settings.value = settingsBuilder.buildCategories()
    }
}

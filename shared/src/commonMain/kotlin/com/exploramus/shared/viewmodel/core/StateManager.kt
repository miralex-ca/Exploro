package com.exploramus.shared.viewmodel.core

import com.exploramus.core.common.logging.Log
import com.exploramus.data.repository.Repository
import com.exploramus.shared.viewmodel.appstate.AppEnvironment
import com.exploramus.shared.viewmodel.appstate.AppStartupState
import com.exploramus.shared.viewmodel.appstate.prepareAppEnvironment
import com.exploramus.shared.viewmodel.screens.settings.builder.SettingsBuilder
import com.exploramus.shared.viewmodel.screens.settings.builder.SettingsCategory
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.reflect.KClass

interface ScreenState
interface ScreenParams

class StateManager(repo: Repository) {
    val screenStatesMap : MutableMap<URI,MutableStateFlow<ScreenState>> = mutableMapOf() // map of screen states currently in memory
    val screenScopesMap : MutableMap<URI,CoroutineScope> = mutableMapOf() // map of coroutine scopes associated to current screen states

    val level1Backstack: MutableList<ScreenIdentifier> = mutableListOf() // list elements are only NavigationLevel1 screenIdentifiers
    val currentVerticalBackstack: MutableList<ScreenIdentifier> = mutableListOf() // list elements are the screenIdentifiers of the current vertical backstack
    val verticalNavigationLevels : MutableMap<URI,MutableMap<Int, ScreenIdentifier>> = mutableMapOf() // the first map key is the NavigationLevel1 screenIdentifier URI, the second map key is the NavigationLevel numbers

    val appScope: CoroutineScope = MainScope()

    private val _appStartupState = MutableStateFlow<AppStartupState>(AppStartupState.Loading)
    val appStartupState = _appStartupState.asStateFlow()

    fun updateStartupState(state: AppStartupState) {
        _appStartupState.update { state }
    }

    private val _appEnvironment = MutableStateFlow(AppEnvironment())
    val appEnvironment = _appEnvironment.asStateFlow()

    fun updateAppEnvironment(state: AppEnvironment) {
        _appEnvironment.update { state }
    }

    val currentScreenIdentifier : ScreenIdentifier
        get() = currentVerticalBackstack.last()
    val currentLevel1ScreenIdentifier : ScreenIdentifier?
        get() = level1Backstack.lastOrNull()
    val currentVerticalNavigationLevelsMap: MutableMap<Int, ScreenIdentifier>
        get() = verticalNavigationLevels[currentLevel1ScreenIdentifier?.URI] ?: mutableMapOf()

    internal val dataRepository by lazy { repo }

    internal val settingsManager by lazy { SettingsStateManager(SettingsBuilder(dataRepository)) }


    init {
        prepareAppEnvironment()

    }
    // INIT SCREEN

    fun initScreen(screenIdentifier: ScreenIdentifier) {
        Log.d("initScreen: "+screenIdentifier.URI)
        val screenInitSettings = screenIdentifier.getScreenInitSettings(this)
        if (screenScopesMap[screenIdentifier.URI] == null || !screenScopesMap[screenIdentifier.URI]!!.isActive) {
            screenScopesMap[screenIdentifier.URI]?.cancel()
            screenScopesMap[screenIdentifier.URI] = CoroutineScope(Job() + Dispatchers.Main)
        }
        var firstInit = false
        if (!isInTheStatesMap(screenIdentifier)) {
            firstInit = true
            screenStatesMap[screenIdentifier.URI] = MutableStateFlow(screenInitSettings.initState(screenIdentifier))
        } else if (screenInitSettings.callOnInitAtEachNavigation == CallOnInitValues.DONT_CALL) {
            return  // in case: the state is already in the map
            //          AND "callOnInitAtEachNavigation" is set to DONT_CALL
            //      => we don't need to run the "callOnInit" function
        }
        runCallOnInit(screenIdentifier, screenInitSettings, firstInit)
    }

    fun isInTheStatesMap(screenIdentifier: ScreenIdentifier) : Boolean {
        return screenStatesMap.containsKey(screenIdentifier.URI)
    }

    fun runCallOnInit(screenIdentifier: ScreenIdentifier, screenInitSettings: ScreenInitSettings, firstInit : Boolean = false) {
        if (!firstInit && screenInitSettings.callOnInitAtEachNavigation == CallOnInitValues.CALL_BEFORE_SHOWING_SCREEN) {
            appScope.launch {
                screenInitSettings.callOnInit(this@StateManager)
            }
        } else {
            runInScreenScope(screenIdentifier) {
                screenInitSettings.callOnInit(this@StateManager)
            }
        }
    }

    inline fun <reified T: ScreenState> updateScreen(
        @Suppress("UNUSED_PARAMETER") stateClass: KClass<T>,
        update: (T) -> T,
    ) {
        Log.d("updateScreen: "+stateClass.simpleName)
        //debugLogger.log("currentVerticalNavigationLevelsMap: "+currentVerticalNavigationLevelsMap.values.map { it.URI } )

        lateinit var screenIdentifier : ScreenIdentifier
        var screenState : T?
        for(i in currentVerticalNavigationLevelsMap.keys.sortedDescending()) {
            screenState = screenStatesMap[currentVerticalNavigationLevelsMap[i]?.URI]?.value as? T
            if (screenState != null) {
                screenIdentifier = currentVerticalNavigationLevelsMap[i]!!
                screenStatesMap[screenIdentifier.URI]!!.value = update(screenState)
                Log.d("state updated @ /${screenIdentifier.URI}")
                return
            }
        }
    }

    // REMOVE SCREEN

    fun removeScreen(screenIdentifier: ScreenIdentifier) {
        Log.d("removeScreen: "+screenIdentifier.URI+" / level "+screenIdentifier.screen.navigationLevel)
        screenScopesMap[screenIdentifier.URI]?.cancel() // cancel screen's coroutine scope
        screenScopesMap.remove(screenIdentifier.URI)
        val screenInitSettings = screenIdentifier.getScreenInitSettings(this)
        if (screenInitSettings.clearStateCacheWhenScreenIsRemovedFromBackstack) {
            Log.d("removeState "+screenIdentifier.URI)
            screenStatesMap.remove(screenIdentifier.URI)
        }
    }

    // COROUTINE SCOPES FUNCTIONS

    fun reinitScreenScopes() : List<ScreenIdentifier> {
        currentVerticalNavigationLevelsMap.forEach {
            //debugLogger.log("reinitScreenScopes() "+it.value.URI)
            screenScopesMap[it.value.URI] = CoroutineScope(Job() + Dispatchers.Main)
        }
        return currentVerticalNavigationLevelsMap.values.toMutableList() // return list of screens whose scope has been reinitialized
    }

    // we run each event function on a Dispatchers.Main coroutine
    fun runInScreenScope (screenIdentifier: ScreenIdentifier? = null, block: suspend () -> Unit) {
        val URI = screenIdentifier?.URI ?: currentScreenIdentifier.URI
        val screenScope = screenScopesMap[URI]
        screenScope?.launch {
            block()
        }
    }

    fun cancelScreenScopes() {
        screenScopesMap.forEach {
            //debugLogger.log("cancelScreenScopes() "+it.key)
            it.value.cancel() // cancel screen's coroutine scope
        }
    }
}


class SettingsStateManager(
    private val settingsBuilder: SettingsBuilder
) {
    private val _settings = MutableStateFlow<List<SettingsCategory>>(emptyList())
    val settings = _settings.asStateFlow()

    fun updateSettingsState() {
        _settings.value = settingsBuilder.buildCategories()
    }

    fun setSettingsState(state: List<SettingsCategory>) {
        _settings.value = state
    }

    fun getCategories(): List<SettingsCategory> = settingsBuilder.buildCategories()
}




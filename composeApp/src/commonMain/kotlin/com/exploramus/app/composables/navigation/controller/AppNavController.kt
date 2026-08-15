package com.exploramus.app.composables.navigation.controller

import androidx.compose.runtime.MutableState
import androidx.navigation3.runtime.NavBackStack
import com.exploramus.shared.viewmodel.core.Navigation
import com.exploramus.shared.viewmodel.core.ScreenIdentifier
import com.exploramus.shared.viewmodel.core.ScreenParams
import com.exploramus.shared.viewmodel.screens.Level1Navigation
import com.exploramus.shared.viewmodel.screens.Screen

interface AppNavController {
    fun navigateBack()
    fun navigate(screen: Screen, screenParams: ScreenParams? = null)
    fun navigateByLevel1(level1Navigation: Level1Navigation)
}

class AppNavControllerBase(
    private val navigation: Navigation,
    private val backStacks: Map<String, NavBackStack<ScreenNavKey>>,
    private val currentLevel1: MutableState<ScreenIdentifier>,
) : AppNavController {

    private fun activeBackStack() = backStacks.getValue(currentLevel1.value.URI)

    override fun navigateBack() {
        if (navigation.nextBackQuitsApp) return
        val stack = activeBackStack()
        if (stack.size <= 1) return
        val originScreenIdentifier = stack.lastOrNull()?.screenIdentifier ?: return
        navigation.exitScreen(originScreenIdentifier)
        stack.removeLastOrNull()
    }

    override fun navigate(screen: Screen, screenParams: ScreenParams?) {
        val screenIdentifier = ScreenIdentifier.get(screen, screenParams)
        navigation.navigateToScreen(screenIdentifier)
        activeBackStack().add(ScreenNavKey(screenIdentifier))
    }

    override fun navigateByLevel1(level1Navigation: Level1Navigation) {
        navigation.selectLevel1Navigation(level1Navigation.screenIdentifier)
        currentLevel1.value = level1Navigation.screenIdentifier
    }
}

fun Navigation.createAppNavController(
    backStacks: Map<String, NavBackStack<ScreenNavKey>>,
    currentLevel1: MutableState<ScreenIdentifier>,
): AppNavController {
    return AppNavControllerBase(
        navigation = this,
        backStacks = backStacks,
        currentLevel1 = currentLevel1,
    )
}

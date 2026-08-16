package com.exploramus.app

import androidx.compose.ui.window.ComposeUIViewController
import com.exploramus.app.composables.MainComposable
import com.exploramus.shared.viewmodel.core.Navigation
import platform.UIKit.UIViewController

fun MainViewController(navigation: Navigation): UIViewController = ComposeUIViewController {
    MainComposable(navigation = navigation)
}

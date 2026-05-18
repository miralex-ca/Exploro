package com.muralex.myapp.viewmodel.screens.settings

import com.muralex.myapp.viewmodel.ScreenState

data class SettingsScreenState(
    val isLoading: Boolean = false,
    val savedThemeMode: Int = 0
) : ScreenState


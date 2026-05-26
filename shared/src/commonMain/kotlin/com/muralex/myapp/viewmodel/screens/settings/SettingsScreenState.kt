package com.muralex.myapp.viewmodel.screens.settings

import com.muralex.myapp.viewmodel.ScreenState
import com.muralex.myapp.viewmodel.screens.settings.builder.SettingsCategory

data class SettingsScreenState(
    val isLoading: Boolean = false,
    val categories: List<SettingsCategory> = emptyList(),
) : ScreenState






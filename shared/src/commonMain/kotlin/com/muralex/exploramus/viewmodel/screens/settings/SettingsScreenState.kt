package com.muralex.exploramus.viewmodel.screens.settings

import com.muralex.exploramus.viewmodel.ScreenState
import com.muralex.exploramus.viewmodel.screens.settings.builder.SettingsCategory

data class SettingsScreenState(
    val isLoading: Boolean = false,
    val categories: List<SettingsCategory> = emptyList(),
) : ScreenState






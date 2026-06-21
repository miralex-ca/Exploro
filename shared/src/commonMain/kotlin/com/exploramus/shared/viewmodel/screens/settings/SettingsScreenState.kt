package com.exploramus.shared.viewmodel.screens.settings

import com.exploramus.shared.viewmodel.core.ScreenState
import com.exploramus.shared.viewmodel.screens.settings.builder.SettingsCategory

data class SettingsScreenState(
    val isLoading: Boolean = false,
    val categories: List<SettingsCategory> = emptyList(),
) : ScreenState






package com.muralex.myapp.viewmodel.screens.settings.builder

import com.muralex.myapp.viewmodel.resources.FormattedText
import com.muralex.myapp.viewmodel.resources.StringRef


sealed class Setting {
    abstract val key: String
    abstract val title: StringRef
    abstract val summary: StringRef?

    data class Switch(
        override val key: String,
        override val title: StringRef,
        override val summary: StringRef? = null,
        val summaryOn: StringRef? = null,
        val summaryOff: StringRef? = null,
        val defaultValue: Boolean = false,
        val value: Boolean = false,
        val onToggle: () -> SettingAction,
    ) : Setting()

    data class Options(
        override val key: String,
        override val title: StringRef,
        override val summary: StringRef? = null,
        val formattedSummary: FormattedText.Ref? = null,
        val options: List<SettingOption>,
        val selectedValue: String = "",
        val dialogTitle: StringRef? = null,
        val onSelect: (String) -> SettingAction
    ) : Setting()

    data class Action(
        override val key: String,
        override val title: StringRef,
        override val summary: StringRef? = null,
        val formattedSummary: FormattedText? = null,
        val dialogTitle: StringRef? = null,
        val dialogMessage: StringRef? = null,
        val onClick: () -> SettingAction
    ) : Setting()

    data class Info(
        override val key: String,
        override val title: StringRef,
        override val summary: StringRef? = null,
        val info: String? = null,
    ) : Setting()
}

data class SettingOption(
    val value: String,
    val label: StringRef
)

sealed class SettingAction {
    data class SetThemeMode(val value: String) : SettingAction()
    data class SetFavoriteSwipe(val enabled: Boolean) : SettingAction()
    data object SyncData: SettingAction()
}
package com.muralex.exploramus.viewmodel.resources


/**
 * Entry point for shared resource references.
 *
 * Provides a type-safe way to reference resources across platforms within the shared module.
 */
object SharedRes {
    /**
     * Enum of string resource keys.
     *
     * Note: Suffix `_fmt` indicates the resource is used for [FormattedText] and requires arguments.
     */
    enum class Strings : StringRef {
        empty_ref_placeholder,
        settings_summary_current_fmt,
        settings_theme_title,
        settings_theme_option_system,
        settings_theme_option_dark,
        settings_theme_option_light,
        settings_theme_dialog_title,
        settings_favorite_swipe_title,
        settings_favorite_swipe_summaryOn,
        settings_favorite_swipe_summaryOff,
        settings_sync_title,
        settings_sync_summary,
        settings_sync_dialog_title,
        settings_sync_dialog_message,
        settings_category_interface,
        settings_category_data,
        settings_appversion_title,
        settings_appversion_summary,
        settings_deviceinfo_title,
        settings_deviceinfo_summary,
        settings_sync_in_progress,
        settings_sync_last_fmt,
        settings_sync_success_fmt,
        settings_sync_failed_fmt,
        ;

        override fun simpleName(): String {
            return this.name
        }
    }
}
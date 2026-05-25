package com.muralex.myapp.viewmodel.resources

/**
 * Interface for references to string resources
 */
interface StringRef {
    /**
     * Returns the name of reference
     */
    fun simpleName(): String
}

/**
 * Class for references to string resources with arguments
 */
data class StringRefWithArgs(
    val ref: StringRef
)

object SharedRes {
    enum class Strings : StringRef {
        settings_theme_title,
        settings_summary_current,
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
        settings_sync_success,
        settings_sync_failed
        ;

        override fun simpleName(): String {
            return this.name
        }
    }
}
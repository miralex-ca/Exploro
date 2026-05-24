package com.muralex.myapp.viewmodel.resources

/**
 * Interface for references to string resources
 */
interface StringRef {
    /**
     * Returns the name of reference
     */
    fun simpleName(): String
    val args: List<Any> get() = emptyList()
}

object SharedRes {
    enum class Strings : StringRef {
        settings_theme_title,
        settings_theme_summary,
        settings_theme_option_system,
        settings_theme_option_dark,
        settings_theme_option_light,
        settings_theme_dialog_title,
        settings_favorite_swipe_title,
        settings_favorite_swipe_summary,
        settings_favorite_swipe_summaryOn,
        settings_favorite_swipe_summaryOff,
        settings_sync_title,
        settings_sync_summary,
        settings_sync_dialog_title,
        settings_sync_dialog_message,
        ;

        override fun simpleName(): String {
            return this.name
        }
    }
}
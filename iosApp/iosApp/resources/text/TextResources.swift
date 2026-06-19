import Foundation

func stringRes(_ key: String, _ args: CVarArg...) -> String {
    let format = NSLocalizedString(key, comment: "")
    return String(format: format, arguments: args)
}

struct Strings {
    static var homeTitle: String { stringRes("screen_home_title") }
    static var favoritesTitle: String { stringRes("screen_favorites_title") }
    static var searchTitle: String { stringRes("screen_search_title") }
    static var settingsTitle: String { stringRes("screen_settings_title") }

    static var navBrowse: String { stringRes("nav_browse") }
    static var navFavorites: String { stringRes("nav_favorites") }

    static var appLoadingMessage: String { stringRes("app_loading_message") }
    static var appStartupErrorTitle: String { stringRes("app_startup_error_title") }
    static var appStartupErrorSyncDesc: String { stringRes("app_startup_error_sync_desc") }
    static var appStartupErrorDesc: String { stringRes("app_startup_error_desc") }
    static var appErrorTryAgain: String { stringRes("app_error_try_again") }

    static var searchPlaceholder: String { stringRes("search_placeholder") }
    static var noSearchResult: String { stringRes("no_search_result") }
    static var startSearch: String { stringRes("start_search") }
    static var detailsCoatOfArms: String { stringRes("details_coat_of_arms") }
    static var commonClose: String { stringRes("common_close") }
    static var commonConfirm: String { stringRes("common_confirm") }
    static var commonContinue: String { stringRes("common_continue") }
    static var commonCancel: String { stringRes("common_cancel") }

    static var detailLabelLocation: String { stringRes("detail_label_location") }
    static var detailLabelArea: String { stringRes("detail_label_area") }
    static var detailLabelPopulation: String { stringRes("detail_label_population") }
    static var detailLabelLanguages: String { stringRes("detail_label_languages") }
    static var detailLabelCurrency: String { stringRes("detail_label_currency") }
    static var detailLabelTimezones: String { stringRes("detail_label_timezones") }
    static var detailLabelCapital: String { stringRes("detail_label_capital") }
    static var detailLabelRegion: String { stringRes("detail_label_region") }

    static var listItemLabelCapital: String { stringRes("list_item_label_capital") }
    static func listItemLabelCapital(_ capital: String) -> String {
        return stringRes("list_item_label_capital_fmt", capital)
    }

    static var commonView: String { stringRes("common_view") }
    static var commonRemove: String { stringRes("common_remove") }
    static var commonBack: String { stringRes("common_back") }
    static var commonClear: String { stringRes("common_clear") }
    static var commonSettings: String { stringRes("common_settings") }
    static var commonMore: String { stringRes("common_more") }
    static var commonMoreOptions: String { stringRes("common_more_options") }
    static var commonSearch: String { stringRes("common_search") }
    static var commonOpenInMaps: String { stringRes("common_open_in_maps") }
    static var commonOpenInWikipedia: String { stringRes("common_open_in_wikipedia") }
    static var openKeyboard: String { stringRes("open_keyboard") }


    static var emptyTitleNoResults: String { stringRes("empty_title_no_results") }
    static var emptyMsgNoResults: String { stringRes("empty_msg_no_results") }
    static var emptyTitleEmptyList: String { stringRes("empty_title_empty_list") }
    static var emptyMsgEmptyList: String { stringRes("empty_msg_empty_list") }
    static var emptyTitleNotFound: String { stringRes("empty_title_not_found") }
    static var emptyMsgNotFound: String { stringRes("empty_msg_not_found") }

    static func detailLabelLanguage(count: Int) -> String {
        return count == 1 ? stringRes("detail_label_language") : stringRes("detail_label_languages")
    }
}

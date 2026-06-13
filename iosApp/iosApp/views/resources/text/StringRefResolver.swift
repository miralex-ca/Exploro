import Foundation
import Shared

struct StringRefResolver {
    
    static func resolve(ref: StringRef) -> String {
        guard let sharedRef = ref as? SharedRes.__Strings else {
            return ref.simpleName()
        }
        
        switch sharedRef.toSwiftEnum() {
        case .settingsThemeTitle: return stringRes("settings_theme_title")
        case .settingsThemeOptionSystem: return stringRes("settings_theme_option_system")
        case .settingsThemeOptionDark: return stringRes("settings_theme_option_dark")
        case .settingsThemeOptionLight: return stringRes("settings_theme_option_light")
        case .settingsThemeDialogTitle: return stringRes("settings_theme_dialog_title")
        case .settingsFavoriteSwipeTitle: return stringRes("settings_favorite_swipe_title")
        case .settingsFavoriteSwipeSummaryOn: return stringRes("settings_favorite_swipe_summaryOn")
        case .settingsFavoriteSwipeSummaryOff: return stringRes("settings_favorite_swipe_summaryOff")
        case .settingsSyncTitle: return stringRes("settings_sync_title")
        case .settingsSyncSummary: return stringRes("settings_sync_summary")
        case .settingsSyncDialogTitle: return stringRes("settings_sync_dialog_title")
        case .settingsSyncDialogMessage: return stringRes("settings_sync_dialog_message")
        case .settingsCategoryInterface: return stringRes("settings_category_interface")
        case .settingsCategoryData: return stringRes("settings_category_data")
        case .settingsAppversionTitle: return stringRes("settings_appversion_title")
        case .settingsAppversionSummary: return stringRes("settings_appversion_summary")
        case .settingsDeviceinfoTitle: return stringRes("settings_deviceinfo_title")
        case .settingsDeviceinfoSummary: return stringRes("settings_deviceinfo_summary")
        case .settingsSyncInProgress: return stringRes("settings_sync_in_progress")
        default: return ref.simpleName()
        }
    }
    
    static func resolve(ref: StringRefWithArgs, args: [String]) -> String {
        switch ref.ref {
        case SharedRes.Strings.settingsSummaryCurrentFmt:
            return stringRes("settings_summary_current", args.first ?? "")
        default:
            return ref.ref.simpleName()
        }
    }

    static func resolve(ref: StringRefWithArgs, arg: String) -> String {
        switch ref.ref {
        case SharedRes.Strings.settingsSyncLastFmt:
            return stringRes("settings_sync_last", arg)
        case SharedRes.Strings.settingsSyncSuccessFmt:
            return stringRes("settings_sync_success", arg)
        case SharedRes.Strings.settingsSyncFailedFmt:
            return stringRes("settings_sync_failed", arg)
        default:
            return ref.ref.simpleName()
        }
    }
}

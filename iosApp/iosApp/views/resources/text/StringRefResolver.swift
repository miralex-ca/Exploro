 

import SwiftUI
import Shared


extension SharedRes.Strings {
    func asString() -> String {
        switch self {
        case .settingsThemeTitle: return "Theme"
        case .settingsThemeOptionSystem: return "System"
        case .settingsThemeOptionDark: return "Dark"
        case .settingsThemeOptionLight: return "Light"
        case .settingsThemeDialogTitle: return "Choose Theme"
        case .settingsFavoriteSwipeTitle: return "Swipe to remove"
        case .settingsFavoriteSwipeSummaryOn: return "Swipe enabled"
        case .settingsFavoriteSwipeSummaryOff: return "Swipe disabled"
        case .settingsSyncTitle: return "Content updates"
        case .settingsSyncSummary: return "Keep app content up to date"
        case .settingsSyncInProgress: return "Updating content…"
        case .settingsCategoryInterface: return "Interface"
        case .settingsCategoryData: return "Data"
        case .settingsAppversionTitle: return "App Version"
        case .settingsAppversionSummary: return "Version info"
        case .settingsDeviceinfoTitle: return "Device Info"
        case .settingsSyncDialogTitle: return "Check for updates?"
        case .settingsSyncDialogMessage: return "\nWe’ll refresh the app content with the latest available data.\n"
        default: return String(describing: self)
        }
    }
}


extension StringRef {
    func asString() -> String {
        if let ref = self as? SharedRes.__Strings {
            return ref.toSwiftEnum().asString()
        }
        return String(describing: self)
    }
}


extension StringRefWithArgs {
    func asStringWithArg(_ arg: String) -> String {
        switch self.ref {
        case SharedRes.Strings.settingsSummaryCurrentFmt: return "Current: \(arg)"
        case SharedRes.Strings.settingsSyncLastFmt: return "Last updated: \(arg)"
        case SharedRes.Strings.settingsSyncSuccessFmt: return "Content updated successfully\nLast updated: \(arg)"
        case SharedRes.Strings.settingsSyncFailedFmt: return "Update failed. Please try again.\nLast updated: \(arg)"
        default: return self.ref.simpleName()
        }
    }
}



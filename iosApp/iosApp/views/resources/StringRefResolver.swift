 

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
        case .settingsSyncTitle: return "Sync"
        case .settingsSyncSummary: return "Sync data"
        case .settingsCategoryInterface: return "Interface"
        case .settingsCategoryData: return "Data"
        case .settingsAppversionTitle: return "App Version"
        case .settingsAppversionSummary: return "Version info"
        case .settingsDeviceinfoTitle: return "Device Info"
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
//
//extension StringRefWithArgs {
//    func asStringWithArg(_ arg: String) -> String {
//        switch self.ref {
//        case SharedRes.Strings.shared.settings_summary_current_fmt: return "Current: \(arg)"
//        case SharedRes.Strings.shared.settings_sync_last_fmt: return "Last sync: \(arg)"
//        case SharedRes.Strings.shared.settings_sync_success_fmt: return "Sync success: \(arg)"
//        case SharedRes.Strings.shared.settings_sync_failed_fmt: return "Sync failed: \(arg)"
//        default: return self.ref.simpleName()
//        }
//    }
//}
//
//extension FormattedText {
//    func asString() -> String {
//        switch self {
//        case let t as FormattedText.SimpleText: return t.textRef.asString()
//        case let t as FormattedText.WithString: return t.ref.asStringWithArg(t.arg)
//        case let t as FormattedText.WithRef: return t.ref.asStringWithArg(t.arg.asString())
//        default: return self.ref.ref.simpleName()
//        }
//    }
//}


import SwiftUI
import Shared



enum SettingsAction {
    case toggle(key: String, value: Bool)
    case select(key: String, value: String)
    case action(key: String)
}



class SettingsSheetEventHandler {
    private let events: Events

    init(events: Events) {
        self.events = events
    }
    
    func onSettingAction(_ action: SettingsAction) {

        switch action {

        case let .toggle(key, value):
            events.updateSetting(key: key, value: value)

        case let .select(key, value):
            events.updateSetting(key: key, value: value)

        case let .action(key):  
            events.triggerSettingAction(key: key)
        }
    }
 
//    func onSettingAction(_ action: SettingAction) {
//        if let action = action as? SettingAction.SetFavoriteSwipe {
//            events.setFavoriteSwipeEnabled(enabled: action.enabled)
//        } else if let action = action as? SettingAction.SetThemeMode {
//            events.saveThemeMode(name: action.value)
//        } else if action is SettingAction.SyncData {
//            events.syncDataFromSettings()
//        }
//    }
}


struct SettingsView: View { 
    @EnvironmentObject var appObj: AppObservableObject
    
    @StateObject private var vm = SettingsViewModel()
    
    @State var settingsList: [SettingsCategory] = []
    
    @Environment(\.appTheme) var theme

    var body: some View {
        NavigationStack {
            ScrollView {
                VStack(spacing: 16) {
                    ForEach(vm.settingsList, id: \.id) { category in
                        SettingsCategorySection(
                            category: category,
                            onAction: vm.send
                        )
                    }
                }
                .padding()
                
            }
            .navigationTitle("Settings")
            .toolbar {
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button {
                        appObj.showSettings = false
                    } label: {
                        Image(systemName: "xmark")
                    }
                }
            }
            .background(theme.background)
        }
        .background(theme.background)
        .task {
            vm.bind(appObj)
        }
    }
}

extension Setting {

    var isNavigable: Bool {
        switch self {
        case is Setting.Options, is Setting.Action:
            return true
        default:
            return false
        }
    }
}


struct SettingsCategorySection: View {
    let category: SettingsCategory
    let onAction: (SettingsAction) -> Void
    
    @Environment(\.appTheme) var theme

    var body: some View {
        
        VStack(alignment: .leading, spacing: 8) {

            if let title = category.title {
                Text(title.asString())
                    .font(.headline)
                    .padding(.horizontal, 8)
            }
            
            VStack(spacing: 2) {
                ForEach(Array(category.settings.enumerated()), id: \.offset) { index, setting in
                    let isFirst = index == 0
                    let isLast = index == category.settings.count - 1

                    let corners: UIRectCorner = {
                        if isFirst && isLast { return .allCorners }
                        if isFirst { return [.topLeft, .topRight] }
                        if isLast { return [.bottomLeft, .bottomRight] }
                        return []
                    }()

                    SettingsRow(
                        setting: setting,
                        onAction: onAction
                    )
                        .background(theme.cardSurface)
                        .clipShape(RoundedCornerShape(corners: corners, radius: 14))
                        .overlay(
                            RoundedCornerShape(corners: corners, radius: 14)
                                .stroke(Color.gray.opacity(0.2), lineWidth: 0.5)
                        )
                }
            }

//            VStack(spacing: 5) {
//                ForEach(category.settings, id: \.key) { setting in
//                    SettingsRow(setting: setting)
//                }
//            }
//            .background(Color(.secondarySystemGroupedBackground))
//            .clipShape(RoundedRectangle(cornerRadius: 14))
        }
    }
}





@ViewBuilder
func SettingsRow(
    setting: Setting,
    onAction: @escaping (SettingsAction) -> Void
) -> some View {
    switch setting {

    case let s as Setting.Switch:
        SwitchRow(setting: s, onAction: onAction)

    case let s as Setting.Info:
        InfoRow(setting: s)

    case let s as Setting.Options:
        NavigationLink {
            OptionsSettingView(setting: s, onAction: onAction)
        } label: {
            OptionsRow(setting: s)
        }
        .buttonStyle(.plain)

    case let s as Setting.Action:
        NavigationLink {
            ActionSettingView(setting: s, onAction: onAction)
        } label: {
            ActionRow(setting: s)
        }
        .buttonStyle(.plain)
        
    default:
        EmptyView()
    }
}




struct OptionsSettingView: View {
    let setting: Setting.Options
    let onAction: (SettingsAction) -> Void
    @Environment(\.appTheme) var theme

    var body: some View {
        let options = setting.options.map { $0.toUitItem() }
        let selectedValue = setting.selectedValue

        List {
            ForEach(options, id: \.id) { option in
                Button {
                    onAction(
                        .select(
                            key: setting.key,
                            value: option.id
                        )
                    )
                } label: {
                    HStack {
                        Text(option.label)
                            .foregroundStyle(.primary)
                        
                        Spacer()
                        
                        if selectedValue == option.id {
                            Image(systemName: "checkmark")
                                .foregroundStyle(theme.onBackground)
                        }
                    }
                    .padding(.vertical, 6)
                }
                .listRowBackground(theme.cardSurface)
            }
        }
        .scrollContentBackground(.hidden)
        .background(theme.background)
        .navigationTitle(setting.dialogTitle?.asString() ?? setting.title.asString())
    }
}


struct OptionsRow: View {
    let setting: Setting.Options
     

    var body: some View {
        PreferenceContent(
            title: setting.title.asString(),
            summary: "Summary"
        )
        .padding(14)
        .frame(maxWidth: .infinity, alignment: .leading)
        .contentShape(Rectangle())
    }
}

struct ActionRow: View {
    let setting: Setting.Action
 
    var body: some View {
        PreferenceContent(
            title: setting.title.asString(),
            summary: "Summary"
        )
        .padding(14)
        .frame(maxWidth: .infinity, alignment: .leading)
        .contentShape(Rectangle())
    }
}


struct ActionSettingView: View {
    let setting: Setting.Action
    let onAction: (SettingsAction) -> Void

    var body: some View {
        VStack(spacing: 16) {
            Text(setting.dialogMessage?.asString() ?? "")
                .multilineTextAlignment(.center)

            Button() {
                onAction(.action(key: setting.key))
            } label: {
                Text(setting.title.asString())
            }
            Spacer()
        }
        .padding()
        .navigationTitle(setting.dialogTitle?.asString() ?? setting.title.asString())
    }
}




struct SwitchRow: View {
    let setting: Setting.Switch
    let onAction: (SettingsAction) -> Void

    var body: some View {

        HStack {
            PreferenceContent(
                title: setting.title.asString(),
                summary: "Swipe to delete favorites"//setting.summary?.asString()
            )

            Spacer()

            Toggle(
                "",
                isOn: Binding(
                    get: { setting.value },
                    set: { newValue in
                        onAction(.toggle(key: setting.key, value: newValue))
                    }
                )
            )
            .labelsHidden()
        }
        .padding()
    }
}



struct InfoRow: View {
    let setting: Setting.Info
    
    var body: some View {
        PreferenceContent(
            title: setting.title.asString(),
            summary: setting.info
        )
        .padding(14)
    }
}




/////////////////
struct SettingsSheet: View {

    var body: some View {
        NavigationStack {
            SettingsRootView()
        }
    }
}


struct SettingsRootView: View {

    var body: some View {
        
        List {

            NavigationLink("Appearance") {
                AppearanceView()
            }

            NavigationLink("Language") {
                LanguageView()
            }

            NavigationLink("About") {
                AboutView()
            }
        }
        .navigationTitle("Settings")
    }
}

struct AppearanceView: View {

    var body: some View {
        Text("Appearance Settings")
            .navigationTitle("Appearance")
    }
}

struct LanguageView: View {

    var body: some View {
        Text("Language Settings")
            .navigationTitle("Language")
    }
}

struct AboutView: View {

    var body: some View {
        Text("About App")
            .navigationTitle("About")
    }
}

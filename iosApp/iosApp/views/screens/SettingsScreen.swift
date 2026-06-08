 
import SwiftUI
import Shared


class SettingsEventHandler {
    private let events: Events

    init(events: Events) {
        self.events = events
    }
 

    func onSettingAction(_ action: SettingAction) {
        if let action = action as? SettingAction.SetFavoriteSwipe {
            events.setFavoriteSwipeEnabled(enabled: action.enabled)
        } else if let action = action as? SettingAction.SetThemeMode {
           // events.saveThemeMode(themeMode: ThemeMode.companion.byName(name: action.value))
        } else if action is SettingAction.SyncData {
            events.syncDataFromSettings()
        }
    }
}

struct SettingsScreen: View {

    let screenState: SettingsScreenState
    let eventHandler: SettingsEventHandler

    var body: some View {

        if screenState.isLoading {
            ProgressView()
        } else if screenState.categories.isEmpty {
            ContentUnavailableView(
                "No Settings",
                systemImage: "gear"
            )
        } else {
            ScrollView {
                VStack(spacing: 24) {

                    ForEach(screenState.categories, id: \.id) { category in
                        SettingsCategoryView(
                            category: category,
                            onAction: eventHandler.onSettingAction
                        )
                    }
                }
                .padding()
                .frame(maxWidth: 600)
            }
        }
    }
}


struct SettingsCategoryView: View {

    let category: SettingsCategory
    let onAction: (SettingAction) -> Void

    var body: some View {

        VStack(alignment: .leading, spacing: 8) {

            if let title = category.title {
                Text(title.asString())
                    .font(.headline)
                    .foregroundStyle(.tint)
                    .padding(.leading, 8)
            }

            VStack(spacing: 0) {

                ForEach(Array(category.settings.enumerated()), id: \.offset) { _, setting in
                    SettingRow(
                        setting: setting,
                        onAction: onAction
                    )

                    if setting !== category.settings.last {
                        Divider()
                    }
                }
            }
            .background(.regularMaterial)
            .clipShape(
                RoundedRectangle(cornerRadius: 14)
            )
        }
    }
}

struct SettingRow: View {

    let setting: Setting
    let onAction: (SettingAction) -> Void

    var body: some View {

        switch setting {

        case let setting as Setting.Switch:
            SwitchPreference(
                setting: setting,
                onAction: onAction
            )

        case let setting as Setting.Options:
            OptionsPreference(
                setting: setting,
                onAction: onAction
            )

        case let setting as Setting.Action:
            ActionPreference(
                setting: setting,
                onAction: onAction
            )

        case let setting as Setting.Info:
            InfoPreference(setting: setting)

        default:
            EmptyView()
        }
    }
}

struct SwitchPreference: View {
    let setting: Setting.Switch
    let onAction: (SettingAction) -> Void

    var body: some View {

        HStack {

            PreferenceContent(
                title: setting.title.asString(),
                summary: setting.summary?.asString()
            )

            Spacer()

            Toggle(
                "",
                isOn: Binding(
                    get: { setting.value },
                    set: { _ in
                        onAction(setting.onToggle())
                    }
                )
            )
            .labelsHidden()
        }
        .padding()
    }
}

 

struct ActionPreference: View {

    let setting: Setting.Action
    let onAction: (SettingAction) -> Void

    @State private var showDialog = false

    var body: some View {

        Button {
            showDialog = true
        } label: {
            PreferenceContent(
                title: setting.title.asString(),
                summary: setting.summary?.asString()
            )
        }
        .buttonStyle(.plain)
        .padding()
        .confirmationDialog(
            setting.title.asString(),
            isPresented: $showDialog
        ) {
            Button("Confirm") {
                onAction(setting.onClick())
            }
        }
    }
}

struct OptionsPreference: View {

    let setting: Setting.Options
    let onAction: (SettingAction) -> Void

    @State private var showSheet = false

    var body: some View {

        Button {
            showSheet = true
        } label: {
            PreferenceContent(
                title: setting.title.asString(),
                summary: selectedLabel
            )
        }
        .buttonStyle(.plain)
        .padding()
        .sheet(isPresented: $showSheet) {

//            NavigationStack {
//
//                List {
//
//                    ForEach(setting.options.indices, id: \.self) { index in
//
//                        let option = setting.options[index]
//
//                        Button(option.label.asString()) {
//
//                            onAction(
//                                setting.onSelect(
//                                    value: option.value
//                                )
//                            )
//
//                            showSheet = false
//                        }
//                    }
//                }
//                .navigationTitle(setting.title.asString())
//            }
        }
    }

    private var selectedLabel: String {
        setting.options
            .first { $0.value == setting.selectedValue }?
            .label
            .asString() ?? ""
    }
}

struct PreferenceContent: View {
    let title: String
    let summary: String?

    var body: some View {

        VStack(
            alignment: .leading,
            spacing: 4
        ) {

            Text(title)
                .font(.body.weight(.medium))

            if let summary {
                Text(summary)
                    .font(.subheadline)
                    .foregroundStyle(.secondary)
            }
        }
        .frame(
            maxWidth: .infinity,
            alignment: .leading
        )
    }
}


struct InfoPreference: View {

    let setting: Setting.Info

    var body: some View {

        PreferenceContent(
            title: setting.title.asString(),
            summary: setting.info
        )
        .padding()
    }
}

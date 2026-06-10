 
import SwiftUI
import Shared

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

    @Environment(\.appTheme) var theme

    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            if let title = category.title {
                Text(title.asString())
                    .font(.headline)
                    .foregroundStyle(.tint)
                    .padding(.leading, 8)
                    .padding(.bottom, 8)
            }

            let settings = category.settings as! [Setting]

            VStack(spacing: 2) {  // tiny space between cards
                ForEach(Array(settings.enumerated()), id: \.offset) { index, setting in
                    let isFirst = index == 0
                    let isLast = index == settings.count - 1

                    let corners: UIRectCorner = {
                        if isFirst && isLast { return .allCorners }
                        if isFirst { return [.topLeft, .topRight] }
                        if isLast { return [.bottomLeft, .bottomRight] }
                        return []
                    }()

                    SettingRow(setting: setting, onAction: onAction)
                        .background(theme.cardSurface)
                        .clipShape(RoundedCornerShape(corners: corners, radius: 14))
                        .overlay(
                            RoundedCornerShape(corners: corners, radius: 14)
                                .stroke(Color.gray.opacity(0.2), lineWidth: 0.5)
                        )
                }
            }
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
                summary: "Swipe to delete favorites"//setting.summary?.asString()
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
            .padding()
            .frame(maxWidth: .infinity, alignment: .leading)
            .contentShape(Rectangle())
        }
        .buttonStyle(.plain)
        .alert(
            setting.dialogTitle?.asString() ?? setting.title.asString(),
            isPresented: $showDialog
        ) {
            Button("Confirm") {
                onAction(setting.onClick())
            }
            Button("Cancel", role: .cancel) {}
        } message: {
            if let message = setting.dialogMessage?.asString() ?? setting.summary?.asString() {
                Text(message)
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
            .padding(14)
            .frame(maxWidth: .infinity, alignment: .leading)
            .contentShape(Rectangle())
        }
        .buttonStyle(.plain)
        .sheet(isPresented: $showSheet) {
            NavigationStack {
                let options = setting.options.map { $0.toUitItem() }
                let selectedValue = setting.selectedValue

                List {
                    ForEach(Array(options.enumerated()), id: \.offset) { index, option in

                        let isSelected = option.id == selectedValue

                        Button {
                            onAction( setting.onSelect(option.id))
                            showSheet = false

                        } label: {
                            HStack {
                                Text("  \(option.label)")
                                    .foregroundStyle(.primary)
                                
                                Spacer()
                                if isSelected {
                                    Image(systemName: "checkmark")
                                        .foregroundStyle(.blue)
                                }
                            }
                            .padding(.trailing, 10)
                        }
                        .listRowInsets(EdgeInsets(top: 0, leading: 20, bottom: 0, trailing: 20))
                    }
                }
                .listStyle(.plain)
                .padding(.top, 20)
                
                .navigationTitle(setting.title.asString())
                .navigationBarTitleDisplayMode(.inline)
                .toolbar {
                    ToolbarItem(placement: .bottomBar) {
                        Button("Cancel") { showSheet = false }
                    }
                }
            }
            .presentationDetents([.fraction(0.60), .large])
            .presentationDragIndicator(.visible)
        }
    }

    private var selectedLabel: String {
        setting.options
            .first { $0.value == setting.selectedValue }?
            .label
            .asString() ?? ""
    }
}

struct SettingsOptionsUiItem {
    let id: String
    let label: String
}

extension SettingOption {
    func toUitItem() -> SettingsOptionsUiItem {
        SettingsOptionsUiItem(id: self.value, label: self.label.asString())
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
        .padding(14)
    }
}

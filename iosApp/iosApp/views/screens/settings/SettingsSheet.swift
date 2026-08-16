
import SwiftUI
import ComposeApp


struct SettingsView: View {
    @EnvironmentObject var appObj: AppObservableObject
    @Environment(\.appTheme) var theme
    @Environment(\.appLayout) var appLayout
    
    @StateObject private var vm = SettingsViewModel()
    @State var settingsList: [SettingsCategory] = []
    
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
            .navigationTitle(Strings.settingsTitle)
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
            vm.bind(appObj, isTablet: appLayout.isTablet)
        }
    }
}


struct SettingsCategorySection: View {
    let category: SettingsCategory
    let onAction: (SettingsUiAction) -> Void
    
    @Environment(\.appTheme) var theme
    
    var body: some View {
        
        VStack(alignment: .leading, spacing: 8) {
            
            if let title = category.title {
                Text(title.asString())
                    .font(.appHeadline)
                    .padding(.horizontal, 8)
            } else {
                Spacer().frame(height: 6)
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
        }
    }
}


@ViewBuilder
func SettingsRow(
    setting: Setting,
    onAction: @escaping (SettingsUiAction) -> Void
) -> some View {
    switch setting {
        
    case let s as Setting.Switch:
        SettingsSwitchRow(setting: s, onAction: onAction)
        
    case let s as Setting.Info:
        InfoRow(setting: s)
        
    case let s as Setting.Options:
        NavigationLink {
            SettingsOptionsView(setting: s, onAction: onAction)
        } label: {
            SettingsWithOptionsRow(setting: s)
        }
        .buttonStyle(SettingsRowButtonStyle())
        
    case let s as Setting.Action:
        NavigationLink {
            SettinsActionView(setting: s, onAction: onAction)
        } label: {
            SettingsWithActionRow(setting: s)
        }
        .buttonStyle(SettingsRowButtonStyle())
        
    default:
        EmptyView()
    }
}

struct SettingsRowButtonStyle: ButtonStyle {
    func makeBody(configuration: Configuration) -> some View {
        configuration.label
            .background(
                configuration.isPressed
                ? Color.primary.opacity(0.08)
                : Color.clear
            )
            .animation(.easeOut(duration: 0.12), value: configuration.isPressed)
    }
}


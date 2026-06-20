
import SwiftUI
import Shared

struct SettingsWithActionRow: View {
    let setting: Setting.Action
 
    var body: some View {
        HStack {
            PreferenceContent(
                title: setting.title.asString(),
                summary: setting.formattedSummary?.asString()
            )
            Spacer()

            Image(systemName: "chevron.right")
                .font(.footnote)
                .foregroundStyle(.secondary)
                .padding(.trailing, 8)
        }
        .padding(14)
        .frame(maxWidth: .infinity, alignment: .leading)
        .contentShape(Rectangle())
    }
}


struct SettinsActionView: View {
    let setting: Setting.Action
    let onAction: (SettingsAction) -> Void
    @Environment(\.appTheme) var theme

    var body: some View {
        ZStack (alignment: .top) {
            Color.clear
            VStack(spacing: 16) {
                Text(setting.formattedSummary?.asString() ?? "")
                    .multilineTextAlignment(.center)
                
                Text(setting.dialogMessage?.asString() ?? "")
                    .multilineTextAlignment(.center)
                
                Spacer().frame(height: 24)
                
                Button() {
                    onAction(.action(key: setting.key))
                } label: {
                    Text(setting.dialogActionText?.asString() ?? Strings.commonContinue)
                }
                .buttonStyle(.bordered)
            }
            .padding(.horizontal, 20)
            .padding(.vertical, 16)
            .background(theme.cardSurface)
            .clipShape(RoundedRectangle(cornerRadius: 14))
            .overlay(
                RoundedRectangle(cornerRadius: 14)
                    .stroke(Color.gray.opacity(0.2), lineWidth: 0.5)
            )
        }
        .padding(.top, 16)
        .padding(.horizontal, 20)
        .background(theme.background)
        .navigationTitle(setting.dialogTitle?.asString() ?? setting.title.asString())
        .navigationBarTitleDisplayMode(.inline)
        
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


import SwiftUI
import Shared


struct SettingsWithOptionsRow: View {
    let setting: Setting.Options
    
    private var selectedLabel: String {
        print(setting.selectedValue)
        return setting.options
            .first { $0.value.lowercased() == setting.selectedValue.lowercased() }?
            .label
            .asString() ?? ""
    }
     
    var body: some View {
        HStack {
            PreferenceContent(
                title: setting.title.asString(),
                summary: setting.formattedSummary?.with(selectedLabel)
            )

            Spacer()

            Image(systemName: "chevron.right")
                .font(.footnote)
                .foregroundStyle(.secondary)
                .padding(.trailing, 8)
        }
        .padding(14)
        .contentShape(Rectangle())
    }
}


struct SettingsOptionsView: View {
    let setting: Setting.Options
    let onAction: (SettingsUiAction) -> Void
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
                                .foregroundStyle(theme.caret)
                                .fontWeight(.bold)
                                .padding(.trailing, 10)
                        }
                    }
                    .padding(.vertical, 6)
                }
                .listRowBackground(theme.cardSurface)
                .contentShape(Rectangle())
                .foregroundStyle(.primary)
            }
        }
        .scrollContentBackground(.hidden)
        .background(theme.background)
        .navigationTitle(setting.dialogTitle?.asString() ?? setting.title.asString())
        .navigationBarTitleDisplayMode(.inline)
    }
}


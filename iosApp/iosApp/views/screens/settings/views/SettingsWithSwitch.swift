
import SwiftUI
import ComposeApp


struct SettingsSwitchRow: View {
    let setting: Setting.Switch
    let onAction: (SettingsUiAction) -> Void
    
    private var summary: String? {
            if setting.value, let summaryOn = setting.summaryOn {
                return summaryOn.asString()
            }

            if !setting.value, let summaryOff = setting.summaryOff {
                return summaryOff.asString()
            }

            return setting.summary?.asString()
        }

    var body: some View {
        HStack {
            PreferenceContent(
                title: setting.title.asString(),
                summary: summary
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

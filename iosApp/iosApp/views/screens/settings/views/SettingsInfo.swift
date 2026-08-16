
import SwiftUI
import ComposeApp


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

struct PreferenceContent: View {
    let title: String
    let summary: String?

    var body: some View {
        VStack(
            alignment: .leading,
            spacing: 4
        ) {
            Text(title)
                .font(.appBody.weight(.medium))

            if let summary {
                Text(summary)
                    .font(.appSubheadline)
                    .foregroundStyle(.secondary)
            }
        }
        .frame(
            maxWidth: .infinity,
            alignment: .leading
        )
    }
}

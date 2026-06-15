
import SwiftUI
import Shared

struct AppLoadingScreen: View {
    @Environment(\.appTheme) var theme

    @State private var spinnerVisible = false
    @State private var textVisible = false

    var body: some View {
        ZStack {
            theme.background.ignoresSafeArea()

            VStack(spacing: 32) {
                if spinnerVisible {
                    CircleProgressBarView()
                        .tint(theme.onSurfaceVariant)
                }

                Text(Strings.appLoadingMessage)
                    .font(.body)
                    .foregroundStyle(theme.onSurfaceVariant)
                    .opacity(textVisible ? 1 : 0)
                    .animation(.easeIn(duration: 0.3), value: textVisible)
                    .frame(height: 24)
            }
        }
        .task {
            try? await Task.sleep(nanoseconds: 200_000_000)
            withAnimation(.easeIn(duration: 0.2)) { spinnerVisible = true }
            try? await Task.sleep(nanoseconds: 1_000_000_000)
            textVisible = true
        }
    }
}

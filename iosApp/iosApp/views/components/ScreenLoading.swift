
import SwiftUI

struct ScreenLoadingView: View {
    @State private var isVisible = false

    var body: some View {
        ZStack {
            ProgressView()
                .progressViewStyle(CircularProgressViewStyle())
                .scaleEffect(1.8)
                .tint(Color(UIColor.secondaryLabel))
                .opacity(isVisible ? 0.7 : 0)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .onAppear {
            withAnimation(.easeIn(duration: 0.2).delay(0.3)) {
                isVisible = true
            }
        }
    }
}


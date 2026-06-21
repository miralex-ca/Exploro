
import SwiftUI


struct LoadingScreen: View {
    var showBackground: Bool = false
    @Environment(\.appTheme) var theme
    var body: some View {
        ZStack {
            VStack {
                Spacer()
                CircleProgressBarView()
                Spacer()
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .background(showBackground ? theme.background : Color.clear)
        }
    }
}

struct CircleProgressBarView: View {
    @State var spinCircle = false
    @State var animation = false
    
    @Environment(\.appTheme) var theme
    
    var body: some View {
        ZStack {
            Circle()
                .stroke( theme.onSurface.opacity(0.3), lineWidth: 5)
                .frame(width: 50, height: 50)
            
            Circle()
                .trim(from: 0.0, to: 0.6)
                .stroke(theme.onSurface.opacity(0.9) , lineWidth: 5)
                .frame(width: 50, height: 50)
                .rotationEffect(.degrees(spinCircle ? 0 : -360), anchor: .center)
        }
        .onAppear {
            withAnimation(Animation.linear(duration: 1).repeatForever(autoreverses: false)) {
                self.spinCircle = true
            }
        }
        .opacity(animation ? 0.6 : 0)
        .onAppear {
            withAnimation() {
                animation = true
            }
        }
    }
}

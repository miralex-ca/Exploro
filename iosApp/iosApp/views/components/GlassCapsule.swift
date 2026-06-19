
import SwiftUI

struct GlassCapsuleModifier: ViewModifier {
    
    @Environment(\.appTheme) var theme
    
    func body(content: Content) -> some View {
        if #available(iOS 26, *) {
            content
                .glassEffect(.regular, in: Capsule())
                 
            
        } else {
            content
                .background(
                    .ultraThinMaterial,
                    in: Capsule()
                )
                .overlay {
                    Capsule()
                        .strokeBorder(.white.opacity(0.15))
                }
                .shadow(
                    color: .black.opacity(0.12),
                    radius: 12,
                    y: 4
                )
        }
    }
}

extension View {
    func glassCapsule() -> some View {
        modifier(GlassCapsuleModifier())
    }
}


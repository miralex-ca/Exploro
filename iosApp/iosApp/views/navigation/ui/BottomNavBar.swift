
import SwiftUI
import Shared


struct BottomNavBar: View {
    let navigationActions: NavigationActions
    @EnvironmentObject var appObj: AppObservableObject
    @Environment(\.appTheme) var theme
    
    @Namespace private var selectionAnimation
    
    var body: some View {
        let currentURI = appObj.localNavigationState.currentLevel1ScreenIdentifier.URI
    
        HStack(spacing: 12) {
            HStack(spacing: 16) {
                BottomNavButton(
                    label: Strings.navBrowse,
                    icon: "safari",
                    selectedIcon: "safari.fill",
                    selected: currentURI == Level1Navigation.home.screenIdentifier.URI,
                    namespace: selectionAnimation,
                    onClick: { navigationActions.toLevel1Screen(.home) }
                )
                BottomNavButton(
                    label: Strings.navFavorites,
                    icon: "star",
                    selectedIcon: "star.fill",
                    selected: currentURI == Level1Navigation.favorites.screenIdentifier.URI,
                    namespace: selectionAnimation,
                    onClick: { navigationActions.toLevel1Screen(.favorites) }
                )
            }
            .padding(.horizontal, 8)
            .padding(.vertical, 6)
            .bottomGlassCapsule()
            
            Button(action: navigationActions.toSearch) {
                Image(systemName: "magnifyingglass")
                    .font(.system(size: 28))
                    .foregroundColor(theme.navText)
                    .padding(18)
                    .bottomGlassCapsule()
            }
            .buttonStyle(.plain)
            .accessibilityIdentifier("Search")
        }
        .background(Color.black.opacity(0.001))
    }
}


struct BottomNavButton: View {
    let label: String
    let icon: String
    let selectedIcon: String
    let selected: Bool
    
    let namespace: Namespace.ID
    let onClick: () -> Void
    
    @Environment(\.appTheme) var theme

    var body: some View {
        Button(action: onClick) {
            VStack(spacing: 3) {
                Image(systemName: selected ? selectedIcon : icon)
                    .font(.system(size: 20))
                
                Text(label)
                    .font(.system(size: 12, weight: .medium))
            }
            .foregroundStyle(selected ? theme.navSelected: theme.navText)
            .padding(.horizontal, 24)
            .padding(.vertical, 8)
            .background {
                if selected {
                    Capsule()
                        .fill(.gray.opacity(0.2))
                        .overlay {
                            Capsule()
                                .strokeBorder(
                                    .white.opacity(0.05),
                                    lineWidth: 0.5
                                )
                        }
                        .scaleEffect(selected ? 1.03 : 1.0)
                        .animation(
                            .spring(response: 0.35, dampingFraction: 0.8),
                            value: selected
                        )
                        .matchedGeometryEffect(
                            id: "BOTTOM_TAB_SELECTION",
                            in: namespace
                        )
                }
            }
        }
        .buttonStyle(.plain)
        .accessibilityIdentifier(label)
    }
}


struct BottomGlassCapsuleModifier: ViewModifier {
    
    @Environment(\.appTheme) var theme
    
    func body(content: Content) -> some View {
        if #available(iOS 26, *) {
            content
                .glassEffect(.regular, in: Capsule())
                .background (
                    Capsule()
                        .fill(theme.bottomnNavTint.opacity(0.25))
                )

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
    func bottomGlassCapsule() -> some View {
        modifier(BottomGlassCapsuleModifier())
    }
}


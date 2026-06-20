import SwiftUI
import Shared



struct TopNavBar: View {
    @EnvironmentObject var appObj: AppObservableObject
    let screenNavActions: NavigationActions

    @Environment(\.appLayout) var appLayout

    @Namespace private var selectionAnimation

    var body: some View {

        let currentURI = appObj.localNavigationState.currentLevel1ScreenIdentifier.URI

        HStack(spacing: 12) {

            HStack(spacing: 8) {
                TopTabButton(
                    label: "Sidebar",
                    icon: "sidebar.left",
                    selectedIcon: "sidebar.left",
                    selected: false,
                    isIcon: true,
                    namespace: selectionAnimation
                ) {
                    appLayout.updateUseDrawer(true)
                    AppSession.shared.showDrawerOnLandscape = true
                }

                TopTabButton(
                    label: Strings.homeTitle,
                    icon: "safari",
                    selectedIcon: "safari.fill",
                    selected: currentURI == Level1Navigation.home.screenIdentifier.URI,
                    namespace: selectionAnimation
                ) {
                    withAnimation(.spring(response: 0.35, dampingFraction: 0.8)) {
                        appObj.dkmpNav.navigateByLevel1Menu(
                            appObj,
                            level1Navigation: .home
                        )
                    }
                }

                TopTabButton(
                    label: Strings.navFavorites,
                    icon: "star",
                    selectedIcon: "star.fill",
                    selected: currentURI == Level1Navigation.favorites.screenIdentifier.URI,
                    namespace: selectionAnimation
                ) {
                    withAnimation(.spring(response: 0.35, dampingFraction: 0.8)) {
                        appObj.dkmpNav.navigateByLevel1Menu(
                            appObj,
                            level1Navigation: .favorites
                        )
                    }
                }

                TopTabButton(
                    label: Strings.searchTitle,
                    icon: "magnifyingglass",
                    selectedIcon: "magnifyingglass",
                    selected: false,
                    isIcon: true,
                    namespace: selectionAnimation
                ) {
                    screenNavActions.toSearch()
                }
            }
            .padding(.horizontal, 4)
            .padding(.vertical, 4)
            .topbarGlassCapsule()
        }
        .padding(.top, 6)
        .background(Color.black.opacity(0.001))
    }
}

struct TopTabButton: View {
    let label: String
    let icon: String
    let selectedIcon: String
    let selected: Bool
    var isIcon: Bool = false

    let namespace: Namespace.ID
    let onClick: () -> Void

    @Environment(\.appTheme) var theme

    var body: some View {

        Button(action: onClick) {

            VStack(spacing: 0) {
                if isIcon {
                    Image(
                        systemName: selected
                        ? selectedIcon
                        : icon
                    )
                    .font(.system(size: 20))

                } else {
                    Text(label)
                        .font(.subheadline)
                        .fontWeight(.semibold)
                }
            }
            .foregroundStyle(
                selected
                ? theme.navSelected
                : theme.navText
            )
            .padding(.horizontal, isIcon ? 12 : 16)
            .padding(.vertical, 10)
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
                            id: "TOP_TAB_SELECTION",
                            in: namespace
                        )
                }
            }
        }
        .buttonStyle(.plain)
    }
}


struct TopbarGlassCapsuleModifier: ViewModifier {
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
    func topbarGlassCapsule() -> some View {
        modifier(TopbarGlassCapsuleModifier())
    }
}




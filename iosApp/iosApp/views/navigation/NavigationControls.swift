import SwiftUI


struct NavigationControls: View {
    let navigationActions: NavigationActions

    @EnvironmentObject var appObj: AppObservableObject
    @Environment(\.appLayout) var layout

    var body: some View {
        let isLevel1 = appObj.localNavigationState.topScreenIdentifier.screen.navigationLevel == 1
        let showBottomBar = isLevel1 && layout.useBottomBar
        let showLargeScreenNav = isLevel1 && layout.useLargeScreenNav

        ZStack(alignment: .top) {
            Color.clear.ignoresSafeArea()
            if showLargeScreenNav {
                LargeScreenNavigation(navigationActions: navigationActions)
            }
            
            if showBottomBar {
                BottomNavigation(navigationActions: navigationActions)
            }
        }
    }
}


struct BottomNavigation: View {
    let navigationActions: NavigationActions
    @Environment(\.appTheme) var theme
    
    var body: some View {
        ZStack {
            VStack {
                Spacer()
                ZStack {
                    LinearGradient(
                        colors: [.clear, theme.bottomGradient],
                        startPoint: .top,
                        endPoint: .bottom
                    )
                    .frame(height: 80 + safeAreaBottom)
                    .ignoresSafeArea(edges: .all)
                }
            }
            .ignoresSafeArea(edges: .all)
            
            VStack {
                Spacer()
            }
            .safeAreaInset(edge: .bottom) {
                BottomNavBar(navigationActions: navigationActions)
                    .padding(.bottom, 12)
            }
        }
    }
    
    private var safeAreaBottom: CGFloat {
        UIApplication.shared.connectedScenes
            .compactMap { $0 as? UIWindowScene }
            .first?.windows.first?.safeAreaInsets.bottom ?? 0
    }
}


struct LargeScreenNavigation: View {
    let navigationActions: NavigationActions
    
    @Environment(\.appLayout) var layout
    @State private var hasAppeared = false
    
    var body: some View {
        ZStack(alignment: .top) {
            Color.clear.ignoresSafeArea()
                .onChange(of: layout.isLandscape) { _, isLandscape in
                    if isLandscape {
                        if AppSession.shared.showDrawerOnLandscape {
                            layout.useDrawer = true
                        }
                    } else {
                        layout.useDrawer = false
                    }
                }
            
            ZStack(alignment: .topLeading) {
                Color.clear.ignoresSafeArea()
                if layout.useDrawer {
                    ZStack(alignment: .topLeading) {
                        SideDrawer(navigationActions: navigationActions)
                            .padding(.leading, 12)
                            .padding(.top, 12)
                            .padding(.bottom, 12)
                    }
                    .transition(.revealFromTop.combined(with: .opacity))
                }
            }
            .animation(
                .spring(duration: 0.3).delay(layout.useDrawer ? 0.08 : 0),
                value: layout.useDrawer
            )
            
            ZStack(alignment: .top) {
                Color.clear.ignoresSafeArea()
                if layout.useTopbar {
                    TopNavBar(screenNavActions: navigationActions)
                        .transition(.move(edge: .leading).combined(with: .opacity))
                        .onAppear { hasAppeared = true }
                }
            }
            .animation(
                hasAppeared ? .spring(duration: 0.3).delay(layout.useTopbar ? 0.1 : 0) : nil,
                value: layout.useTopbar
            )
        }
    }
}

struct VerticalRevealFromTop: ViewModifier {
    let progress: CGFloat

    func body(content: Content) -> some View {
        content
            .scaleEffect(
                x: 1,
                y: progress,
                anchor: .top
            )
    }
}

extension AnyTransition {
    static var revealFromTop: AnyTransition {
        .modifier(
            active: VerticalRevealFromTop(progress: 0.01),
            identity: VerticalRevealFromTop(progress: 1)
        )
    }
}


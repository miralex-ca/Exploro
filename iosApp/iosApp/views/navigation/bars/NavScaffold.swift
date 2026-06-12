import SwiftUI
import Shared


enum NavigationType {
    case drawer
    case topbar
    case none
}



struct NavScaffold: View {
    let screenNavActions: ScreenNavActions
    @EnvironmentObject var appObj: AppObservableObject
    @Environment(\.appTheme) var theme
    @Environment(\.appLayout) var layout
    
    @State private var hasAppeared = false
 
    var body: some View {
        let isLevel1 = appObj.localNavigationState.topScreenIdentifier.screen.navigationLevel == 1
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
            
            if layout.useBottomBar {
                VStack {
                    Spacer()
                    FloatingTabBar(
                        onSearch: { screenNavActions.toSearch() }
                    )
                }
            }
            
            if isLevel1 {
                ZStack(alignment: .topLeading) {
                    Color.clear
                    if layout.useDrawer {
                        ZStack (alignment: .topLeading) {
                            SideDrawer(screenNavActions: screenNavActions)
                                .padding(.leading, 12)
                                .padding(.top, 12)
                                .padding(.bottom, 12)
                        }
                        .transition(
                                    .revealFromTop
                                        .combined(with: .opacity)
                                )
                    }
                }
                .animation(
                    .spring(duration: 0.3).delay(layout.useDrawer ? 0.08 : 0),
                    value: layout.useDrawer)
                
 
                ZStack(alignment: .top) {
                    if layout.useTopbar {
                        ZStack {
                            TopTabBar(
                                screenNavActions: screenNavActions
                            )
                        }
                        .transition(
                            .move(edge: .leading)
                            .combined(with: .opacity)
                        )
                        .onAppear {
                            hasAppeared = true
                        }
                    }
                }
                .animation(
                    hasAppeared ? .spring(duration: 0.3).delay(layout.useTopbar ? 0.1 : 0) : nil,
                    value: layout.useTopbar
                )
            }
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


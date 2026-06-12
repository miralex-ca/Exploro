
import SwiftUI


struct Level1ScreenContainer<Content: View>: View {
    let screenTitle : String?
    let isAdjustedPadding: Bool
    let content: Content
    @State private var hasAppeared = false
    
    @Environment(\.appLayout) var appLayout
    
    @State private var hasSpaceForDrawer: Bool = false

    init(
        screenTitle: String? = nil,
        isAdjustedPadding: Bool = true,
        @ViewBuilder content: () -> Content
    ) {
        self.screenTitle = screenTitle
        self.isAdjustedPadding = isAdjustedPadding
        self.content = content()
    }

    var body: some View {
        ScrollView(showsIndicators: false) {
            ZStack(alignment: .top) {
                if let screenTitle, appLayout.useDrawer, appLayout.isLandscape {
                    HStack {
                        Text(screenTitle)
                            .font(.system(size: 25, weight: .semibold))
                            .foregroundColor(.primary)
                        
                        Spacer()
                    }
                    .padding(.leading, 300)
                    .offset(y: -30)
                }
                
                content
                    .padding(.top, appLayout.isTablet ? 20 : 0)
                    .padding(.leading, hasSpaceForDrawer && isAdjustedPadding ? 260 : 0)
            }
        }
        .onChange(of: appLayout.hasSpaceForDrawer, initial: true) { _, newValue in
            if hasAppeared {
                withAnimation(.easeInOut(duration: 0.2)) {
                    hasSpaceForDrawer = newValue
                }
            } else {
                hasSpaceForDrawer = newValue
                hasAppeared = true
            }
            
        }
    }
}

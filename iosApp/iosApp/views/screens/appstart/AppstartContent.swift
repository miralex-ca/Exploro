

import SwiftUI
import Shared



struct AppStartupContent<Content: View>: View {
    let state: AppStartupState
    let onRetry: () -> Void
    let content: Content
   
    init(
        state: AppStartupState,
        onRetry: @escaping () -> Void,
        @ViewBuilder content: () -> Content
    ) {
        self.state = state
        self.onRetry = onRetry
        self.content = content()
    }

    var body: some View {
        switch state {
        case is AppStartupStateLoading:
            AppLoadingScreen()
            
        case let failure as AppStartupStateFailure:
            AppErrorScreen(
                failedAfterSync: failure is AppStartupStateFailureAfterSync,
                onRetry: onRetry
            )
            
        case is AppStartupStateReady:
            content
            
        default:
            AppLoadingScreen()
        }
    }
}



import SwiftUI
import ComposeApp



struct AppStartupContent<Content: View>: View {
    @EnvironmentObject var appObj: AppObservableObject
    let onRetry: () -> Void
    @ViewBuilder let content: () -> Content

    var body: some View {
        switch appObj.startupState {
        case is AppStartupStateLoading:
            AppLoadingScreen()
        case let failure as AppStartupStateFailure:
            AppErrorScreen(
                failedAfterSync: failure is AppStartupStateFailureAfterSync,
                onRetry: onRetry
            )
        case is AppStartupStateReady:
            content()
        default:
            AppLoadingScreen()
        }
    }
}


struct AppStartupGate<Content: View>: View {
    @EnvironmentObject var appObj: AppObservableObject
    let onRetry: () -> Void
    @ViewBuilder let content: () -> Content

    var body: some View {
        switch appObj.startupState {
        case is AppStartupStateLoading:
            AppLoadingScreen()
        case let failure as AppStartupStateFailure:
            AppErrorScreen(
                failedAfterSync: failure is AppStartupStateFailureAfterSync,
                onRetry: onRetry
            )
        case is AppStartupStateReady:
            content()
        default:
            AppLoadingScreen()
        }
    }
}

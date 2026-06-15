
import SwiftUI
import Shared

struct AppErrorScreen: View {
    let failedAfterSync: Bool
    let onRetry: () -> Void
    
    @Environment(\.appTheme) var theme
    
    var body: some View {
        ZStack {
            theme.background.ignoresSafeArea()
            
            GeometryReader { geo in
                
                ScrollView(showsIndicators: false) {
                    
                    VStack {
                        Spacer()
                        
                        VStack(spacing: 0) {
                            Image(systemName: "exclamationmark.circle")
                                .font(.system(size: 35))
                                .foregroundStyle(theme.onSurfaceVariant)
                            
                            Spacer().frame(height: 20)
                            
                            Text(Strings.appErrorTryAgain)
                                .font(.title3.weight(.semibold))
                                .multilineTextAlignment(.center)
                            
                            Spacer().frame(height: 28)
                            
                            Text(failedAfterSync
                                 ? Strings.appStartupErrorSyncDesc
                                 : Strings.appStartupErrorDesc
                            )
                            .font(.body)
                            .foregroundStyle(theme.onSurfaceVariant)
                            .lineLimit(4)
                            .multilineTextAlignment(.center)
                            
                            Spacer().frame(height: 44)
                            
                            Button(action: onRetry) {
                                Text(Strings.appErrorTryAgain)
                                    .padding(.horizontal, 16)
                                    .padding(.vertical, 4)
                            }
                            .buttonStyle(.bordered)
                        }
                        .padding(28)
                        .frame(maxWidth: 360)
                        .background(theme.cardSurface)
                        .clipShape(RoundedRectangle(cornerRadius: 16))
                        .overlay(
                            RoundedRectangle(cornerRadius: 16)
                                .stroke(Color.gray.opacity(0.2), lineWidth: 0.5)
                        )
                        .padding(.init(top: 10, leading: 32, bottom: 32, trailing: 32))
                        
                        
                        Spacer()
                    }
                    .frame(minHeight: geo.size.height)
                    
                }
                .frame(maxWidth: .infinity)
                
                
            }
            
        }
    }
}

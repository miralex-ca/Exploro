
import SwiftUI

struct EmptyStateView: View {
    let state: EmptyState
    @Environment(\.appTheme) var theme
    
    var body: some View {
        ScrollView {
            VStack(alignment: .center, spacing: 0) {
                VStack(spacing: 0) {
                    VStack(spacing: 0) {
                        Image(systemName: state.icon)
                            .font(.system(size: 52))
                            .foregroundColor(theme.onSurface)
                            .opacity(0.7)
                        
                        Spacer().frame(height: 16)
                        
                        Text(state.title)
                            .font(.appHeadline)
                            .foregroundColor(theme.onSurface)
                        
                        Spacer().frame(height: 8)
                        
                        Text(state.message)
                            .font(.appSubheadline)
                            .foregroundColor(theme.onSurface)
                            .multilineTextAlignment(.center)
                            .opacity(0.8)
                    }
                    .padding(28)
                }
                .frame(maxWidth: 360)
                .background(theme.cardSurface)
                .clipShape(RoundedRectangle(cornerRadius: 20))
                .padding(24)
                .padding(.top, 30)
            }
            .frame(maxWidth: .infinity)
        }
    }
}

enum EmptyState {
    case noResults
    case emptyList
    case notFound
    
    var title: String {
        switch self {
        case .noResults: return Strings.emptyTitleNoResults
        case .emptyList: return Strings.emptyTitleEmptyList
        case .notFound:  return Strings.emptyTitleNotFound
        }
    }
    
    var message: String {
        switch self {
        case .noResults: return Strings.emptyMsgNoResults
        case .emptyList: return Strings.emptyMsgEmptyList
        case .notFound:  return Strings.emptyMsgNotFound
        }
    }
    
    var icon: String {
        switch self {
        case .noResults: return "magnifyingglass"
        case .emptyList: return "tray"
        case .notFound:  return "info.circle"
        }
    }
}




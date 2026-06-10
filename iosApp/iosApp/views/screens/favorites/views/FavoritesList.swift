 

import SwiftUI
import Shared


struct FavoritesList: View {
    let items: [FavoriteListItem]
    let onEvent: (FavoritesUiEvent) -> Void

    @EnvironmentObject var appObj: AppObservableObject

    var body: some View {
        let isSwipeEnabled: Bool = appObj.appEnvironment.favoriteSwipeEnabled
        
        List {
            ForEach(items, id: \.id) { item in
                FavoriteListRow(
                    item: item,
                    isSwipeEnabled: isSwipeEnabled,
                    onClick: { onEvent(.onItemClicked(item)) },
                    onRemove: { onEvent(.removeFavorite(item.id)) }
                )
                .if(isSwipeEnabled) { view in
                    view.swipeActions(edge: .trailing, allowsFullSwipe: true) {
                        Button(role: .destructive) {
                            onEvent(.removeFavorite(item.id))
                        } label: {
                            Label("Delete", systemImage: "trash")
                        }
                    }
                }
 
                .listRowInsets(EdgeInsets(top: 4, leading: 16, bottom: 4, trailing: 16))
                .listRowBackground(Color.clear)
                .listRowSeparator(.hidden)
            }
        }
        .listStyle(.plain)
    }
}

struct FavoriteListRow: View {
    let item: FavoriteListItem
    let isSwipeEnabled: Bool
    let onClick: () -> Void
    let onRemove: () -> Void
    
    @Environment(\.appTheme) var theme
    
    var body: some View {
        Button(action: onClick) {
            HStack(spacing: 16) {
                RemoteImage(
                    url: item.flagPngUrl,
                    size: CGSize(width: 90, height: 55)
                )
                .scaledToFill()
                .frame(width: 90, height: 55)
                .clipped()
                .clipShape(RoundedRectangle(cornerRadius: 6))
                .overlay(
                    RoundedRectangle(cornerRadius: 6)
                        .stroke(Color.gray.opacity(0.45), lineWidth: 1)
                )
                
                VStack(alignment: .leading, spacing: 4) {
                    Text(item.name)
                        .font(.headline)
                        .lineLimit(1)
                    
                    Text(item.subregion)
                        .font(.subheadline)
                        .foregroundStyle(.secondary)
                        .lineLimit(1)
                }
                
                Spacer()
            }
            .padding(10)
            .frame(maxWidth: .infinity, alignment: .leading)
            .contentShape(Rectangle())
            .if(!isSwipeEnabled) { view in
                view.overlay(alignment: .bottomTrailing) {
                    Menu {
                        Button {
                            onClick()
                        } label: {
                            Label("View", systemImage: "eye")
                        }
                        Button(role: .destructive) {
                            onRemove()
                        } label: {
                            Label("Remove", systemImage: "trash")
                        }
                    } label: {
                        Image(systemName: "ellipsis")
                            .foregroundStyle(.secondary)
                            .padding(8)
                    }
                    .onTapGesture {}
                }
            }
        }
        .buttonStyle(.plain)
        .background(theme.cardSurface)
        .clipShape(RoundedRectangle(cornerRadius: 12))
        .overlay(
            RoundedRectangle(cornerRadius: 12)
                .stroke(Color.gray.opacity(0.2), lineWidth: 1)
        )
        
    }
}

extension View {
    @ViewBuilder
    func `if`<Content: View>(_ condition: Bool, transform: (Self) -> Content) -> some View {
        if condition {
            transform(self)
        } else {
            self
        }
    }
}

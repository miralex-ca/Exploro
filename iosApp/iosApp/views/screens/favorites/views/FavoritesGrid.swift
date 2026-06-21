import SwiftUI
import Shared

struct FavoritesGrid: View {
    let items: [FavoriteListItem]
    let onEvent: (FavoritesUiEvent) -> Void
    
    @Environment(\.appLayout) var appLayout

    let columns = [GridItem(.adaptive(minimum: 280), spacing: 8)]

    var body: some View {
        ScrollView {
            LazyVGrid(columns: columns, spacing: 8) {
                ForEach(items, id: \.id) { item in
                    FavoriteGridCell(
                        item: item,
                        onClick: { onEvent(.onItemClicked(item)) },
                        onRemove: { onEvent(.removeFavorite(item.id)) }
                    )
                }
            }
            .padding(.horizontal, 36)
            .padding(.vertical, Dimens.Favorites.topPadding.of(appLayout))
        }
        .scrollClipDisabled()
    }
}

struct FavoriteGridCell: View {
    let item: FavoriteListItem
    let onClick: () -> Void
    let onRemove: () -> Void
    
    @Environment(\.appTheme) var theme
    
    var body: some View {
        Button(action: onClick) {
            
            HStack(spacing: 12) {
                RemoteImage(url: item.flagPngUrl, size: CGSize(width: 120, height: 70))
                    .scaledToFill()
                    .frame(width: 120, height: 70)
                    .clipped()
                    .clipShape(RoundedRectangle(cornerRadius: 6))
                    .overlay(
                        RoundedRectangle(cornerRadius: 6)
                            .stroke(Color.gray.opacity(0.45), lineWidth: 1)
                    )
                
                VStack(alignment: .leading, spacing: 4) {
                    Text(item.name)
                        .font(.appHeadline)
                        .lineLimit(1)
                    Text(item.subregion)
                        .font(.appSubheadline)
                        .foregroundStyle(.secondary)
                        .lineLimit(1)
                }
                .padding(.trailing, 6)
                
                Spacer()
                
            }
            .padding(10)
            .frame(maxWidth: .infinity, alignment: .leading)
            .contentShape(Rectangle())
            
            .overlay(alignment: .bottomTrailing) {
                Menu {
                    Button {
                        onClick()
                    } label: {
                        Label(Strings.commonView, systemImage: "eye")
                    }
                    Button(role: .destructive) {
                        onRemove()
                    } label: {
                        Label(Strings.commonRemove, systemImage: "trash")
                    }
                } label: {
                    Image(systemName: "ellipsis")
                        .foregroundStyle(.secondary)
                        .padding(8)
                }
                .onTapGesture {}
            }
            
        }
        .buttonStyle(.plain)
        .background(theme.cardSurface)
        .clipShape(RoundedRectangle(cornerRadius: 12))
        .overlay(
            RoundedRectangle(cornerRadius: 12)
                .stroke(Color.gray.opacity(0.2), lineWidth: 1)
        )
        .contextMenu {
            Button { onClick() } label: { Label("View", systemImage: "eye") }
            Button(role: .destructive) { onRemove() } label: { Label("Remove", systemImage: "trash") }
        }
    }
}


import SwiftUI
import Shared


enum FavoritesUiEvent {
    case removeFavorite(countryId: String)
    case itemClicked(FavoriteListItem)
}


final class FavoritesEventHandler {
    let events: Events

    init(
        events: Events
    ) {
        self.events = events
    }

    func onEvent(_ event: FavoritesUiEvent) {
        switch event {

        case .itemClicked(let item): print("")
//            navActions.toDetailFromList(
//                navParams: item.toDetailsNavParams()
//            )

        case .removeFavorite(let countryId):
            events.removeFavoriteBySwipe(code: countryId)
        }
    }
}





struct FavoritesScreen: View {

    let screenState: FavoritesScreenState
    //let eventHandler: FavoritesEventHandler
    let onItemClick: (FavoriteListItem) -> Void

    var body: some View {

        if screenState.isLoading {
            ProgressView()
        } else if screenState.favorites.isEmpty {
            ContentUnavailableView(
                "No Favorites",
                systemImage: "star"
            )
        } else {
            ScrollView {
                LazyVStack(spacing: 8) {

                    ForEach(screenState.favorites, id: \.id) { item in
                        FavoriteListRow(
                            item: item,
                            onClick: {
                                onItemClick(item)
                            }
                        )
                    }
                }
                .padding()
                .frame(maxWidth: 600)
            }
        }
    }
}

struct FavoriteListRow: View {

    let item: FavoriteListItem
    let onClick: () -> Void

    var body: some View {
        Button(action: onClick) {
            HStack(spacing: 12) {

                AsyncImage(
                    url: URL(string: item.flagPngUrl)
                ) { image in
                    image
                        .resizable()
                        .scaledToFill()
                } placeholder: {
                    ProgressView()
                }
                .frame(width: 90, height: 60)
                .clipShape(
                    RoundedRectangle(cornerRadius: 6)
                )

                VStack(
                    alignment: .leading,
                    spacing: 4
                ) {
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
            .frame(maxWidth: .infinity, alignment: .leading)
            .padding(12)
            .contentShape(Rectangle())
        }
        .buttonStyle(.plain)
        .background(.regularMaterial)
        .clipShape(
            RoundedRectangle(cornerRadius: 12)
        )
    }
}


 


import SwiftUI
import Shared


struct SectionScreen: View {
    let screenState: SectionScreenState
    let eventHandler: SectionEventHandler
    
    var body: some View {
        if screenState.isLoading {
            ScreenLoadingView()
        } else {
            SectionScreenContent(
                screenState: screenState,
                onListItemClick: { eventHandler.onEvent(.onItemClicked($0)) }
            )
        }
    }
}

struct SectionScreenContent: View {
    let screenState: SectionScreenState
    let onListItemClick: (SectionListItem) -> Void
    
    let columns = [GridItem(.adaptive(minimum: 160))]
    
    var body: some View {
        if screenState.countries.isEmpty {
           // EmptyStateView()
            EmptyView()
        } else {
            ScrollView {
                LazyVGrid(columns: columns, spacing: 12) {
                    ForEach(screenState.countries, id: \.id) { item in
                        CountryGridCard(
                            item: item,
                            onClick: { onListItemClick(item) }
                        )
                    }
                }
                .padding(12)
            }
        }
    }
}

struct CountryGridCard: View {
    let item: SectionListItem
    let onClick: () -> Void
    
    @Environment(\.appTheme) var theme

    var body: some View {
        Button(action: onClick) {
            VStack(alignment: .leading, spacing: 0) {
                GeometryReader { geo in
                    RemoteImage(
                        url: item.flagPngUrl,
                        size: CGSize(width: geo.size.width, height: geo.size.width * 0.6)
                    )
                    .scaledToFill()
                    .frame(width: geo.size.width, height: geo.size.width * 0.6)
                    .clipped()
                }
                .aspectRatio(5/3, contentMode: .fit)  // ← fixed ratio, adapts to grid width
                .clipShape(RoundedRectangle(cornerRadius: 6))
                .overlay(
                    RoundedRectangle(cornerRadius: 6)
                        .stroke(Color.gray.opacity(0.25), lineWidth: 1)
                )
                .padding(8)

                VStack(alignment: .leading, spacing: 2) {
                    Text(item.name)
                        .font(.headline)
                        .lineLimit(1)

                    Text(item.subregion)
                        .font(.caption)
                        .lineLimit(1)
                        .foregroundStyle(.secondary)
                }
                .padding(.horizontal, 12)
                .padding(.top, 4)
                .padding(.bottom, 10)
            }
            .frame(maxWidth: .infinity, alignment: .leading)
            .background(theme.cardSurface)
            .clipShape(RoundedRectangle(cornerRadius: 10))
            .overlay(
                RoundedRectangle(cornerRadius: 10)
                    .stroke(Color.gray.opacity(0.2), lineWidth: 1)
            )
        }
        .buttonStyle(.plain)
    }
}

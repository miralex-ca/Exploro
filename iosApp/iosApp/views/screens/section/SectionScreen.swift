import SwiftUI
import Shared


struct SectionScreen: View {
    let screenState: SectionScreenState
    let eventHandler: SectionEventHandler
    
    var body: some View {
        if screenState.isLoading {
            LoadingScreen()
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
    
    @Environment(\.appLayout) var appLayout
    
    var body: some View {
        let spacing = Dimens.Section.cardSpacing.of(appLayout)
        
        let columns = [GridItem(
            .adaptive(
                minimum: Dimens.Section.gridItemMinWidth.of(appLayout)),
                spacing: spacing
            )
        ]
        
        if screenState.countries.isEmpty {
            EmptyStateView(state: .emptyList)
        } else {
            ScrollView(showsIndicators: false) {
                
                ZStack {
                    ScrollView(showsIndicators: false) {
                        LazyVGrid(columns: columns, spacing: spacing) {
                            ForEach(screenState.countries, id: \.id) { item in
                                CountryGridCard(
                                    name: item.name,
                                    flagImage: item.flagImage,
                                    location: item.location,
                                    onClick: { onListItemClick(item) }
                                )
                            }
                        }
                        .padding(.horizontal, Dimens.Section.gridHPadding.of(appLayout))
                        .padding(.vertical, Dimens.Section.gridVPadding.of(appLayout))
                        .frame(maxWidth: 1000)
                    }
                }
                .frame(maxWidth: .infinity)
            }
            .frame(maxWidth: .infinity)
        }
    }
}

struct CountryGridCard: View {
    let name: String
    let flagImage: String
    let location: String
    let onClick: () -> Void
    
    @Environment(\.appTheme) var theme
    @Environment(\.appLayout) var appLayout

    var body: some View {
        Button(action: onClick) {
            VStack(alignment: .leading, spacing: 0) {
                GeometryReader { geo in
                    RemoteImage(
                        url: flagImage,
                        size: CGSize(width: geo.size.width, height: geo.size.width * 0.55)
                    )
                    .scaledToFill()
                    .frame(width: geo.size.width, height: geo.size.width * 0.55)
                    .clipped()
                }
                .aspectRatio(100/55, contentMode: .fit)
                .clipShape(RoundedRectangle(cornerRadius: 6))
                .overlay(
                    RoundedRectangle(cornerRadius: 6)
                        .stroke(Color.gray.opacity(0.25), lineWidth: 1)
                )
                .padding(8)

                VStack(alignment: .leading, spacing: 2) {
                    Text(name)
                        .font(.appHeadline)
                        .lineLimit(1)

                    Text(location)
                        .font(.appCaption)
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

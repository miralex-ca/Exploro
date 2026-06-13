
import SwiftUI
import Shared


struct LargeDetailsContent: View {
    let details: CountryDetailsState
    let onEvent: (DetailsUiEvent) -> Void

    var body: some View {
        VStack(spacing: 0) {

            LargeDetailsHeaderCard(
                details: details,
                onFavoriteClick: {
                    onEvent(.toggleFavorite(details.id))
                }
            )

            LargeDetailsInfoCard(
                details: details
            )
        }
        .padding(16)
        .frame(maxWidth: 800)
    }
}


struct LargeDetailsHeaderCard: View {
    let details: CountryDetailsState
    let onFavoriteClick: () -> Void

    @Environment(\.appTheme) var theme

    var body: some View {

        HStack(alignment: .top, spacing: 24) {
            VStack(alignment: .leading) {
                CountryHeaderTitle(
                    officialName: details.officialName,
                    coatOfArmsUrl: details.coatOfArmsUrl,
                    capital: details.capital,
                    region: details.continent,
                    isCentered: false
                )
            }
            .padding(.leading, 20)
            .frame(maxWidth: .infinity)

            FlagContainer(
                flagUrl: details.flagUrl,
                flagAlt: details.flagAlt,
                isFavorite: details.isFavorite,
                onFavoriteClick: onFavoriteClick
            )
            .frame(maxWidth: 400)
        }
        .padding(.horizontal, 16)
        .padding(.top, 10)
        .padding(.bottom, 24)
        .background(theme.cardSurface)
        .clipShape(
            UnevenRoundedRectangle(
                topLeadingRadius: 16,
                bottomLeadingRadius: 0,
                bottomTrailingRadius: 0,
                topTrailingRadius: 16
            )
        )
    }
}


struct LargeDetailsInfoCard: View {
    let details: CountryDetailsState

    @Environment(\.appTheme) var theme
    @Environment(\.appLayout) var appLayout

    var body: some View {

        let rows = detailRows(details)

        VStack(spacing: Dimens.Details.infoPadding.of(appLayout)) {
            ForEach(
                Array(rows.chunked(into: 2).enumerated()),
                id: \.offset
            ) { _, pair in

                HStack(alignment: .top, spacing: 24) {
                    ForEach(pair, id: \.label) { row in
                        DetailsInfoRow(row: row)
                            .frame(maxWidth: .infinity)
                    }

                    if pair.count == 1 {
                        Spacer()
                            .frame(maxWidth: .infinity)
                    }
                }
            }

            Spacer() .frame(height: 10)
        }
        .padding(.leading, 36)
        .padding(.trailing, 24)
        .padding(.vertical, 30)
        .background(theme.cardSurface)
        .clipShape(
            UnevenRoundedRectangle(
                topLeadingRadius: 0,
                bottomLeadingRadius: 16,
                bottomTrailingRadius: 16,
                topTrailingRadius: 0
            )
        )
        .padding(.top, 1)
        .padding(.bottom, 60)
    }
}


extension Array {

    func chunked(into size: Int) -> [[Element]] {
        stride(from: 0, to: count, by: size).map {
            Array(self[$0..<Swift.min($0 + size, count)])
        }
    }
}

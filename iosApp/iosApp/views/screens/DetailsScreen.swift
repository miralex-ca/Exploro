
import SwiftUI
import Shared

struct DetailsScreen: View {
    let screenState: DetailsScreenState
    let onFavoriteClick: (String)-> Void

    var body: some View {

        if screenState.isLoading {
            ProgressView()
        } else if let details = screenState.details {
            DetailsContent(
                details: details,
                onFavoriteClick: {
                    onFavoriteClick(details.id)
                }
            )
        } else {
            ContentUnavailableView(
                "Country not found",
                systemImage: "globe"
            )
        }
    }
}

struct DetailsContent: View {
    let details: CountryDetailsState
    let onFavoriteClick: () -> Void

    var body: some View {

        ScrollView {

            VStack(spacing: 20) {

                
                ZStack {
//                    Rectangle()
//                        .frame(height: 180)
                    
                    AsyncImage(
                        url: URL(string: details.flagUrl)
                    ) { image in
                        image
                            .resizable()
                            .scaledToFill()
                    } placeholder: {
                         EmptyView()
                    }
                    .frame(height: 180)
                    .blur(radius: 30)
                    .clipShape(
                        Rectangle()
                    )
                    
                    
                    AsyncImage(
                        url: URL(string: details.flagUrl)
                    ) { image in
                        image
                            .resizable()
                            .scaledToFit()
                    } placeholder: {
                        ProgressView()
                    }
                    .frame(width: 200, height: 120)
                    .clipShape(
                        RoundedRectangle(cornerRadius: 6)
                    )
                    .shadow(radius: 14)
                }
                

                HStack {

                    Text(details.officialName)
                        .font(.title2)
                        .fontWeight(.bold)

                    Spacer()

                    Button(action: onFavoriteClick) {
                        Image(
                            systemName: details.isFavorite
                            ? "star.fill"
                            : "star"
                        )
                    }
                }

                DetailRow(
                    title: "Capital",
                    value: details.capital
                )

                DetailRow(
                    title: "Region",
                    value: details.continent
                )

                DetailRow(
                    title: "Population",
                    value: details.population.description
                )

                DetailRow(
                    title: "Area",
                    value: "\(Int(details.area)) km²"
                )

                DetailRow(
                    title: "Currency",
                    value: details.currency
                )

                DetailRow(
                    title: "Languages",
                    value: details.languages.joined(separator: ", ")
                )
            }
            .padding()
        }
    }
}

struct DetailRow: View {

    let title: String
    let value: String

    var body: some View {

        VStack(alignment: .leading, spacing: 4) {

            Text(title)
                .font(.caption)
                .foregroundStyle(.secondary)

            Text(value)
        }
        .frame(maxWidth: .infinity, alignment: .leading)
    }
}


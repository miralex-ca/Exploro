import SwiftUI
import Shared


struct SectionScreen: View {

    let screenState: SectionScreenState
    let onItemClick: (SectionListItem) -> Void

    var body: some View {

        if screenState.isLoading {
            ProgressView()
        } else if screenState.countries.isEmpty {
            ContentUnavailableView(
                "No countries",
                systemImage: "globe"
            )
        } else {
            ScrollView {
                LazyVGrid(
                    columns: [
                        GridItem(.adaptive(minimum: 160))
                    ],
                    spacing: 12
                ) {

                    ForEach(screenState.countries, id: \.id) { item in
                        CountryGridCard(
                            item: item,
                            onClick: {
                                onItemClick(item)
                            }
                        )
                    }
                }
                .padding()
            }
        }
    }
}

struct CountryGridCard: View {

    let item: SectionListItem
    let onClick: () -> Void

    var body: some View {

        Button(action: onClick) {

            VStack(alignment: .leading, spacing: 8) {

//                Rectangle()
//                    .frame(height: 100)
//                
                AsyncImage(
                    url: URL(string: item.flagPngUrl)
                ) { image in
                    image
                        .resizable()
                        .scaledToFit()
                } placeholder: {
                    ProgressView()
                }
                .frame(height: 100)
               // .frame(maxWidth: .infinity)
                .clipShape(
                    RoundedRectangle(cornerRadius: 6)
                )

                Text(item.name)
                    .font(.headline)
                    .lineLimit(1)

                Text(item.subregion)
                    .font(.caption)
                    .lineLimit(1)
                    .foregroundStyle(.secondary)
            }
            .padding()
            .frame(maxWidth: .infinity, alignment: .leading)
        }
        .buttonStyle(.plain)
    }
}

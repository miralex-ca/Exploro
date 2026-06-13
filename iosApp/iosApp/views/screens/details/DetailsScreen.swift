
import SwiftUI
import Shared


struct DetailsScreen: View {
    let screenState: DetailsScreenState
    let eventHandler: DetailsEventHandler
    
    var body: some View {
        if screenState.isLoading {
            ScreenLoadingView()
        } else if let details = screenState.details {
            DetailsScreenContent(
                details: details,
                onEvent: eventHandler.onEvent
            )
        } else {
            EmptyView()
        }
    }
}


struct DetailsScreenContent: View {
    let details: CountryDetailsState
    let onEvent: (DetailsUiEvent) -> Void
    
    @Environment(\.appLayout) var appLayout
    
    var body: some View {
        ScrollView {
            
            if appLayout.isLandscape {
                LargeDetailsContent(
                    details: details,
                    onEvent: onEvent
                )
            } else {
                ZStack (alignment: .top) {
                    Color.clear
                    VStack(spacing: 0) {
                        DetailsHeaderCard(
                            details: details,
                            onFavoriteClick: { onEvent(.toggleFavorite(details.id)) }
                        )
                        DetailsInfoCard(details: details)
                    }
                    .padding(16)
                    .frame(maxWidth: 600)  // ← add this
                }
            }
            
        }
    }
}

struct DetailsHeaderCard: View {
    let details: CountryDetailsState
    let onFavoriteClick: () -> Void
    
    @Environment(\.appTheme) var theme
    
    var body: some View {
        VStack(spacing: 0) {
            FlagContainer(
                flagUrl: details.flagUrl,
                flagAlt: details.flagAlt,
                isFavorite: details.isFavorite,
                onFavoriteClick: onFavoriteClick
            )
            
            CountryHeaderTitle(
                officialName: details.officialName,
                coatOfArmsUrl: details.coatOfArmsUrl,
                capital: details.capital,
                region: details.continent
            )
            .padding(.bottom, 28)
            .padding(.horizontal, 16)
            
        }
        .padding(.horizontal, 10)
        .padding(.top, 10)
        .background(theme.cardSurface)
        .clipShape(UnevenRoundedRectangle(
            topLeadingRadius: 16,
            bottomLeadingRadius: 0,
            bottomTrailingRadius: 0,
            topTrailingRadius: 16
        ))
        .overlay(
            UnevenRoundedRectangle(
                topLeadingRadius: 16,
                bottomLeadingRadius: 0,
                bottomTrailingRadius: 0,
                topTrailingRadius: 16
            )
            .stroke(Color.gray.opacity(0.2), lineWidth: 0.5)
        )
    }
}


// MARK: - Flag Container

struct FlagContainer: View {
    let flagUrl: String
    let flagAlt: String?
    let isFavorite: Bool
    let onFavoriteClick: () -> Void
    
    @Environment(\.appLayout) var appLayout
    
    var body: some View {
        ZStack {
            Color.black
                .opacity(0.15)
                .background(
                    RemoteImage(url: flagUrl, size: CGSize(width: 400, height: 200))
                        .scaledToFill()
                        .blur(radius: 20)
                        .opacity(0.45)
                )
            
            RemoteImage(url: flagUrl, size: CGSize(width: 300, height: 140))
                .scaledToFit()
                .frame(height: 140)
                .clipShape(RoundedRectangle(cornerRadius: 6))
                .shadow(radius: 8)
            
            VStack {
                if appLayout.isPhone {
                    Spacer()
                }
                HStack {
                    
                    Spacer()
                    FavoriteButton(
                        isFavorite: isFavorite,
                        onClick: onFavoriteClick
                    )
                    .padding(6)
                }
                if !appLayout.isPhone {
                    Spacer()
                }
            }
        }
        .frame(maxWidth: .infinity)  // ← constrain ZStack, not the image
        .frame(height: 200)
        
        .clipShape(RoundedRectangle(cornerRadius: 12))
        .clipped()  // ← extra safety to prevent overflow
    }
}

// MARK: - Favorite Button

struct FavoriteButton: View {
    let isFavorite: Bool
    let onClick: () -> Void
    
    var body: some View {
        Button(action: onClick) {
            ZStack {
                Circle()
                    .fill(Color.black.opacity(0.42))
                    .frame(width: 38, height: 38)
                
                Image(systemName: isFavorite ? "star.fill" : "star")
                    .font(.system(size: 18))
                    .foregroundColor(isFavorite ? .yellow : .white)
            }
        }
        .frame(width: 48, height: 48)
    }
}


// MARK: - Country Header Title

struct CountryHeaderTitle: View {
    let officialName: String
    let coatOfArmsUrl: String
    let capital: String
    let region: String
    var isCentered: Bool = true
    
    var body: some View {
        VStack(spacing: 0) {
            Text(officialName)
                .font(.system(size: isCentered ? 26 : 24, weight: .medium))
                .multilineTextAlignment(
                    isCentered ? .center : .leading
                )
                .lineLimit(4)
                .frame(
                    maxWidth: .infinity,
                    minHeight: isCentered ? 30 : 45,
                    alignment: isCentered ? .center : .topLeading
                )
                .padding(.vertical, 6)
            
            Spacer().frame(height: 16)
            
            HStack(alignment: .top, spacing: 24) {
                if !coatOfArmsUrl.isEmpty {
                    CoatOfArmsImage(url: coatOfArmsUrl)
                }
                
                VStack(alignment: .leading, spacing: 8) {
                    if !capital.isEmpty {
                        InlineHeaderDetailRow(label: "Capital", value: capital.uppercased())
                    }
                    InlineHeaderDetailRow(label: "Region", value: region)
                }
                .padding(.top, 4)
                .padding(.trailing, 15)
            }
            .frame(
                maxWidth: .infinity,
                alignment: isCentered ? .center : .leading
            )
        }
        .padding(.top, 12)
    }
}


// MARK: - Coat of Arms

struct CoatOfArmsImage: View {
    let url: String
    @State private var showDialog = false
    
    var body: some View {
        Button(action: { showDialog = true }) {
            RemoteImage(url: url, size: CGSize(width: 52, height: 52))
                .scaledToFit()
                .frame(width: 40, height: 40)
                .padding(6)
        }
        .frame(width: 52, height: 52)
        .background(Color(hex: "D5DEEC").opacity(0.68))
        .clipShape(RoundedRectangle(cornerRadius: 8))
        .overlay(
            RoundedRectangle(cornerRadius: 8)
                .stroke(Color(hex: "C9D3E3").opacity(0.85), lineWidth: 0.5)
        )
        .sheet(isPresented: $showDialog) {
            VStack(spacing: 20) {
                Text("Coat of Arms")
                    .font(.headline)
                RemoteImage(url: url, size: CGSize(width: 180, height: 180))
                    .scaledToFit()
                    .frame(width: 180, height: 180)
                Button("Close") { showDialog = false }
            }
            .padding()
            .presentationDetents([.medium])
        }
    }
}


// MARK: - Inline Detail Row

struct InlineHeaderDetailRow: View {
    let label: String
    let value: String
    
    var body: some View {
        HStack(alignment: .top, spacing: 0) {
            Text(label)
                .frame(width: 55, alignment: .leading)
                .font(.subheadline)
            
            Text(value)
                .font(.subheadline)
                .fontWeight(.semibold)
                .lineLimit(2)
        }
    }
}

// MARK: - Info Card

struct DetailsInfoCard: View {
    let details: CountryDetailsState
    
    @Environment(\.appTheme) var theme
    
    var body: some View {
        VStack(spacing: 0) {
            ForEach(detailRows(details), id: \.label) { row in
                DetailsInfoRow(row: row)
            }
            Spacer().frame(height: 16)
        }
        .padding(.horizontal, 24)
        .padding(.vertical, 20)
        .background(theme.cardSurface)
        .clipShape(UnevenRoundedRectangle(
            topLeadingRadius: 0,
            bottomLeadingRadius: 16,
            bottomTrailingRadius: 16,
            topTrailingRadius: 0
        ))
        .overlay(
            UnevenRoundedRectangle(
                topLeadingRadius: 0,
                bottomLeadingRadius: 16,
                bottomTrailingRadius: 16,
                topTrailingRadius: 0
            )
            .stroke(Color.gray.opacity(0.2), lineWidth: 0.5)
        )
        .padding(.top, 1)
        .padding(.bottom, 60)
    }
}

// MARK: - Info Row

struct DetailsInfoRow: View {
    let row: DetailsRowModel
    
    var body: some View {
        if !row.value.isEmpty {
            Group {
                if let url = row.url, let link = URL(string: url) {
                    Link(destination: link) {
                        rowContent
                    }
                    .tint(Color.primary)
                    
                } else {
                    rowContent
                }
            }
        }
    }
    
    var rowContent: some View {
        HStack(alignment: .top, spacing: 0) {
            Image(systemName: row.systemIcon)
                .frame(width: 28, height: 28)
                .opacity(0.8)
                .padding(.top, 6)
            
            Spacer().frame(width: 20)
            
            VStack(alignment: .leading, spacing: 2) {
                Text(row.label)
                    .font(.caption)
                    .opacity(0.7)
                
                HStack(spacing: 4) {
                    Text(row.value)
                        .font(.body)
                    
                    if row.url != nil {
                        Image(systemName: "arrow.up.right")
                            .font(.caption)
                            .opacity(0.6)
                    }
                }
            }
            .frame(maxWidth: .infinity, alignment: .leading)
        }
        .padding(.vertical, 10)
        .padding(.trailing, 10)
    }
}



// MARK: - Detail Rows

struct DetailsRowModel {
    let systemIcon: String  // SF Symbol name instead of ImageVector
    let label: String
    let value: String
    let url: String?
    
    init(systemIcon: String, label: String, value: String, url: String? = nil) {
        self.systemIcon = systemIcon
        self.label = label
        self.value = value
        self.url = url
    }
}

func detailRows(_ details: CountryDetailsState) -> [DetailsRowModel] {
    [
        DetailsRowModel(systemIcon: "map", label: "Location", value: details.subregion, url: details.mapsUrl.isEmpty ? nil : details.mapsUrl),
        DetailsRowModel(systemIcon: "ruler", label: "Area", value: formatArea(details.area)),
        DetailsRowModel(systemIcon: "person.2", label: "Population", value: details.population.toHumanReadable()),
        DetailsRowModel(systemIcon: "character.bubble", label: details.languages.count == 1 ? "Language" : "Languages", value: details.languages.joined(separator: ", ")),
        DetailsRowModel(systemIcon: "dollarsign.circle", label: "Currency", value: details.currency),
        DetailsRowModel(systemIcon: "clock", label: "Timezones", value: formatTimezones(details.timezones)),
    ].filter { !$0.value.isEmpty }
}

func formatArea(_ area: Double) -> String {
    if area >= 1_000_000 {
        return String(format: "%.1fM km²", area / 1_000_000)
    } else if area >= 1_000 {
        return String(format: "%.1fK km²", area / 1_000)
    } else {
        return "\(Int(area)) km²"
    }
}

func formatTimezones(_ timezones: [String]) -> String {
    guard !timezones.isEmpty else { return "N/A" }
    if timezones.count == 1 { return timezones[0] }
    if timezones.count <= 3 { return timezones.joined(separator: ", ") }
    return "\(timezones.prefix(2).joined(separator: ", ")) +\(timezones.count - 2) more"
}

extension Int64 {
    func toHumanReadable() -> String {
        switch self {
        case 1_000_000_000...: return String(format: "%.1fB", Double(self) / 1_000_000_000)
        case 1_000_000...: return String(format: "%.1fM", Double(self) / 1_000_000)
        case 1_000...: return String(format: "%.1fK", Double(self) / 1_000)
        default: return "\(self)"
        }
    }
}

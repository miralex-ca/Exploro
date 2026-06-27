import SwiftUI

private let name = Country.france.name
private let flagPngUrl = Country.france.flagImage
private let subregion = Country.france.location

#Preview("Card - Light", traits: .sizeThatFitsLayout) {
    PreviewCard(dark: false) {
        CountryGridCard(
            name: name,
            flagImage: flagPngUrl,
            location: subregion,
            onClick: {}
        )
    }
    .frame(maxWidth: 250)
}

#Preview("Card - Dark", traits: .sizeThatFitsLayout) {
    PreviewCard(dark: true) {
        CountryGridCard(
            name: name,
            flagImage: flagPngUrl,
            location: subregion,
            onClick: {}
        )
        
    }
    .frame(maxWidth: 250)
}

#Preview("Card no flag", traits: .sizeThatFitsLayout) {
    PreviewCard(dark: false) {
        CountryGridCard(
            name: name,
            flagImage: "",
            location: subregion,
            onClick: {}
        )
    }
    .frame(maxWidth: 250)
}

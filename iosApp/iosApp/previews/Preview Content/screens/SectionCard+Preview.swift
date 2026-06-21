import SwiftUI

private let name = Country.france.name
private let flagPngUrl = Country.france.flagPngUrl
private let subregion = Country.france.subregion

#Preview("Card - Light", traits: .sizeThatFitsLayout) {
    PreviewCard(dark: false) {
        CountryGridCard(
            name: name,
            flagPngUrl: flagPngUrl,
            subregion: subregion,
            onClick: {}
        )
    }
    .frame(maxWidth: 250)
}

#Preview("Card - Dark", traits: .sizeThatFitsLayout) {
    PreviewCard(dark: true) {
        CountryGridCard(
            name: name,
            flagPngUrl: flagPngUrl,
            subregion: subregion,
            onClick: {}
        )
        
    }
    .frame(maxWidth: 250)
}

#Preview("Card no flag", traits: .sizeThatFitsLayout) {
    PreviewCard(dark: false) {
        CountryGridCard(
            name: name,
            flagPngUrl: "",
            subregion: subregion,
            onClick: {}
        )
    }
    .frame(maxWidth: 250)
}

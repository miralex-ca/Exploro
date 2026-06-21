import SwiftUI


private let name = Country.france.name
private let flagPngUrl = Country.france.flagPngUrl

#Preview("Card - Light", traits: .sizeThatFitsLayout) {
    PreviewCard(dark: false) {
        HomeSectionListCard(
            name: name,
            flagPngUrl: flagPngUrl,
            onClick: {}
        )
    }
}

#Preview("Card - Dark", traits: .sizeThatFitsLayout) {
    PreviewCard(dark: true) {
        HomeSectionListCard(
            name: name,
            flagPngUrl: flagPngUrl,
            onClick: {}
        )
    }
}

#Preview("Card no flag", traits: .sizeThatFitsLayout) {
    PreviewCard(dark: false) {
        HomeSectionListCard(
            name: name,
            flagPngUrl: "",
            onClick: {}
        )
    }
}




import SwiftUI

private let detailsUiState = DetailsUiState.france

#Preview(
    "Details (iPhone - Light)",
    traits: .sizeThatFitsLayout
) {
    PreviewScreen(
        dark: false,
        layout: .previewPhone
    ) {
        DetailsScreenContent(
            details: detailsUiState,
            onEvent: {_ in }
        )
    }
}

#Preview(
    "Details (iPhone - Dark)",
    traits: .sizeThatFitsLayout
) {
    PreviewScreen(
        dark: true,
        layout: .previewPhone
    ) {
        DetailsScreenContent(
            details: detailsUiState,
            onEvent: {_ in }
        )
    }
}

#Preview(
    "Details (iPad Landscape)",
    traits: .fixedLayout(width: 1000, height: 600)
) {
    PreviewScreen(
        dark: false,
        layout: .previewTabletLandscape
    ) {
        DetailsScreenContent(
            details: detailsUiState,
            onEvent: {_ in }
        )
    }
}



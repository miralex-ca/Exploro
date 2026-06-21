import SwiftUI

#Preview("Empty list - Light") {
    PreviewScreen {
        EmptyStateView(state: .emptyList)
    }
}


#Preview("Empty list - Dark") {
    PreviewScreen(dark: true) {
        EmptyStateView(state: .emptyList)
    }
}

#Preview("Not found") {
    PreviewScreen {
        EmptyStateView(state: .notFound)
    }
}

#Preview("Not found") {
    PreviewScreen {
        EmptyStateView(state: .noResults)
    }
}




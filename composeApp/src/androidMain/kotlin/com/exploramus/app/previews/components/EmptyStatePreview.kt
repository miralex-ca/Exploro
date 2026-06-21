package com.exploramus.app.previews.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.exploramus.app.composables.components.EmptyState
import com.exploramus.app.composables.components.EmptyStateView
import com.exploramus.app.previews.PreviewScreen


@Preview(name = "Empty List", showBackground = true)
@Composable
internal fun EmptyStateEmptyListPreview() {
    PreviewScreen(dark = false) {
        EmptyStateView(state = EmptyState.EmptyList)
    }
}

@Preview(name = "Not Found", showBackground = true)
@Composable
internal fun EmptyStateNotFoundPreview() {
    PreviewScreen(dark = true) {
        EmptyStateView(state = EmptyState.NotFound)
    }
}

@Preview(name = "No Results", showBackground = true)
@Composable
internal fun EmptyStateNoResultsPreview() {
    PreviewScreen(dark = false) {
        EmptyStateView(state = EmptyState.NoResults)
    }
}
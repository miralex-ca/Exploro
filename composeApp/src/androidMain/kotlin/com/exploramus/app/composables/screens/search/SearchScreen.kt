package com.exploramus.app.composables.screens.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.exploramus.app.resources.Strings
import com.exploramus.app.composables.screens.search.views.SearchBar
import com.exploramus.app.composables.screens.search.views.SearchResultsList
import com.exploramus.app.composables.screens.search.views.rememberSearchInputController
import com.exploramus.app.composables.screens.search.views.rememberSearchKeyboardController
import com.exploramus.app.design.adaptive.layout
import com.exploramus.app.design.adaptive.value
import com.exploramus.app.design.theme.AppTypography
import com.exploramus.app.utils.SingleEffect
import com.exploramus.shared.viewmodel.screens.search.SearchListItem
import com.exploramus.shared.viewmodel.screens.search.SearchResult
import com.exploramus.shared.viewmodel.screens.search.SearchScreenState
import kotlinx.coroutines.delay

@Composable
fun SearchScreen(
    screenState: SearchScreenState,
    eventHandler: SearchEventHandler,
) {
    SearchScreenContent(
        screenState = screenState,
        onEvent = eventHandler::onEvent,
    )
}

@Composable
fun SearchScreenContent(
    screenState: SearchScreenState,
    onEvent: (SearchUiEvent) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val listState = rememberLazyListState()
    val keyboardController = rememberSearchKeyboardController(focusRequester, listState)
    val searchInput = rememberSearchInputController(focusRequester, keyboardController)
    var searchQuery by searchInput.query

    SingleEffect(
        effect = screenState.screenBecomeActive,
        consume = { onEvent(SearchUiEvent.ConsumeBecomeActive) },
    ) {
        searchInput.clear()
        delay(100)
        searchInput.focus()
    }

    LaunchedEffect(searchQuery) {
        delay(400)
        onEvent(SearchUiEvent.SearchByQuery(searchQuery))
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            SearchBar(
                state = searchInput.fieldState,
                focusRequester = focusRequester,
                onQueryChange = { searchQuery = it },
                onBackClick = {
                    searchInput.clear()
                    onEvent(SearchUiEvent.OnBackClicked)
                },
                onClearClick = {
                    searchInput.clearAndFocus()
                }
            )

            SearchResultContent(
                query = searchQuery,
                screenState = screenState,
                listState = listState,
                onItemClicked = { onEvent(SearchUiEvent.OnItemClicked(it)) }
            )
        }

        if (MaterialTheme.layout.showSearchFab.value()) {
            SearchFab(focusRequester, keyboardController)
        }
    }
}

@Composable
private fun SearchResultContent(
    query: String,
    screenState: SearchScreenState,
    listState: LazyListState,
    onItemClicked: (SearchListItem) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        when (val result = screenState.searchResult) {
            is SearchResult.Idle -> SearchInitial()
            is SearchResult.NotFound -> SearchEmptyResult(query = query)
            is SearchResult.Success -> SearchResultsList(
                listState = listState,
                result = result,
                onItemClicked = onItemClicked,
            )
        }
    }
}

@Composable
private fun BoxScope.SearchFab(
    focusRequester: FocusRequester,
    keyboardController: SoftwareKeyboardController?
) {
    FloatingActionButton(
        containerColor = FloatingActionButtonDefaults.containerColor,
        onClick = {
            focusRequester.requestFocus()
            keyboardController?.show()
        },
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(end = 26.dp)
            .padding(bottom = 46.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = Strings.openKeyboard
        )
    }
}


@Composable
fun SearchInitial() {
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = Strings.startSearch,
            style = AppTypography.searchResultText,
            textAlign = TextAlign.Center,
            modifier = Modifier.alpha(0.7f)
        )
    }
}

@Composable
fun SearchEmptyResult(
    query: String
) {
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = Strings.noSearchResult,
            style = AppTypography.searchResultText,
            textAlign = TextAlign.Center,
            modifier = Modifier.alpha(0.7f)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = query.trim(),
            style = AppTypography.searchResultText,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}




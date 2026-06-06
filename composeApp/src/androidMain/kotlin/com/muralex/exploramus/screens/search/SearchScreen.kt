package com.muralex.exploramus.screens.search

import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.retain.retain
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muralex.exploramus.resources.Strings
import com.muralex.exploramus.screens.search.SearchUiEvent.DidBecomeActive
import com.muralex.exploramus.screens.search.SearchUiEvent.OnItemClicked
import com.muralex.exploramus.screens.search.SearchUiEvent.SearchByQuery
import com.muralex.exploramus.ui.adaptive.layout
import com.muralex.exploramus.ui.adaptive.value
import com.muralex.exploramus.ui.components.RemoteImage
import com.muralex.exploramus.ui.theme.AppTypography
import com.muralex.exploramus.ui.theme.appColors
import com.muralex.exploramus.utils.OnChange
import com.muralex.exploramus.utils.SingleEffect
import com.muralex.exploramus.viewmodel.screens.search.SearchListItem
import com.muralex.exploramus.viewmodel.screens.search.SearchResult
import com.muralex.exploramus.viewmodel.screens.search.SearchScreenState
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreenContent(
    screenState: SearchScreenState,
    onEvent: (SearchUiEvent) -> Unit,
) {
    var query by remember { mutableStateOf("") }
    val searchFieldState = rememberTextFieldState(query)
    val uiState = retain { SearchUiState() }
    val imeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0

    LaunchedEffect(query) {
        delay(400)
        onEvent(SearchByQuery(query))
    }

    LaunchedEffect(screenState.searchResult) {
        if (query.isNotEmpty()) {
            delay(1200)
        }
        uiState.isSearching = false
    }

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(imeVisible) {
        if (imeVisible) {
            uiState.wasKeyboardVisible = true
        } else {
            delay(500)
            uiState.wasKeyboardVisible = false
        }
    }

    LaunchedEffect(Unit) {
        if (uiState.wasKeyboardVisible) {
            delay(300)
            focusRequester.requestFocus()
            keyboardController?.show()
        }
    }

    SingleEffect(
        effect = screenState.screenBecomeActive,
        consume = { onEvent(DidBecomeActive) },
    ) {
        query = ""
        searchFieldState.clearText()
        delay(100)
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    val listState = rememberLazyListState()

    LaunchedEffect(listState.isScrollInProgress) {
        if (listState.isScrollInProgress && (listState.firstVisibleItemScrollOffset > 0)) {
            keyboardController?.hide()
            focusRequester.freeFocus()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.appColors.topBarContainer,
                    titleContentColor = MaterialTheme.appColors.onTopBarContainer,
                    navigationIconContentColor = MaterialTheme.appColors.onTopBarContainer,
                    actionIconContentColor = MaterialTheme.appColors.onTopBarContainer,
                ),
                title = {
                    SearchTopBar(
                        state = searchFieldState,
                        onQueryChange = { query = it },
                        onBackClick = {
                            query = ""
                            searchFieldState.clearText()
                            onEvent(SearchUiEvent.OnBackClicked)
                        },
                        onClearClick = {
                            query = ""
                            searchFieldState.clearText()
                            focusRequester.requestFocus()
                            keyboardController?.show()
                        },
                        focusRequester = focusRequester,
                    )
                }
            )

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                when (val result = screenState.searchResult) {
                    is SearchResult.Idle -> SearchInitial()
                    is SearchResult.NotFound -> SearchEmptyResult(query = query)
                    is SearchResult.Success -> SearchItemsList(
                        listState = listState,
                        result = result,
                        onEvent = onEvent
                    )
                }
            }
        }

        if (MaterialTheme.layout.showSearchFab.value()) {
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
                    contentDescription = "Open keyboard"
                )
            }
        }
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

@Composable
private fun SearchItemsList(
    listState: LazyListState,
    result: SearchResult.Success,
    onEvent: (SearchUiEvent) -> Unit
) {
    LazyColumn(
        state = listState,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.layout.cardSpacing.value()),
        contentPadding = PaddingValues(
            start = 14.dp,
            end = 14.dp,
            top = 12.dp,
            bottom = 60.dp
        ),
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(
            items = result.items,
            key = { _, item -> item.id }
        ) { index, item ->
            SearchListRow(
                item = item,
                index = index,
                lastIndex = result.items.lastIndex,
                onClick = { onEvent(OnItemClicked(item)) },
                modifier = Modifier.animateItem(
                    fadeInSpec = tween(180),
                    fadeOutSpec = tween(180),
                    placementSpec = tween(180)
                )
            )
        }
    }
}

@Composable
fun SearchTopBar(
    state: TextFieldState,
    onQueryChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onClearClick: () -> Unit,
    focusRequester: FocusRequester,
) {
    var isSearching by remember { mutableStateOf(value = false) }

    LaunchedEffect(state.text) {
        onQueryChange(state.text.toString())
    }

    OnChange(state.text) { _, new ->
        isSearching = true

        if (new.isNotEmpty()) {
            delay(1200)
        }
        isSearching = false
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
            .padding(start = 5.dp, end = 15.dp)
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(24.dp),
                clip = false,
                spotColor = MaterialTheme.colorScheme.surfaceTint
            )
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {

        TextField(
            state = state,
            modifier = Modifier
                .fillMaxSize()
                .focusRequester(focusRequester)
                .padding(3.dp)
            ,
            shape = RoundedCornerShape(24.dp),
            lineLimits = TextFieldLineLimits.SingleLine,
            textStyle = TextStyle(
                fontSize = 18.sp,
                lineHeight = 20.sp,
            ),
            contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp),
            placeholder = {
                Text(
                    text = Strings.searchPlaceholder,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light
                )
            },

            leadingIcon = {
                IconButton(
                    onClick = {
                        onBackClick.invoke()
                    },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },

            trailingIcon = {
                Box(
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .width(60.dp)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.CenterEnd
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                    ) {

                        if (isSearching) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp).alpha(0.7f),
                                strokeWidth = 2.dp,
                                gapSize = 2.dp
                            )

                            Spacer(modifier = Modifier.width(6.dp))
                        }

                        if (state.text.isNotBlank()) {
                            IconButton(
                                onClick = {
                                    onClearClick.invoke()
                                },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Close,
                                    contentDescription = "Clear"
                                )
                            }
                        }
                    }
                }
            },
            colors = TextFieldDefaults.colors(
                cursorColor = MaterialTheme.appColors.caretColor,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}


@Composable
fun SearchListRow(
    item: SearchListItem,
    index: Int,
    lastIndex: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val layout = MaterialTheme.layout.search

    val isFirst = index == 0
    val isLast = index == lastIndex

    val shape = when {
        isFirst && isLast -> RoundedCornerShape(12.dp)
        isFirst -> RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
        isLast -> RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
        else -> RoundedCornerShape(0.dp)
    }


    Card(
        modifier = modifier
            .widthIn(max = layout.listItemMaxWidth.value()),
        shape = shape,
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = BorderStroke(
            width = 0.5.dp,
            color = MaterialTheme.appColors.cardBorder
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            RemoteImage(
                imageUrl = item.flagPngUrl,
                modifier = Modifier
                    .height(58.dp)
                    .width(layout.itemImageWidth.value())
                    .border(
                        width = 1.dp,
                        color = Color.LightGray.copy(alpha = 0.45f),
                        shape = RoundedCornerShape(6.dp)
                    ),
                shape = RoundedCornerShape(6.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(26.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = item.officialName,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "Capital: ${item.capital}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

class SearchUiState {
    var wasKeyboardVisible by mutableStateOf(value = false)
    var isSearching by mutableStateOf(value = false)
}





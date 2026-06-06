package com.muralex.exploramus.screens.search.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muralex.exploramus.resources.Strings
import com.muralex.exploramus.ui.theme.appColors
import com.muralex.exploramus.utils.OnChange
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    state: TextFieldState,
    focusRequester: FocusRequester,
    onQueryChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onClearClick: () -> Unit,
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.appColors.topBarContainer,
            titleContentColor = MaterialTheme.appColors.onTopBarContainer,
            navigationIconContentColor = MaterialTheme.appColors.onTopBarContainer,
            actionIconContentColor = MaterialTheme.appColors.onTopBarContainer,
        ),
        title = {
            SearchTextField(
                state = state,
                onQueryChange = onQueryChange,
                onBackClick = onBackClick,
                onClearClick = onClearClick,
                focusRequester = focusRequester,
            )
        }
    )
}

@Composable
fun SearchTextField(
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
                .padding(3.dp),
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
                                modifier = Modifier
                                    .size(20.dp)
                                    .alpha(0.7f),
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
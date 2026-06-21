package com.exploramus.app.composables.screens.search.views

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.*
import androidx.compose.runtime.retain.retain
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import kotlinx.coroutines.delay

class SearchInputController(
    val query: MutableState<String>,
    val fieldState: TextFieldState,
    val focusRequester: FocusRequester,
    val keyboardController: SoftwareKeyboardController?,
) {
    fun clearAndFocus() {
        clear()
        focus()
    }

    fun focus() {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    fun clear() {
        query.value = ""
        fieldState.clearText()
    }
}

@Composable
fun rememberSearchInputController(
    focusRequester: FocusRequester,
    keyboardController: SoftwareKeyboardController?,
): SearchInputController {
    val query = remember { mutableStateOf("") }
    val fieldState = rememberTextFieldState()
    return remember(keyboardController) {
        SearchInputController(query, fieldState, focusRequester, keyboardController)
    }
}

@Composable
fun rememberSearchKeyboardController(
    focusRequester: FocusRequester,
    listState: LazyListState,
): SoftwareKeyboardController? {
    val keyboardController = LocalSoftwareKeyboardController.current
    val imeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    var wasKeyboardVisible by retain { mutableStateOf(false) }

    LaunchedEffect(imeVisible) {
        if (imeVisible) {
            wasKeyboardVisible = true
        } else {
            delay(500)
            wasKeyboardVisible = false
        }
    }

    LaunchedEffect(Unit) {
        if (wasKeyboardVisible) {
            delay(300)
            focusRequester.requestFocus()
            keyboardController?.show()
        }
    }

    LaunchedEffect(listState.isScrollInProgress) {
        if (listState.isScrollInProgress && listState.firstVisibleItemScrollOffset > 0) {
            keyboardController?.hide()
            focusRequester.freeFocus()
        }
    }

    return keyboardController
}
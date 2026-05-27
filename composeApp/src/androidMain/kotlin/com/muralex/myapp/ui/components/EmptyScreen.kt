package com.muralex.myapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Inbox
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.SearchOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.muralex.myapp.resources.Strings

@Composable
fun EmptyStateView(
    state: EmptyState,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Surface(
            modifier = Modifier
                .padding(24.dp)
                .widthIn(max = 360.dp)
                .padding(top = 30.dp),
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
        ) {

            Column(
                modifier = Modifier.padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Icon(
                    imageVector = state.icon(),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(52.dp).alpha(0.7f),
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = state.title(),
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = state.message(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

sealed interface EmptyState {
    @Composable
    fun title(): String

    @Composable
    fun message(): String

    @Composable
    fun icon(): ImageVector

    data object NoResults : EmptyState {
        @Composable
        override fun title() = Strings.emptyTitleNoResults

        @Composable
        override fun message() = Strings.emptyMsgNoResults

        @Composable
        override fun icon() = Icons.Outlined.SearchOff
    }

    data object EmptyList : EmptyState {
        @Composable
        override fun title() = Strings.emptyTitleEmptyList

        @Composable
        override fun message() = Strings.emptyMsgEmptyList

        @Composable
        override fun icon() = Icons.Outlined.Inbox
    }

    data object NotFound : EmptyState {
        @Composable
        override fun title() = Strings.emptyTitleNotFound

        @Composable
        override fun message() = Strings.emptyMsgNotFound

        @Composable
        override fun icon() = Icons.Outlined.Info
    }
}
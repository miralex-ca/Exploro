package com.muralex.exploramus.composables.screens.favorites.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.muralex.exploramus.resources.Strings

@Composable
fun FavoriteDropdownMenu(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onRemove: () -> Unit
) {
    Box(
        modifier = modifier

    ) {
        var expanded by remember { mutableStateOf(false) }

        IconButton(
            onClick = { expanded = true },
            modifier = Modifier.size(34.dp)
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = Strings.commonMoreOptions,
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            shape = RoundedCornerShape(16.dp),
        ) {
            DropdownMenuItem(
                contentPadding = PaddingValues(horizontal = 20.dp),
                // modifier = Modifier.padding(horizontal = 10.dp),
                text = { Text(Strings.commonView) },
                leadingIcon = {
                    Icon(Icons.Outlined.RemoveRedEye, null)
                },
                onClick = {
                    expanded = false
                    onClick()
                },

                )
            DropdownMenuItem(
                contentPadding = PaddingValues(start = 20.dp, end = 25.dp),
                text = {
                    Text(
                        Strings.commonRemove,
                        color = MaterialTheme.colorScheme.error
                    )
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Delete,
                        null,
                        tint = MaterialTheme.colorScheme.error
                    )
                },
                onClick = {
                    expanded = false
                    onRemove()
                }
            )
        }
    }
}
package com.muralex.exploramus.previews

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.muralex.exploramus.design.adaptive.AppLayout
import com.muralex.exploramus.design.adaptive.LocalAppLayout
import com.muralex.exploramus.design.adaptive.LocalFormFactor
import com.muralex.exploramus.design.adaptive.rememberFormFactor
import com.muralex.exploramus.design.theme.AppTheme
import com.muralex.models.ThemeMode

@Composable
fun PreviewScreen(
    dark: Boolean = false,
    content: @Composable () -> Unit,
) {
    val formFactor = rememberFormFactor()
    CompositionLocalProvider(
        LocalFormFactor provides formFactor,
        LocalAppLayout provides AppLayout.build(formFactor),
    ) {
        AppTheme(themeMode = if (dark) ThemeMode.DARK else ThemeMode.LIGHT) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                content()
            }
        }
    }
}


@Composable
fun PreviewCard(
    dark: Boolean = false,
    content: @Composable () -> Unit,
) {
    val formFactor = rememberFormFactor()
    CompositionLocalProvider(
        LocalFormFactor provides formFactor,
        LocalAppLayout provides AppLayout.build(formFactor),
    ) {
        AppTheme(themeMode = if (dark) ThemeMode.DARK else ThemeMode.LIGHT) {
            Surface(
                color = MaterialTheme.colorScheme.background
            ) {
                Box(modifier = Modifier.padding(16.dp)) {
                    content()
                }
            }
        }
    }
}

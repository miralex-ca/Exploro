package com.muralex.myapp.screens.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muralex.myapp.ui.components.dialogs.ConfirmationDialog
import com.muralex.myapp.ui.components.dialogs.SingleChoiceDialog
import com.muralex.myapp.ui.theme.appColors
import com.muralex.myapp.utils.asString
import com.muralex.myapp.viewmodel.screens.settings.Setting
import com.muralex.myapp.viewmodel.screens.settings.SettingAction
import com.muralex.myapp.viewmodel.screens.settings.SettingsCategory
import com.muralex.myapp.viewmodel.screens.settings.SettingsScreenState

@Composable
fun SettingsScreen(
    screenState: SettingsScreenState,
    eventHandler: SettingsEventHandler,
) {
    AppSettingsContent(
        screenState = screenState,
        onEvent = eventHandler::onEvent,
        onSettingAction = eventHandler::onSettingAction
    )
}

@Composable
fun AppSettingsContent(
    screenState: SettingsScreenState,
    onEvent: (SettingsUiEvent) -> Unit,
    onSettingAction: (SettingAction) -> Unit,
) {

    Column {
        val state = remember {
            MutableTransitionState(false).apply {
                targetState = true
            }
        }

        AnimatedVisibility(
            visibleState = state,
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut()
        ) {
            AppSettingsBox(
                screenState = screenState,
                onSettingAction = onSettingAction
            )
        }
    }
}


@Composable
fun AppSettingsBox(
    screenState: SettingsScreenState,
    onSettingAction: (SettingAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .widthIn(max = 600.dp)
    ) {
        screenState.categories.forEach { category ->
            SettingsCategoryContent(
                category = category,
                onAction = onSettingAction
            )
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
fun SettingsCategoryContent(
    category: SettingsCategory,
    onAction: (SettingAction) -> Unit,
) {
    Column {
        category.title?.let {
            Text(
                text = it.asString(),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
            )
        } ?: Spacer(Modifier.height(12.dp))

        category.settings.forEachIndexed { index, setting ->
            val isFirst = index == 0
            val isLast = index == category.settings.lastIndex

            val shape = when {
                isFirst && isLast -> RoundedCornerShape(14.dp)
                isFirst -> RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp)
                isLast -> RoundedCornerShape(bottomStart = 14.dp, bottomEnd = 14.dp)
                else -> RoundedCornerShape(0.dp)
            }

            Surface(
                shape = shape,
                color = MaterialTheme.colorScheme.surfaceContainerHighest,
                contentColor = MaterialTheme.colorScheme.onSurface,
                border = BorderStroke(0.5.dp, MaterialTheme.appColors.cardBorder),
            ) {
                SettingItem(
                    setting = setting,
                    onAction = onAction
                )
            }

            if (!isLast) {
                Spacer(Modifier.height(0.5.dp))
            }
        }
    }
}




@Composable
private fun AppSettingsBoxes(
    screenState: SettingsScreenState,
    onSettingAction: (SettingAction) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.TopCenter) {

        Card(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 300.dp)
                    .padding(12.dp)
            ) {

                Text(
                    text = "Interface",
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.surfaceTint,
                    fontSize = 18.sp
                )

                screenState.settings.forEach { setting ->
                    SettingItem(
                        setting = setting,
                        onAction = {
                            action -> onSettingAction(action)
                        }
                    )
                }

                Spacer(Modifier.height(35.dp))
            }
        }
    }
}

@Composable
fun SettingItem(
    setting: Setting,
    onAction: (SettingAction) -> Unit
) {
    when (setting) {
        is Setting.Options -> ListPreference(setting, onAction)
        is Setting.Switch -> SwitchPreference(setting, onAction)
        is Setting.Action -> PreferenceWithAction(setting, onAction)
        is Setting.Info -> PreferenceWithInfo(setting)
    }
}

@Composable
fun SwitchPreference(
    setting: Setting.Switch,
    onAction: (SettingAction) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onAction(setting.onToggle()) })
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        val summary = when {
            setting.value && setting.summaryOn != null -> setting.summaryOn
            !setting.value && setting.summaryOff != null -> setting.summaryOff
            else -> setting.summary
        }

        Box(modifier = Modifier.weight(1f)) {
            PreferenceContent(
                title = setting.title.asString(),
                summary = summary?.asString(),
            )
        }

        Switch(
            checked = setting.value,
            onCheckedChange = { onAction(setting.onToggle()) }
        )
    }
}

@Composable
fun ListPreference(
    setting: Setting.Options,
    onAction: (SettingAction) -> Unit
) {
    var isDialogVisible by remember { mutableStateOf(false) }
    val selectedOption = setting.options.find { it.value == setting.selectedValue }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { isDialogVisible = true  })
            .padding(14.dp),
    ) {
        PreferenceContent(
            title = setting.title.asString(),
            summary = selectedOption?.label?.asString() ?: setting.selectedValue,
        )
    }

    SingleChoiceDialog(
        isVisible = isDialogVisible,
        title = (setting.dialogTitle ?: setting.title).asString(),
        options = setting.options.map { it.label.asString() },
        selectedIndex = setting.options.indexOfFirst { it.value == setting.selectedValue },
        onOptionSelected = { index ->
            onAction(setting.onSelect(setting.options[index].value))
        },
        onDismiss = { isDialogVisible = false }
    )
}

@Composable
fun PreferenceWithAction(
    setting: Setting.Action,
    onAction: (SettingAction) -> Unit
) {
    var isDialogVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { isDialogVisible = true  })
            .padding(14.dp),
    ) {
        PreferenceContent(
            title = setting.title.asString(),
            summary = setting.summary?.asString(),
        )
    }

    val dialogTitleRes = setting.dialogTitle ?: setting.title
    val dialogMessageRes = setting.dialogMessage ?: setting.summary

    ConfirmationDialog(
        isVisible = isDialogVisible,
        title = dialogTitleRes.asString(),
        message = dialogMessageRes?.asString() ?: "",
        onConfirm = {
            onAction(setting.onClick())
        },
        onDismiss = {
            isDialogVisible = false
        }
    )
}

@Composable
fun PreferenceWithInfo(
    setting: Setting.Info,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
    ) {
        PreferenceContent(
            title = setting.title.asString(),
            summary = setting.info,
        )
    }
}

@Composable
private fun PreferenceContent(
    title: String = "",
    summary: String? = null,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(2.dp),
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        summary?.let {
            Text(
                text = it,
                modifier = Modifier
                    .padding(2.dp)
                    .alpha(0.8F),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 14.sp
            )
        }
    }
}


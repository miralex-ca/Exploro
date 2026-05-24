package com.muralex.myapp.screens.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muralex.myapp.ui.components.dialogs.ConfirmationDialog
import com.muralex.myapp.ui.components.dialogs.SingleChoiceDialog
import com.muralex.myapp.utils.asString
import com.muralex.myapp.viewmodel.screens.settings.Setting
import com.muralex.myapp.viewmodel.screens.settings.SettingAction
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
private fun AppSettingsBox(
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
            .padding(8.dp),
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
            .padding(8.dp),
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
            .padding(8.dp),
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
            fontSize = 18.sp
        )

        summary?.let {
            Text(
                text = "Current: $summary",
                modifier = Modifier
                    .padding(2.dp)
                    .alpha(0.8F),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 14.sp
            )
        }
    }
}


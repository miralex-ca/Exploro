package com.muralex.myapp.screens.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.muralex.models.ThemeMode
import com.muralex.myapp.utils.Strings
import com.muralex.myapp.utils.asString
import com.muralex.myapp.viewmodel.screens.settings.Setting
import com.muralex.myapp.viewmodel.screens.settings.SettingAction
import com.muralex.myapp.viewmodel.screens.settings.SettingsScreenState

data class ThemeOption(
    val mode: ThemeMode,
    val label: String,
)

@Composable
fun themeOptions(): List<ThemeOption> = listOf(
    ThemeOption(ThemeMode.LIGHT, Strings.themeModeLight),
    ThemeOption(ThemeMode.DARK, Strings.themeModeDark),
    ThemeOption(ThemeMode.SYSTEM, Strings.themeModeSystem),
)

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
private fun OptionsWithDialog(
    radioOptions: List<String> = emptyList(),
    title: String = "",
    summary: String = "",
    optionSelectedIndex: (Int) -> Unit,
    selectedIndex: Int = 0,
) {

    val openDialog = remember { mutableStateOf(false) }

    OptionsSetting(
        openDialog = openDialog,
        title = title,
        summary = summary,
    )

    OptionDialog(
        dialogTitle = title,
        openDialog = openDialog,
        selectedIndex = selectedIndex,
        optionSelectedIndex = optionSelectedIndex,
        radioOptions = radioOptions,
    )
}

@Composable
private fun OptionsSetting(
    openDialog: MutableState<Boolean>,
    title: String = "",
    summary: String = "",
) {
    Column(
        modifier = Modifier
            .clickable { openDialog.value = true }
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        Text(
            text = title,
            modifier = Modifier.padding(2.dp),
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 18.sp
        )

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


@Composable
fun OptionDialog(
    dialogTitle: String = "",
    radioOptions:  List<String> = emptyList(),
    openDialog: MutableState<Boolean>,
    optionSelectedIndex: (Int) -> Unit,
    selectedIndex: Int = 0,
) {
    if (openDialog.value) {
        NoPaddingAlertDialog(
            titleText = dialogTitle,
            content = {
                RadioGroupSample(
                    radioOptions = radioOptions,
                    onOptionSelect = {
                        optionSelectedIndex.invoke(it)
                        openDialog.value = false
                    },
                    selectedIndex = selectedIndex,
                )
            },

            onDismissRequest = { openDialog.value = false },
            confirmButton = {
                TextButton(
                    onClick = { openDialog.value = false }
                ) {
                    Text(
                        text = "CANCEL",
                        fontSize = 15.sp,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        )
    }
}

@Composable
fun RadioGroupSample(
    radioOptions: List<String> = emptyList(),
    onOptionSelect: (Int) -> Unit = {},
    selectedIndex: Int = 0,
) {
    val selectedOptionIndex = if (selectedIndex > radioOptions.lastIndex) 0 else selectedIndex
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[selectedOptionIndex]) }

    Column(
        Modifier.selectableGroup()
    ) {
        radioOptions.forEachIndexed  {index, text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                            onOptionSelect(index)
                        },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = null // null recommended for accessibility with screenreaders
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}



@Composable
fun NoPaddingAlertDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    titleText: String = "",
    title: @Composable (() -> Unit)? = null,
    content: @Composable (() -> Unit)? = null,
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable (() -> Unit)? = null,
    shape: Shape = MaterialTheme.shapes.large,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    properties: DialogProperties = DialogProperties(),
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        Surface(
            modifier = modifier,
            shape = shape,
            color = backgroundColor,
            contentColor = contentColor
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                if (titleText.isEmpty()) {
                    title?.let {
                        CompositionLocalProvider {
                            val textStyle = MaterialTheme.typography.titleLarge
                            ProvideTextStyle(textStyle, it)
                        }
                    }
                } else {
                    Text(
                        text = titleText,
                        modifier = Modifier.padding(vertical = 20.dp, horizontal = 24.dp),
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 20.sp
                    )
                }
                content?.invoke()
                Box(
                    Modifier.fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 14.dp),
                    ) {
                        dismissButton?.invoke()
                        confirmButton()
                    }
                }
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
        is Setting.Options -> OptionsSettingItem(setting, onAction)
        is Setting.Switch -> SwitchSettingItem(setting, onAction)
        is Setting.Action -> ActionSettingItem(setting, onAction)
    }
}


@Composable
fun SwitchSettingItem(
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
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = setting.title.simpleName(),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 18.sp
            )
            val summary = when {
                setting.value && setting.summaryOn != null -> setting.summaryOn
                !setting.value && setting.summaryOff != null -> setting.summaryOff
                else -> setting.summary
            }
            summary?.let {
                Text(
                    text = it.simpleName(),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.alpha(0.8f)
                )
            }
        }
        Switch(
            checked = setting.value,
            onCheckedChange = { onAction(setting.onToggle()) }
        )
    }
}

@Composable
fun ActionSettingItem(
    setting: Setting.Action,
    onAction: (SettingAction) -> Unit
) {
    Column(
        modifier = Modifier
            .clickable {
                //setting.action?.let { onAction(it) }
            }
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = setting.title.simpleName(),
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 18.sp
        )
        setting.summary?.let {
            Text(
                text = it.simpleName(),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.alpha(0.8f)
            )
        }
    }
}

@Composable
fun OptionsSettingItem(
    setting: Setting.Options,
    onAction: (SettingAction) -> Unit
) {
    val openDialog = remember { mutableStateOf(false) }


    OptionsSetting(
        title = setting.title.simpleName(),
        summary = setting.summary?.asString() ?: setting.selectedValue   ,
        openDialog = openDialog
    )

    OptionDialog(
        dialogTitle = setting.dialogTitle?.simpleName()  ?: setting.title.simpleName(),
        radioOptions = setting.entries.map { it.simpleName() },
        openDialog = openDialog,
        selectedIndex = setting.entryValues.indexOf(setting.selectedValue),
        optionSelectedIndex = { index ->
            onAction(setting.onSelect(setting.entryValues[index]))
        }
    )
}


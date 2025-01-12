package ui

import EThemeColor
import LText
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun SettingDialog(
    isVisible: Boolean,
    currentTheme: EThemeColor.Theme,
    currentLanguage: LText.Language,
    onThemeChange: (EThemeColor.Theme) -> Unit,
    onLanguageChange: (LText.Language) -> Unit,
    onAdjustWidths: () -> Unit,
    onDismiss: () -> Unit,
    onExit: () -> Unit
) {
    if (!isVisible) return
    Dialog(
        onDismissRequest = onDismiss
    ) {
        var selectedTheme by remember { mutableStateOf(currentTheme) }
        var selectedLanguage by remember { mutableStateOf(currentLanguage) }
        var expanded by remember { mutableStateOf(false) }
        var expandedInLanguage by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier.fillMaxSize().padding(50.dp).background(selectedTheme.colors.dialogBackgroundColor)
        ) {
            Text(
                selectedLanguage.texts.settingDialogTitle, modifier = Modifier.padding(10.dp).align(Alignment.TopStart),
                color = selectedTheme.colors.groupNowTextColor,
                style = MaterialTheme.typography.h5
            )
            Column(
                modifier = Modifier.height(400.dp).width(500.dp).align(Alignment.Center)
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(10.dp).align(Alignment.CenterHorizontally)
                ) {
                    Button(
                        onClick = { expanded = true }, modifier = Modifier.align(Alignment.Center).fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = selectedTheme.colors.lightBtnBackground),
                    ) {
                        Text(
                            "${selectedLanguage.texts.settingDialogThemeBtnText}: ${
                                selectedTheme.getThemeName(
                                    selectedLanguage
                                )
                            }",
                            color = selectedTheme.colors.lightBtnTextColor
                        )
                    }
                    DropdownMenu(
                        modifier = Modifier.align(Alignment.Center).padding(10.dp),
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                    ) {
                        EThemeColor.Theme.entries.forEach {
                            DropdownMenuItem(onClick = {
                                selectedTheme = it
                                onThemeChange(it)
                                expanded = false
                            }) {
                                Text(it.getThemeName(selectedLanguage))
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier.fillMaxWidth().padding(10.dp).align(Alignment.CenterHorizontally)
                ) {
                    Button(
                        onClick = onAdjustWidths, modifier = Modifier.align(Alignment.Center).fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = selectedTheme.colors.lightBtnBackground)
                    ) {
                        Text(
                            selectedLanguage.texts.settingDialogAdjustWidthsBtnText,
                            color = selectedTheme.colors.lightBtnTextColor
                        )
                    }
                    DropdownMenu(
                        modifier = Modifier.align(Alignment.Center).padding(10.dp),
                        expanded = expandedInLanguage,
                        onDismissRequest = { expandedInLanguage = false },
                    ) {
                        LText.Language.entries.forEach {
                            DropdownMenuItem(onClick = {
                                selectedLanguage = it
                                onLanguageChange(it)
                                expandedInLanguage = false
                            }) {
                                Text(it.getLanguageName())
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier.fillMaxWidth().padding(10.dp).align(Alignment.CenterHorizontally)
                ) {
                    Button(
                        onClick = { expandedInLanguage = true },
                        modifier = Modifier.align(Alignment.Center).fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = selectedTheme.colors.lightBtnBackground)
                    ) {
                        Text(
                            "${selectedLanguage.texts.settingDialogLanguageBtnText}: ${selectedLanguage.getLanguageName()}",
                            color = selectedTheme.colors.lightBtnTextColor
                        )
                    }
                }

                var isShowHelp by remember { mutableStateOf(false) }
                Text(
                    currentLanguage.texts.helpBtnText,
                    modifier = Modifier.padding(10.dp).align(Alignment.CenterHorizontally).pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                isShowHelp = !isShowHelp
                            }
                        )
                    },
                    color = selectedTheme.colors.lightBtnTextColor
                )
                if (isShowHelp) {
                    Text(
                        currentLanguage.texts.help,
                        modifier = Modifier.padding(10.dp).align(Alignment.CenterHorizontally),
                        color = selectedTheme.colors.lightBtnTextColor
                    )
                }
            }
            Button(
                onClick = onExit, modifier = Modifier.align(Alignment.BottomStart).padding(10.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = selectedTheme.colors.primaryBtnBackground)
            ) {
                Text(selectedLanguage.texts.settingDialogExit, color = selectedTheme.colors.primaryBtnTextColor)
            }
            Button(
                onClick = onDismiss, modifier = Modifier.align(Alignment.BottomEnd).padding(10.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = selectedTheme.colors.primaryBtnBackground)
            ) {
                Text(selectedLanguage.texts.settingDialogBackBtnText, color = selectedTheme.colors.primaryBtnTextColor)
            }
        }
    }
}

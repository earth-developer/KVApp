package ui

import EThemeColor
import LText
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.WindowState
import ecompose.compose.EditAndList
import ecompose.utils.sl.SLDataManager
import ecompose.windows.FlowWindow
import model.Group
import model.KVItem
import model.KVSLData
import model.WindowsSLData
import java.awt.Desktop
import java.io.File


@Composable
fun ApplicationScope.EWindow(
    isVisible: Boolean,
    windowState: WindowState,
    windowsSLData: WindowsSLData,
    onWindowClose: () -> Unit
) {
    val settingDialogVisible = remember { mutableStateOf(false) }
    val theme = (windowsSLData.currentThemeOrdinal).let { EThemeColor.Theme.entries[it] }
    val language = (windowsSLData.currentLanguageOrdinal).let { LText.Language.entries[it] }
    var currentTheme by remember { mutableStateOf(theme) }
    var currentLanguage by remember { mutableStateOf(language) }
    var isAdjustWidths by remember { mutableStateOf(false) }
    FlowWindow(
        onSetting = { settingDialogVisible.value = true },
        title = currentLanguage.texts.windowsTitle,
        windowTitleStyle = MaterialTheme.typography.h6,
        windowBackgroundColor = currentTheme.colors.windowBackgroundColor,
        windowDraggableAreaColor = currentTheme.colors.windowDraggableAreaColor,
        windowDraggableAreaHeight = currentTheme.colors.windowDraggableAreaHeight,
        windowTitleColor = currentTheme.colors.windowTitleColor,
        windowIconColor = currentTheme.colors.windowIconColor,
        state = windowState, isVisible = isVisible, onAppClose = { onWindowClose() }) {
        AppContent(currentTheme, currentLanguage, isAdjustWidths, { isAdjustWidths = false })
        SettingDialog(
            settingDialogVisible.value,
            currentLanguage = currentLanguage,
            currentTheme = currentTheme,
            onAdjustWidths = {
                isAdjustWidths = !isAdjustWidths
                settingDialogVisible.value = false
            },
            onExportCsv = {
                val kvSLData = SLDataManager.dataMap[KVSLData.KEY] as KVSLData
                //联合两个表
                val csvData = kvSLData.groups.map { group ->
                    kvSLData.kvItems.filter { it.groupId == group.id }.map { kvItem ->
                        listOf(group.name, kvItem.title, kvItem.key, kvItem.value)
                    }
                }.flatten()
                //在第一行加上标题
                val csvDataWithTitle = listOf(listOf("Group", "Title", "Key", "Value")) + csvData
                //导出
                val file = File("kv_${System.currentTimeMillis()}.csv")
                file.writeText(csvDataWithTitle.joinToString("\n") { it.joinToString(",") })
                //打开文件 awt
                Desktop.getDesktop().open(file)
            },
            onLanguageChange = {
                LText.switchLanguage(it)
                windowsSLData.currentLanguageOrdinal = it.ordinal
                SLDataManager.setAndSave(WindowsSLData.KEY, windowsSLData)
                currentLanguage = it
            },
            onExit = ::exitApplication,
            onThemeChange = {
                EThemeColor.switchTheme(it)
                windowsSLData.currentThemeOrdinal = it.ordinal
                SLDataManager.setAndSave(WindowsSLData.KEY, windowsSLData)
                currentTheme = it
            },
            onDismiss = {
                settingDialogVisible.value = false
            }
        )
    }
}

@Composable
fun AppContent(
    currentTheme: EThemeColor.Theme,
    currentLanguage: LText.Language,
    isAdjustWidths: Boolean, onAdjustDismiss: () -> Unit
) {
    //全局数据
    val windowsSLData by remember { mutableStateOf(SLDataManager.dataMap[WindowsSLData.KEY] as WindowsSLData) }
    val kvSLData by remember { mutableStateOf(SLDataManager.dataMap[KVSLData.KEY] as KVSLData) }
    var groups by remember { mutableStateOf(kvSLData.groups) }
    var kvItems by remember { mutableStateOf(kvSLData.kvItems) }

    var nowGroup by remember { mutableStateOf(kvSLData.groups.firstOrNull()) }
    var nowItems by remember { mutableStateOf(kvItems.filter { it.groupId == nowGroup?.id }) }
    var nowKVItem by remember { mutableStateOf(nowItems.firstOrNull()) }

    fun addGroup() {
        val newGroup = Group(currentLanguage.texts.newGroupName)
        groups = groups.toMutableList().apply { add(newGroup) }
        kvSLData.groups = groups
        SLDataManager.setAndSave(KVSLData.KEY, kvSLData)
        nowGroup = newGroup

        nowItems = kvItems.filter { it.groupId == nowGroup?.id }
        nowKVItem = nowItems.firstOrNull()
    }

    fun addKVItem() {
        if (nowGroup == null) return
        val newItem = KVItem(
            currentLanguage.texts.newKVItemTitle,
            currentLanguage.texts.newKVItemKey,
            currentLanguage.texts.newKVItemValue, nowGroup!!.id
        )
        kvItems = kvItems.toMutableList().apply { add(newItem) }
        kvSLData.kvItems = kvItems
        SLDataManager.setAndSave(KVSLData.KEY, kvSLData)
        nowItems = kvItems.filter { it.groupId == nowGroup?.id }
        nowKVItem = newItem
    }

    fun deleteGroup(group: Group) {
        groups = groups.toMutableList().apply { remove(group) }
        kvSLData.groups = groups
        SLDataManager.setAndSave(KVSLData.KEY, kvSLData)
        nowGroup = groups.firstOrNull()
        nowItems = kvItems.filter { it.groupId == nowGroup?.id }
        nowKVItem = nowItems.firstOrNull()
    }

    fun deleteKVItem(kvItem: KVItem) {
        kvItems = kvItems.toMutableList().apply { remove(kvItem) }
        kvSLData.kvItems = kvItems
        SLDataManager.setAndSave(KVSLData.KEY, kvSLData)
        nowItems = kvItems.filter { it.groupId == nowGroup?.id }
        nowKVItem = nowItems.firstOrNull()
    }

    fun updateKVItem(kvItem: KVItem) {
        kvItems =
            kvItems.toMutableList().apply { set(index = kvItems.indexOfFirst { it.id == kvItem.id }, element = kvItem) }
        kvSLData.kvItems = kvItems
        SLDataManager.setAndSave(KVSLData.KEY, kvSLData)
        nowItems = kvItems.filter { it.groupId == nowGroup?.id }
        nowKVItem = kvItem
    }

    fun updateGroup(group: Group) {
        groups =
            groups.toMutableList().apply { set(index = groups.indexOfFirst { it.id == group.id }, element = group) }
        kvSLData.groups = groups
        SLDataManager.setAndSave(KVSLData.KEY, kvSLData)
        nowGroup = group
        nowItems = kvItems.filter { it.groupId == nowGroup?.id }
        nowKVItem = nowItems.firstOrNull()
    }

    fun changeGroup(group: Group) {
        nowGroup = group
        nowItems = kvItems.filter { it.groupId == nowGroup?.id }
        nowKVItem = nowItems.firstOrNull()
    }

    fun changeKVItem(kvItem: KVItem) {
        nowKVItem = kvItem
    }

    // 新增的宽度状态
    var groupWidth by remember { mutableStateOf(windowsSLData.groupWidth) }
    var titleWight by remember { mutableStateOf(windowsSLData.titleWeight) }
    var keyWight by remember { mutableStateOf(windowsSLData.keyWeight) }
    var valueWight by remember { mutableStateOf(windowsSLData.valueWeight) }

    Column {
        if (isAdjustWidths) {
            Column(
                modifier = Modifier.background(currentTheme.colors.groupNowBackground).padding(5.dp, 5.dp, 5.dp, 0.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(5.dp, 5.dp, 5.dp, 0.dp)
                ) {
                    Text(
                        currentLanguage.texts.adjustWidthsAreaTitle,
                        style = MaterialTheme.typography.h6,
                        color = currentTheme.colors.groupNowTextColor,
                        modifier = Modifier.align(Alignment.CenterStart),
                        textAlign = TextAlign.Center
                    )
                    IconButton(
                        onClick = {
                            windowsSLData.groupWidth = groupWidth
                            windowsSLData.titleWeight = titleWight
                            windowsSLData.keyWeight = keyWight
                            windowsSLData.valueWeight = valueWight
                            onAdjustDismiss()
                            SLDataManager.setAndSave(WindowsSLData.KEY, windowsSLData)
                        },
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Icon(
                            Icons.Default.Done,
                            contentDescription = currentLanguage.texts.adjustWidthsAreaSaveBtnDescription,
                            tint = currentTheme.colors.groupNowTextColor
                        )
                    }
                }
                Row {
                    Text(
                        "${currentLanguage.texts.adjustWidthsAreaGroupWidth}: ${groupWidth.toInt()} f",
                        color = currentTheme.colors.groupNowTextColor
                    )
                    Slider(
                        value = groupWidth, onValueChange = { groupWidth = it }, valueRange = 0f..300f, colors =
                            SliderDefaults.colors(
                                thumbColor = currentTheme.colors.groupNowTextColor,
                                activeTrackColor = currentTheme.colors.groupNowTextColor,
                                inactiveTrackColor = currentTheme.colors.groupNowTextColor
                            )
                    )
                }
                Row {
                    Text(
                        "${currentLanguage.texts.adjustWidthsAreaTitleWidth}: ${titleWight.toInt()} f",
                        color = currentTheme.colors.groupNowTextColor
                    )
                    Slider(
                        value = titleWight, onValueChange = { titleWight = it }, valueRange = 1f..2f, colors =
                            SliderDefaults.colors(
                                thumbColor = currentTheme.colors.groupNowTextColor,
                                activeTrackColor = currentTheme.colors.groupNowTextColor,
                                inactiveTrackColor = currentTheme.colors.groupNowTextColor
                            )
                    )
                }
                Row {
                    Text(
                        "${currentLanguage.texts.adjustWidthsAreaKeyWidth}: ${keyWight.toInt()} f",
                        color = currentTheme.colors.groupNowTextColor
                    )
                    Slider(
                        value = keyWight, onValueChange = { keyWight = it }, valueRange = 1f..2f, colors =
                            SliderDefaults.colors(
                                thumbColor = currentTheme.colors.groupNowTextColor,
                                activeTrackColor = currentTheme.colors.groupNowTextColor,
                                inactiveTrackColor = currentTheme.colors.groupNowTextColor
                            )
                    )
                }
                Row {
                    Text(
                        "${currentLanguage.texts.adjustWidthsAreaValueWidth}: ${valueWight.toInt()} f",
                        color = currentTheme.colors.groupNowTextColor
                    )
                    Slider(
                        value = valueWight, onValueChange = { valueWight = it }, valueRange = 1f..2f, colors =
                            SliderDefaults.colors(
                                thumbColor = currentTheme.colors.groupNowTextColor,
                                activeTrackColor = currentTheme.colors.groupNowTextColor,
                                inactiveTrackColor = currentTheme.colors.groupNowTextColor
                            )
                    )
                }
            }
        }
        Row {
            Box(
                modifier = Modifier.width(groupWidth.dp).defaultMinSize(
                    100.dp,
                    0.dp
                ).fillMaxHeight().background(currentTheme.colors.groupNormalBackground)
            ) {
                EditAndList(
                    language = currentLanguage,
                    list = groups,
                    nowT = nowGroup,
                    onNowChange = {
                        it?.let { it1 -> changeGroup(it1) }
                    },
                    onDoubleTapInBlank = {
                        addGroup()
                    },
                    onDelete = {
                        it?.let { it1 -> deleteGroup(it1) }
                    },
                    TextContent = {
                        if (it == null) return@EditAndList
                        Box(
                            modifier = Modifier.fillMaxWidth().height(50.dp)
                                .background(if (it == nowGroup) currentTheme.colors.groupNowBackground else currentTheme.colors.groupNormalBackground)
                        ) {
                            Text(
                                it.name,
                                color = if (it == nowGroup) currentTheme.colors.groupNowTextColor else currentTheme.colors.groupNormalTextColor,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth().align(Alignment.Center)
                            )
                        }

                    },
                    TextFieldContent = { group ->
                        if (group == null) return@EditAndList
                        var name by remember { mutableStateOf(group.name) }
                        TextField(
                            value = name, onValueChange = {
                                name = it
                                updateGroup(group.copy(name = it))
                            },
                            singleLine = true,
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = if (group == nowGroup) currentTheme.colors.groupNowBackground else currentTheme.colors.groupNormalBackground,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                            ),
                            modifier = Modifier.fillMaxWidth().align(Alignment.Center)
                        )
                    }
                )
            }
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                EditAndList(
                    language = currentLanguage,
                    list = nowItems,
                    nowT = nowKVItem,
                    onNowChange = {
                        it?.let { it1 -> changeKVItem(it1) }
                    },
                    onDoubleTapInBlank = {
                        addKVItem()
                    },
                    onDelete = {
                        it?.let { it1 -> deleteKVItem(it1) }
                    },
                    TextContent = {
                        if (it == null) return@EditAndList
                        Row(
                            modifier = Modifier.fillMaxWidth().height(50.dp)
                                .background(if (it == nowKVItem) currentTheme.colors.kvNowBackground else currentTheme.colors.kvNormalBackground),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                it.title, modifier = Modifier.weight(titleWight),
                                color = if (it == nowKVItem) currentTheme.colors.kvNowTextColor else currentTheme.colors.kvNormalTextColor,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                it.key, modifier = Modifier.weight(keyWight),
                                color = if (it == nowKVItem) currentTheme.colors.kvNowTextColor else currentTheme.colors.kvNormalTextColor,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                it.value, modifier = Modifier.weight(valueWight),
                                color = if (it == nowKVItem) currentTheme.colors.kvNowTextColor else currentTheme.colors.kvNormalTextColor,
                                textAlign = TextAlign.Center
                            )
                        }
                    },
                    TextFieldContent = { kvItem ->
                        if (kvItem == null) return@EditAndList
                        var title by remember { mutableStateOf(kvItem.title) }
                        var key by remember { mutableStateOf(kvItem.key) }
                        var value by remember { mutableStateOf(kvItem.value) }
                        Row {
                            TextField(
                                value = title, onValueChange = {
                                    title = it
                                    updateKVItem(kvItem.copy(title = it))
                                }, modifier = Modifier.weight(titleWight),
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = if (kvItem == nowKVItem) currentTheme.colors.kvNowBackground else currentTheme.colors.kvNormalBackground,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                )
                            )
                            TextField(
                                value = key, onValueChange = {
                                    key = it
                                    updateKVItem(kvItem.copy(key = it))
                                }, modifier = Modifier.weight(keyWight),
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = if (kvItem == nowKVItem) currentTheme.colors.kvNowBackground else currentTheme.colors.kvNormalBackground,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                )
                            )
                            TextField(
                                value = value, onValueChange = {
                                    value = it
                                    updateKVItem(kvItem.copy(value = it))
                                }, modifier = Modifier.weight(valueWight),
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = if (kvItem == nowKVItem) currentTheme.colors.kvNowBackground else currentTheme.colors.kvNormalBackground,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                )
                            )
                        }
                    }
                )
            }
        }
    }
}

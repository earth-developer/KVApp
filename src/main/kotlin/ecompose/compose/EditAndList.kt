package ecompose.compose

import LText
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerButton
import androidx.compose.ui.input.pointer.PointerType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> EditAndList(
    language: LText.Language,
    list: List<T>,
    nowT: T,
    onNowChange: (T) -> Unit,
    onDoubleTapInBlank: () -> Unit = {},
    onDelete: (T) -> Unit = {},
    TextContent: @Composable (T) -> Unit,
    TextFieldContent: @Composable (T) -> Unit,
) {
    if (list.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxHeight().fillMaxWidth().pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        onDoubleTapInBlank()
                    }
                )
            },
            contentAlignment = Alignment.Center
        ) {
            Text(
                language.texts.editAndListAddNewItemDescription, modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h6
            )
        }
        return
    }

    var isEdit by remember { mutableStateOf(false) }
    val showMenu = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isEdit = false
                    },
                    onDoubleTap = {
                        onDoubleTapInBlank()
                    }
                )
            }
    ) {
        list.forEach { item ->
            if (isEdit && item == nowT) {
                TextFieldContent(item)
            } else {
                Box(
                    modifier = Modifier
                        .pointerInput(item) {
                            detectTapGestures(
                                onDoubleTap = {
                                    isEdit = true
                                    onNowChange(item)
                                },
                                onLongPress = {
                                    showMenu.value = true
                                },
                                onPress = {
                                    isEdit = false
                                    onNowChange(item)
                                }
                            )
                        }
                        .onClick(matcher = PointerMatcher.pointer(PointerType.Mouse, PointerButton.Secondary)){
                            onNowChange(item)
                            showMenu.value = true
                        }
                ) {
                    TextContent(item)
                    // 下拉菜单
                    if (item == nowT) {
                        DropdownMenu(
                            expanded = showMenu.value,
                            onDismissRequest = { showMenu.value = false }
                        ) {
                            DropdownMenuItem(onClick = {
                                showMenu.value = false
                                onDelete(item)
                            }) {
                                Text(language.texts.editAndListMenuDelete)
                            }
                        }
                    }
                }
            }
        }

        // 添加一个空白区域，保持与项目高度一致
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp) // 假设每个项目的高度为 50.dp
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = {
                            onDoubleTapInBlank()
                        }
                    )
                }
        ) {
            // 空白区域可以添加提示文本
            Text(
                "+",
                modifier = Modifier.align(Alignment.Center).onClick{
                    onDoubleTapInBlank()
                },
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body2
            )
        }
    }
}
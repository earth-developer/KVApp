package ecompose.windows

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import java.awt.Cursor

@Composable
@Preview
private fun FrameWindowScope.FlowApp(
    onClose: () -> Unit,
    onSetting: () -> Unit = {},
    title: String = "FlowApp",
    windowBackgroundColor: Color = Color.White,
    windowDraggableAreaColor: Color = Color.Blue,
    windowDraggableAreaHeight: Dp = 50.dp,
    windowTitleColor: Color = Color.Black,
    windowTitleStyle: TextStyle = MaterialTheme.typography.h6,
    windowIconColor: Color = Color.Gray,
    content: @Composable() (FrameWindowScope.()->Unit)
) {
    MaterialTheme {
        Column(
            modifier = Modifier
                .background(windowBackgroundColor)
                .fillMaxSize()
        ) {
            WindowDraggableArea {
                Box(
                    modifier = Modifier
                        .background(windowDraggableAreaColor)
                        .fillMaxWidth()
                        .height(windowDraggableAreaHeight)
                        .pointerHoverIcon(PointerIcon(Cursor(Cursor.HAND_CURSOR)))
                ){
                    Text(title, modifier = Modifier.align(Alignment.Center),
                        color = windowTitleColor,
                        style = windowTitleStyle)
                    Row(
                        modifier = Modifier.align(Alignment.CenterEnd).padding(4.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(
                            onClick = onSetting
                        ){
                            Icon(Icons.Default.Settings, "setting", tint = windowIconColor)
                        }
                        IconButton(
                            onClick = onClose
                        ) {
                            Icon(Icons.Default.Close, "close", tint = windowIconColor)
                        }
                    }
                }
            }
            content()
        }
    }
}

@Composable
fun ApplicationScope.FlowWindow(state: WindowState,
                                isVisible: Boolean,
                                title : String = "FlowApp",
                                windowBackgroundColor: Color = Color.White,
                                windowDraggableAreaColor: Color = Color.Blue,
                                windowDraggableAreaHeight: Dp = 50.dp,
                                windowTitleColor: Color = Color.Black,
                                windowTitleStyle: TextStyle = MaterialTheme.typography.h6,
                                windowIconColor: Color = Color.Gray,
                                onSetting: () -> Unit = {},
                                onAppClose: () -> Unit,
                                content:@Composable() (FrameWindowScope.() -> Unit)){
    Window(
        onCloseRequest = ::exitApplication,
        state = state,
        undecorated = true,
        transparent = true,
        alwaysOnTop = true,
        visible = isVisible
    ) {
        FlowApp(
            onSetting = onSetting,
            windowBackgroundColor = windowBackgroundColor,
            windowDraggableAreaColor = windowDraggableAreaColor,
            windowDraggableAreaHeight = windowDraggableAreaHeight,
            windowTitleColor = windowTitleColor,
            windowTitleStyle = windowTitleStyle,
            windowIconColor = windowIconColor,
            title = title,
            onClose = {onAppClose()},
            content = content
        )
    }
}
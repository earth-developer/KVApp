import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import ecompose.utils.sl.SLDataManager
import ecompose.windows.TrayManager
import model.KVSLData
import model.WindowsSLData
import ui.EWindow
import java.awt.MenuItem
import java.awt.PopupMenu
import java.awt.TrayIcon
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.imageio.ImageIO

fun main() = application {
    //初始化数据管理器
    RunInit.register {
        SLDataManager.register(WindowsSLData.KEY, WindowsSLData())
        SLDataManager.register(KVSLData.KEY, KVSLData())
        SLDataManager.load()
    }
    RunInit.run()
    //窗口
    val windowsSLData = SLDataManager.dataMap[WindowsSLData.KEY] as WindowsSLData
    var isVisible by remember { mutableStateOf(true) }
    val windowState = remember {
        WindowState(
            placement = WindowPlacement.Floating,
            position = if (windowsSLData.position == DpOffset.Zero) WindowPosition(Alignment.Center)
            else WindowPosition(
                windowsSLData.position.x, windowsSLData.position.y
            ),
            size = windowsSLData.size
        )
    }
    DisposableEffect(windowState.position, windowState.size) {
        onDispose {
            windowsSLData.size = windowState.size
            windowsSLData.position = DpOffset(windowState.position.x, windowState.position.y)
            SLDataManager.setAndSave(WindowsSLData.KEY, windowsSLData)
        }
    }
    EWindow(
        isVisible = isVisible,
        windowState = windowState,
        windowsSLData = windowsSLData,
        onWindowClose = { isVisible = false }
    )

    //托盘
//    val language = LText.Language.entries[windowsSLData.currentLanguageOrdinal] 编码问题
    val language = LText.Language.English
    TrayManager.setTray(
        PopupMenu().apply {
            add(MenuItem(language.texts.trayShowOrHide).apply { addActionListener { isVisible = !isVisible } })
            addSeparator()
            add(MenuItem(language.texts.trayExit).apply { addActionListener { exitApplication() } })
        },
        TrayIcon(ImageIO.read(javaClass.classLoader.getResource("task.png")), language.texts.trayTooltip),
        object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) =
                if (e.button == MouseEvent.BUTTON1) isVisible = !isVisible else Unit
        })
}


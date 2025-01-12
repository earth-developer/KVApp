package ecompose.windows

import java.awt.PopupMenu
import java.awt.SystemTray
import java.awt.TrayIcon
import java.awt.event.MouseListener

object TrayManager{

    private var isAddTray = false
    private val tray: SystemTray = SystemTray.getSystemTray()

    fun setTray(popupMenu: PopupMenu,trayIcon: TrayIcon,mouseListener: MouseListener) {
        if (!isAddTray) {
            isAddTray = true
            trayIcon.isImageAutoSize = true
            trayIcon.popupMenu = popupMenu
            trayIcon.addMouseListener(mouseListener)
            tray.add(trayIcon)
        }
    }
}

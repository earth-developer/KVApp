object LText {
    var currentLanguage: Language = Language.English

    enum class Language(val texts: Texts) {
        English(
            Texts(
                trayShowOrHide = "Show/Hide",
                trayExit = "Exit",
                trayTooltip = "KVApp",

                settingDialogTitle = "Settings",
                settingDialogThemeBtnText = "Theme",
                settingDialogLanguageBtnText = "Language",
                settingDialogAdjustWidthsBtnText = "Adjust Widths",
                settingDialogBackBtnText = "Back",
                settingDialogExit = "Exit App",
                settingDialogExportCsv = "Export as CSV",

                windowsTitle = "KVApp",
                newGroupName = "New Group",
                newKVItemTitle = "New KV Item",
                newKVItemKey = "New Key",
                newKVItemValue = "New Value",

                adjustWidthsAreaTitle = "Adjust Widths",
                adjustWidthsAreaSaveBtnDescription = "Save",
                adjustWidthsAreaGroupWidth = "Group Width",
                adjustWidthsAreaTitleWidth = "Title Width",
                adjustWidthsAreaKeyWidth = "Key Width",
                adjustWidthsAreaValueWidth = "Value Width",

                editAndListAddNewItemDescription = "Double tap to add new item",
                editAndListMenuDelete = "Delete",

                help = """
                    help:
                    Program auto hide, exit in tray right click menu
                    Double tap in blank to add new item
                    Double tap item to edit
                    Long press item to delete
                    Right click menu to delete
                    Developer email: 
                    earth_developer@outlook.com
                """.trimIndent(),
                helpBtnText = "Help"
            )
        ),
        Chinese(
            Texts(
                trayShowOrHide = "显示/隐藏",
                trayExit = "退出",
                trayTooltip = "KVApp",

                settingDialogTitle = "设置",
                settingDialogThemeBtnText = "主题",
                settingDialogLanguageBtnText = "语言",
                settingDialogAdjustWidthsBtnText = "调整宽度",
                settingDialogBackBtnText = "返回",
                settingDialogExit = "退出应用",
                settingDialogExportCsv = "导出为CSV",

                windowsTitle = "KVApp",
                newGroupName = "新组",
                newKVItemTitle = "新的键值项",
                newKVItemKey = "新键",
                newKVItemValue = "新值",

                adjustWidthsAreaTitle = "调整宽度",
                adjustWidthsAreaSaveBtnDescription = "保存",
                adjustWidthsAreaGroupWidth = "组宽度",
                adjustWidthsAreaTitleWidth = "标题宽度",
                adjustWidthsAreaKeyWidth = "键宽度",
                adjustWidthsAreaValueWidth = "值宽度",

                editAndListAddNewItemDescription = "双击以添加新项",
                editAndListMenuDelete = "删除",

                help = """
                    帮助:
                    程序自动隐藏、在任务栏托盘中右键菜单退出
                    双击空白处以添加新项
                    双击项以编辑
                    长按项以删除
                    右键菜单以删除
                    开发者邮箱: earth_developer@outlook.com
                """.trimIndent(),
                helpBtnText = "帮助"
            )
        );

        fun getLanguageName(): String {
            return if (name == "Chinese") {
                when (this) {
                    English -> "英语"
                    Chinese -> "简体中文"
                }
            } else {
                name.lowercase().replaceFirstChar { it.uppercaseChar() }
            }
        }
    }

    data class Texts(
        val trayShowOrHide: String,
        val trayExit: String,
        val trayTooltip: String,

        val settingDialogTitle: String,
        val settingDialogThemeBtnText: String,
        val settingDialogAdjustWidthsBtnText: String,
        val settingDialogBackBtnText: String,
        val settingDialogLanguageBtnText: String,
        val settingDialogExit: String,
        val settingDialogExportCsv: String,

        val windowsTitle: String,
        val newGroupName: String,
        val newKVItemTitle: String,
        val newKVItemKey: String,
        val newKVItemValue: String,

        val adjustWidthsAreaTitle: String,
        val adjustWidthsAreaSaveBtnDescription: String,
        val adjustWidthsAreaGroupWidth: String,
        val adjustWidthsAreaTitleWidth: String,
        val adjustWidthsAreaKeyWidth: String,
        val adjustWidthsAreaValueWidth: String,

        val editAndListAddNewItemDescription: String = "Double tap to add new item",
        val editAndListMenuDelete: String = "Delete",

        val help: String,
        val helpBtnText:String
    )


    fun switchLanguage(language: Language) {
        currentLanguage = language
    }
}
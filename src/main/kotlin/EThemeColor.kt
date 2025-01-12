import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object EThemeColor {
    var currentTheme: Theme = Theme.Blue

    enum class Theme(val colors: Colors) {
        Blue(
            Colors(
                primaryBtnBackground = Color(0xff0d47a1),
                primaryBtnTextColor = Color.White,
                dialogBackgroundColor = Color(0xffffffff),
                windowBackgroundColor = Color(0xffffffff),
                windowDraggableAreaColor = Color(0xffbbdefb),
                windowDraggableAreaHeight = 50.dp,
                windowTitleColor = Color(0xff0d47a1),
                windowIconColor = Color(0xff0d47a1),
                groupNormalBackground = Color(0xffe3f2fd),
                groupNowBackground = Color(0xff90caf9),
                groupNormalTextColor = Color(0xff0d47a1),
                groupNowTextColor = Color(0xff0d47a1),
                kvNormalBackground = Color(0xffe3f2fd),
                kvNowBackground = Color(0xff90caf9),
                kvNormalTextColor = Color(0xff0d47a1),
                kvNowTextColor = Color(0xff0d47a1),
                lightBtnBackground = Color(0xFFF1F1F1),   // 浅色按钮背景颜色（白色）
                lightBtnTextColor = Color(0xFF757575)     // 浅色按钮文本颜色（灰色）
            )
        ),
        MINT(
            Colors(
                primaryBtnBackground = Color(0xFF00796B),
                primaryBtnTextColor = Color.White,
                dialogBackgroundColor = Color(0xFFF0F8FF),
                windowBackgroundColor = Color(0xFFF0F8FF),
                windowDraggableAreaColor = Color(0xFFA5D6A7),
                windowDraggableAreaHeight = 50.dp,
                windowTitleColor = Color(0xFF00796B),
                windowIconColor = Color(0xFF00796B),
                groupNormalBackground = Color(0xFFE8F5E9),
                groupNowBackground = Color(0xFF81C784),
                groupNormalTextColor = Color(0xFF00796B),
                groupNowTextColor = Color(0xFF00796B),
                kvNormalBackground = Color(0xFFE8F5E9),
                kvNowBackground = Color(0xFF81C784),
                kvNormalTextColor = Color(0xFF00796B),
                kvNowTextColor = Color(0xFF00796B),
                lightBtnBackground = Color(0xFFE6F3EE),   // 浅色按钮背景颜色（薄荷白）
                lightBtnTextColor = Color(0xFF00796B)     // 浅色按钮文本颜色（薄荷绿）
            )
        ),
        PASTEL(
            Colors(
                primaryBtnBackground = Color(0xFFFFAB00),
                primaryBtnTextColor = Color.Black,
                dialogBackgroundColor = Color(0xFFFFFFFF),
                windowBackgroundColor = Color(0xFFFFFFFF),
                windowDraggableAreaColor = Color(0xFFFFF8E1),
                windowDraggableAreaHeight = 50.dp,
                windowTitleColor = Color(0xFFFFAB00),
                windowIconColor = Color(0xFFFFAB00),
                groupNormalBackground = Color(0xFFFFF8E1),
                groupNowBackground = Color(0xFFFFF176),
                groupNormalTextColor = Color(0xFFFFAB00),
                groupNowTextColor = Color(0xFFFFAB00),
                kvNormalBackground = Color(0xFFFFF8E1),
                kvNowBackground = Color(0xFFFFF176),
                kvNormalTextColor = Color(0xFFFFAB00),
                kvNowTextColor = Color(0xFFFFAB00),
                lightBtnBackground = Color(0xFFFFFDE7),   // 浅色按钮背景颜色（淡黄色）
                lightBtnTextColor = Color(0xFF4E342E)     // 浅色按钮文本颜色（棕褐色）
            )
        ),
        ROSE(
            Colors(
                primaryBtnBackground = Color(0xFFE91E63),
                primaryBtnTextColor = Color.White,
                dialogBackgroundColor = Color(0xFFFFF0F8),
                windowBackgroundColor = Color(0xFFFFF0F8),
                windowDraggableAreaColor = Color(0xFFF8BBD0),
                windowDraggableAreaHeight = 50.dp,
                windowTitleColor = Color(0xFFE91E63),
                windowIconColor = Color(0xFFE91E63),
                groupNormalBackground = Color(0xFFF8BBD0),
                groupNowBackground = Color(0xFFFF80AB),
                groupNormalTextColor = Color(0xFFE91E63),
                groupNowTextColor = Color(0xFFE91E63),
                kvNormalBackground = Color(0xFFF8BBD0),
                kvNowBackground = Color(0xFFFF80AB),
                kvNormalTextColor = Color(0xFFE91E63),
                kvNowTextColor = Color(0xFFE91E63),
                lightBtnBackground = Color(0xFFFFF0F8),   // 浅色按钮背景颜色（玫瑰白）
                lightBtnTextColor = Color(0xFFE91E63)     // 浅色按钮文本颜色（玫瑰红）
            )
        );

        fun getThemeName(language: LText.Language): String {
            return if (language.name == "Chinese") {
                when (this) {
                    Blue -> "蓝色主题"
                    MINT -> "薄荷主题"
                    PASTEL -> "柔和主题"
                    ROSE -> "玫瑰主题"
                }
            } else {
                name.lowercase().replaceFirstChar { it.uppercase() }
            }
        }
    }

    data class Colors(
        val primaryBtnBackground: Color = Color(0xff0d47a1),
        val primaryBtnTextColor: Color = Color.White,
        val dialogBackgroundColor: Color = Color.White,
        val windowBackgroundColor: Color = Color.White,
        val windowDraggableAreaColor: Color = Color.Blue,
        val windowDraggableAreaHeight: Dp = 50.dp,
        val windowTitleColor: Color = Color.Black,
        val windowIconColor: Color = Color.Gray,
        val groupNormalBackground: Color,
        val groupNowBackground: Color,
        val groupNormalTextColor: Color,
        val groupNowTextColor: Color,
        val kvNormalBackground: Color,
        val kvNowBackground: Color,
        val kvNormalTextColor: Color,
        val kvNowTextColor: Color,
        val lightBtnBackground: Color = Color.LightGray,  // 默认浅色按钮背景颜色
        val lightBtnTextColor: Color = Color.DarkGray     // 默认浅色按钮文本颜色
    )

    fun switchTheme(theme: Theme) {
        currentTheme = theme
    }
}
package com.fphoenixcorneae.eyepetizer.mvi.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalThemeColors = staticCompositionLocalOf { WhiteColors as AppColors }

/**
 * @desc：App颜色基类
 * @date：2023/08/07 17:31
 */
sealed interface AppColors {
    val colorPrimary: Color
    val colorPrimaryDark: Color
    val colorAccent: Color
    val backgroundColor: Color
    val dividerColor: Color
    val textColor: Color
    val textColorSecondary: Color
    val textColorTertiary: Color
    val contentTextColor: Color
    val contentTextColorSecondary: Color
}

/**
 * @desc：白色主题颜色
 * @date：2023/08/07 17:49
 */
object WhiteColors : AppColors {
    override val colorPrimary: Color get() = Color(0xFFFAFAFA)
    override val colorPrimaryDark: Color get() = Color(0xFFFAFAFA)
    override val colorAccent: Color get() = Color(0xFF61F2D4)
    override val backgroundColor: Color get() = Color.White
    override val dividerColor: Color get() = Color(0xFFEEEEEE)
    override val textColor: Color get() = Color(0xFF333333)
    override val textColorSecondary: Color get() = Color(0xFF444444)
    override val textColorTertiary: Color get() = Color(0xFF888888)
    override val contentTextColor: Color get() = Color(0xFF444444)
    override val contentTextColorSecondary: Color get() = Color(0xFF666666)
}

/**
 * @desc：黑色主题颜色
 * @date：2023/08/08 10:03
 */
object BlackColors : AppColors {
    override val colorPrimary: Color get() = Color(0xFFFAFAFA)
    override val colorPrimaryDark: Color get() = Color(0xFFFAFAFA)
    override val colorAccent: Color get() = Color(0xFF61F2D4)
    override val backgroundColor: Color get() = Color.Black
    override val dividerColor: Color get() = Color(0xFFEEEEEE)
    override val textColor: Color get() = Color(0xFFDDDDDD)
    override val textColorSecondary: Color get() = Color(0xFFCCCCCC)
    override val textColorTertiary: Color get() = Color(0xFFAAAAAA)
    override val contentTextColor: Color get() = Color(0xFFCCCCCC)
    override val contentTextColorSecondary: Color get() = Color(0xFF999999)
}

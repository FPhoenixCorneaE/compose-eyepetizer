package com.fphoenixcorneae.eyepetizer.mvi.ui.theme

import androidx.compose.runtime.mutableStateOf

/**
 * @desc：主题管理类
 * @date：2023/08/07 17:25
 */
object ThemeManager {
    /** 当前主题，TODO 保存本地 */
    private val currentTheme = mutableStateOf<Theme>(Theme.White)

    fun currentTheme() = currentTheme.value

    fun switchSkin(theme: Theme) {
        currentTheme.value = theme
    }
}

/**
 * @desc：主题
 * @date：2023/08/09 10:21
 */
sealed interface Theme {
    object White : Theme
    object Black : Theme
    object Yellow : Theme
    object Red : Theme
    object Gray : Theme
}
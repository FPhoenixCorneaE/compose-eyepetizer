package com.fphoenixcorneae.compose.mvi

/**
 * @desc：默认操作
 * @date：2023/05/25 16:36
 */
sealed interface DefaultAction {
    /** 初始化 */
    object Initialize : DefaultAction

    /** 刷新 */
    object Refresh : DefaultAction
}
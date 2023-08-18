package com.fphoenixcorneae.compose.mvi

/**
 * @desc：表示副作用视图，用于表示与用户界面响应相关的副作用。
 * @date：2023/05/25 15:28
 */
sealed interface UiEffect {
    /** 显示内容 */
    data class ShowContent<out T>(val result: T?) : UiEffect

    /** 显示加载中 */
    data class ShowLoading(val message: String? = null) : UiEffect

    /** 显示空白 */
    data class ShowEmpty(val message: String?) : UiEffect

    /** 显示错误 */
    data class ShowError(val t: Throwable?) : UiEffect

    /** 显示无网络 */
    data class ShowNoNetwork(val message: String?) : UiEffect
}

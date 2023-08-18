package com.fphoenixcorneae.compose.mvi

/**
 * @desc：表示ViewModel执行Action并返回的结果，最终会被转换为UiState/UiEffect。
 * @date：2023/05/25 17:20
 */
sealed interface ActionResult {
    /** 成功 */
    data class Success<out T>(val data: T?) : ActionResult

    /** 无 */
    object Nothing : ActionResult

    /** 失败 */
    data class Failure(val cause: Throwable?) : ActionResult
}

package com.fphoenixcorneae.compose.mvi

/**
 * @desc：表示ViewModel执行Action并返回的结果。
 * @date：2023/05/25 17:41
 */
interface IResult {

    /**
     * 分发结果
     */
    fun dispatchResult(result: ActionResult)

    /**
     * 处理结果
     */
    fun processResult(result: ActionResult)
}
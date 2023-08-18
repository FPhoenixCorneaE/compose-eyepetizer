package com.fphoenixcorneae.compose.mvi

/**
 * @desc：表示用户操作或系统事件，这是通过UI事件传递给ViewModel的。
 * @date：2023/05/25 15:22
 */
interface IAction<A> {
    /**
     * 分发意图
     */
    fun dispatchIntent(action: A)

    /**
     * 处理意图
     */
    fun processIntent(action: A)
}
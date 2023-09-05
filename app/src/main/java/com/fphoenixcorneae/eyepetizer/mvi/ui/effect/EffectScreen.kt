package com.fphoenixcorneae.eyepetizer.mvi.ui.effect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.fphoenixcorneae.compose.mvi.UiEffect
import com.fphoenixcorneae.compose.mvi.uiEffect

/**
 * @desc：
 * @date：2023/09/05 17:04
 */
@Composable
fun EffectScreen(onRefresh: () -> Unit = {}, content: @Composable () -> Unit) {
    val uiEffect by uiEffect.collectAsState(UiEffect.ShowContent<Any>())
    when (uiEffect) {
        is UiEffect.ShowContent<*> -> content()
        is UiEffect.ShowEmpty -> EmptyScreen((uiEffect as UiEffect.ShowEmpty).message) {
            onRefresh()
        }

        is UiEffect.ShowError -> ErrorScreen((uiEffect as UiEffect.ShowError).t?.message) {
            onRefresh()
        }

        is UiEffect.ShowLoading -> LoadingScreen((uiEffect as UiEffect.ShowLoading).message)
        is UiEffect.ShowNoNetwork -> NoNetworkScreen((uiEffect as UiEffect.ShowNoNetwork).message) {
            onRefresh()
        }
    }
}
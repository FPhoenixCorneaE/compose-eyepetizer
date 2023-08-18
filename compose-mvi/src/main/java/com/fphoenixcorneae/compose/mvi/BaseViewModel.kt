package com.fphoenixcorneae.compose.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fphoenixcorneae.compose.ext.launch
import com.fphoenixcorneae.compose.https.ExceptionHandling
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/** 副作用视图 */
private val effectChannel by lazy { Channel<UiEffect>() }
val uiEffect: Flow<UiEffect>
    get() = effectChannel.receiveAsFlow()

/**
 * @desc：处理用户输入并通过在其内部处理结果以更新UI中的视图状态
 * @date：2023/05/25 15:12
 */
abstract class BaseViewModel<A> : ViewModel(), IAction<A>, IResult {
    /** 用户意图 */
    private val actionChannel = Channel<A>()

    /** 用户意图结果 */
    private val resultChannel = Channel<ActionResult>()

    protected fun <T> sendHttpRequest(
        call: suspend () -> T,
        showLoading: Boolean = true,
        loadingMsg: String? = null,
        judgeEmpty: ((T) -> Boolean) = { false },
        isOk: ((T) -> Boolean) = { false },
        handleResult: ((T) -> Unit) = { },
    ) = viewModelScope.launch {
        runCatching {
            if (showLoading) {
                showLoading(message = loadingMsg)
            }
            call()
        }.onSuccess {
            if (isOk(it)) {
                if (judgeEmpty(it)) {
                    dispatchResult(ActionResult.Nothing)
                } else {
                    dispatchResult(ActionResult.Success(it))
                }
            } else {
                handleResult(it)
            }
        }.onFailure {
            dispatchResult(ActionResult.Failure(ExceptionHandling.deal(it)))
        }
    }

    override fun dispatchIntent(action: A) {
        launch { actionChannel.send(action) }
    }

    override fun dispatchResult(result: ActionResult) {
        launch { resultChannel.send(result) }
    }

    /**
     * 根据自己的项目需求，可以重写该方法
     */
    override fun processResult(result: ActionResult) {
        when (result) {
            is ActionResult.Failure -> launch { showError(result.cause) }
            ActionResult.Nothing -> launch { showEmpty() }
            is ActionResult.Success<*> -> launch { showContent(result.data) }
        }
    }

    private inline fun <reified T> showContent(message: T?) {
        launch { effectChannel.send(UiEffect.ShowContent(message)) }
    }

    fun showLoading(message: String? = null) {
        launch { effectChannel.send(UiEffect.ShowLoading(message = message)) }
    }

    fun showError(t: Throwable?) {
        launch { effectChannel.send(UiEffect.ShowError(t = t)) }
    }

    fun showEmpty(message: String? = null) {
        launch { effectChannel.send(UiEffect.ShowEmpty(message = message)) }
    }

    fun showNoNetwork(message: String? = null) {
        launch { effectChannel.send(UiEffect.ShowNoNetwork(message = message)) }
    }

    init {
        launch { actionChannel.receiveAsFlow().collect { processIntent(it) } }
        launch { resultChannel.receiveAsFlow().collect { processResult(it) } }
    }
}
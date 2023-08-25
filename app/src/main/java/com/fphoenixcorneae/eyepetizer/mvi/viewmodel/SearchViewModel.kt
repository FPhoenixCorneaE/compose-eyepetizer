package com.fphoenixcorneae.eyepetizer.mvi.viewmodel

import com.fphoenixcorneae.compose.mvi.BaseViewModel
import com.fphoenixcorneae.eyepetizer.https.mainService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * @desc：
 * @date：2023/08/25 15:21
 */
class SearchViewModel : BaseViewModel<SearchAction>() {
    private val _searchUiState = MutableStateFlow(SearchUiState())
    val searchUiState = _searchUiState.asStateFlow()

    override fun processIntent(action: SearchAction) {
        when (action) {
            SearchAction.GetHotSearchKeys -> sendHttpRequest(
                call = {
                    mainService.getHotSearchKeys()
                },
                handleResult = { reply ->
                    _searchUiState.update {
                        it.copy(hotSearchKeys = reply)
                    }
                },
            )
        }
    }
}

/**
 * @desc：
 * @date：2023/08/25 16:53
 */
data class SearchUiState(
    val hotSearchKeys: List<String>? = null,
)

/**
 * @desc：
 * @date：2023/08/25 15:21
 */
sealed interface SearchAction {
    object GetHotSearchKeys : SearchAction
}
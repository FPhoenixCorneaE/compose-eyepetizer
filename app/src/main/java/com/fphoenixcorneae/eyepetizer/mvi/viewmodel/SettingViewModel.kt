package com.fphoenixcorneae.eyepetizer.mvi.viewmodel

import coil.annotation.ExperimentalCoilApi
import coil.imageLoader
import com.fphoenixcorneae.common.ext.cleanExternalCache
import com.fphoenixcorneae.common.ext.cleanInternalCache
import com.fphoenixcorneae.compose.ext.launch
import com.fphoenixcorneae.compose.ext.toast
import com.fphoenixcorneae.compose.mvi.BaseViewModel
import com.fphoenixcorneae.compose.startup.applicationContext
import com.fphoenixcorneae.eyepetizer.R
import com.fphoenixcorneae.eyepetizer.const.Constant
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.withContext

/**
 * @desc：
 * @date：2023/09/05 17:35
 */
class SettingViewModel : BaseViewModel<SettingAction>() {
    private val _settingUiState = MutableStateFlow(SettingUiState())
    val settingUiState = _settingUiState.asStateFlow()

    private fun toggleDailyUpdateReminder() {
        launch {
            _settingUiState.updateAndGet {
                it.copy(dailyUpdateReminderOpen = !it.dailyUpdateReminderOpen)
            }.also {
                MMKV.defaultMMKV()
                    .encode(Constant.SettingOption.DAILY_UPDATE_REMINDER, it.dailyUpdateReminderOpen)
            }
        }
    }

    private fun toggleWifiFollowAutoPlay() {
        launch {
            _settingUiState.updateAndGet {
                it.copy(wifiFollowAutoPlayOpen = !it.wifiFollowAutoPlayOpen)
            }.also {
                MMKV.defaultMMKV()
                    .encode(Constant.SettingOption.WIFI_FOLLOW_AUTO_PLAY, it.wifiFollowAutoPlayOpen)
            }
        }
    }

    private fun toggleTranslate() {
        launch {
            _settingUiState.updateAndGet {
                it.copy(translateOpen = !it.translateOpen)
            }.also {
                MMKV.defaultMMKV()
                    .encode(Constant.SettingOption.TRANSLATE, it.translateOpen)
            }
        }
    }

    @OptIn(ExperimentalCoilApi::class)
    private fun clearAllCache() {
        launch {
            GSYVideoManager.instance().clearAllDefaultCache(applicationContext)
            applicationContext.imageLoader.apply {
                // 清除内存缓存
                memoryCache?.clear()
                // 清除磁盘缓存
                diskCache?.clear()
            }
            cleanExternalCache()
            cleanInternalCache()
            withContext(Dispatchers.Main) {
                applicationContext.getString(R.string.clear_cache_succeed).toast()
            }
        }
    }

    override fun processIntent(action: SettingAction) {
        when (action) {
            SettingAction.ToggleDailyUpdateReminder -> toggleDailyUpdateReminder()
            SettingAction.ToggleTranslate -> toggleTranslate()
            SettingAction.ToggleWifiFollowAutoPlay -> toggleWifiFollowAutoPlay()
            SettingAction.ClearAllCache -> clearAllCache()
        }
    }
}

/**
 * @desc：
 * @date：2023/09/06 10:49
 */
data class SettingUiState(
    val dailyUpdateReminderOpen: Boolean = MMKV.defaultMMKV().decodeBool(Constant.SettingOption.DAILY_UPDATE_REMINDER),
    val wifiFollowAutoPlayOpen: Boolean = MMKV.defaultMMKV().decodeBool(Constant.SettingOption.WIFI_FOLLOW_AUTO_PLAY),
    val translateOpen: Boolean = MMKV.defaultMMKV().decodeBool(Constant.SettingOption.TRANSLATE),
)

/**
 * @desc：
 * @date：2023/09/05 17:35
 */
sealed interface SettingAction {
    object ToggleDailyUpdateReminder : SettingAction
    object ToggleWifiFollowAutoPlay : SettingAction
    object ToggleTranslate : SettingAction
    object ClearAllCache : SettingAction
}

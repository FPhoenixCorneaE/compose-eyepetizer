package com.fphoenixcorneae.eyepetizer.mvi.ui.page.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fphoenixcorneae.compose.ext.toast
import com.fphoenixcorneae.eyepetizer.R
import com.fphoenixcorneae.eyepetizer.const.Constant
import com.fphoenixcorneae.eyepetizer.ext.clickableNoRipple
import com.fphoenixcorneae.eyepetizer.ext.overScrollVertical
import com.fphoenixcorneae.eyepetizer.ext.rememberOverscrollFlingBehavior
import com.fphoenixcorneae.eyepetizer.mvi.ui.nav.NavHostController
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Black
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.GrayDark
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.LocalThemeColors
import com.fphoenixcorneae.eyepetizer.mvi.ui.widget.SystemUiScaffold
import com.fphoenixcorneae.eyepetizer.mvi.ui.widget.Toolbar
import com.fphoenixcorneae.eyepetizer.mvi.ui.widget.VerticalDivider
import com.fphoenixcorneae.eyepetizer.mvi.viewmodel.SettingAction
import com.fphoenixcorneae.eyepetizer.mvi.viewmodel.SettingViewModel

/**
 * @desc：设置
 * @date：2023/09/05 16:30
 */
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun SettingScreen() {
    val context = LocalContext.current
    val viewModel = viewModel<SettingViewModel>()
    val settingUiState by viewModel.settingUiState.collectAsState()
    val verticalScrollState = rememberScrollState()
    SystemUiScaffold(statusBarColor = LocalThemeColors.current.colorPrimary) {
        Column {
            Toolbar(titleText = stringResource(R.string.setting), background = LocalThemeColors.current.colorPrimary)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .overScrollVertical()
                    .verticalScroll(
                        state = verticalScrollState,
                        flingBehavior = rememberOverscrollFlingBehavior { verticalScrollState }),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // 日报更新提醒
                SettingOption(
                    option = stringResource(id = R.string.daily_update_reminder),
                    optionOpen = settingUiState.dailyUpdateReminderOpen,
                ) {
                    viewModel.dispatchIntent(SettingAction.ToggleDailyUpdateReminder)
                }
                // Wi-Fi 关注页自动播放
                SettingOption(
                    option = stringResource(id = R.string.wifi_follow_auto_play),
                    optionOpen = settingUiState.wifiFollowAutoPlayOpen,
                ) {
                    viewModel.dispatchIntent(SettingAction.ToggleWifiFollowAutoPlay)
                }
                // 翻译
                SettingOption(
                    option = stringResource(id = R.string.translate),
                    optionOpen = settingUiState.translateOpen,
                ) {
                    viewModel.dispatchIntent(SettingAction.ToggleTranslate)
                }
                // 清除所有缓存
                Text(
                    text = stringResource(R.string.clear_all_cache),
                    fontSize = 16.sp,
                    fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                        ?.let { FontFamily(it) },
                    modifier = Modifier
                        .padding(top = 45.dp)
                        .clickableNoRipple {
                            viewModel.dispatchIntent(SettingAction.ClearAllCache)
                        },
                )
                // 选择缓存路径
                Text(
                    text = stringResource(R.string.option_cache_path),
                    fontSize = 16.sp,
                    fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                        ?.let { FontFamily(it) },
                    modifier = Modifier
                        .padding(top = 45.dp)
                        .clickableNoRipple {
                            NavHostController.navToLogin()
                        },
                )
                // 选择播放清晰度
                Text(
                    text = stringResource(R.string.option_play_definition),
                    fontSize = 16.sp,
                    fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                        ?.let { FontFamily(it) },
                    modifier = Modifier
                        .padding(top = 45.dp)
                        .clickableNoRipple {
                            NavHostController.navToLogin()
                        },
                )
                // 选择缓存清晰度
                Text(
                    text = stringResource(R.string.option_cache_definition),
                    fontSize = 16.sp,
                    fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                        ?.let { FontFamily(it) },
                    modifier = Modifier
                        .padding(top = 45.dp)
                        .clickableNoRipple {
                            NavHostController.navToLogin()
                        },
                )
                // 检查版本更新
                Text(
                    text = stringResource(R.string.check_version_updates),
                    fontSize = 16.sp,
                    fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                        ?.let { FontFamily(it) },
                    modifier = Modifier
                        .padding(top = 45.dp)
                        .clickableNoRipple {
                            context
                                .getString(R.string.coming_soon)
                                .toast()
                        },
                )
                // 用户协议
                Text(
                    text = stringResource(R.string.user_agreement),
                    fontSize = 16.sp,
                    fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                        ?.let { FontFamily(it) },
                    modifier = Modifier
                        .padding(top = 45.dp)
                        .clickableNoRipple {
                            NavHostController.navToWeb(url = Constant.Url.USER_SERVICE_AGREEMENT)
                        },
                )
                // 隐私政策
                Text(
                    text = stringResource(R.string.privacy_policy),
                    fontSize = 16.sp,
                    fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                        ?.let { FontFamily(it) },
                    modifier = Modifier
                        .padding(top = 45.dp)
                        .clickableNoRipple {
                            NavHostController.navToWeb(url = Constant.Url.LEGAL_NOTICES)
                        },
                )
                // 视频功能声明
                Text(
                    text = stringResource(R.string.video_function_statement),
                    fontSize = 16.sp,
                    fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                        ?.let { FontFamily(it) },
                    modifier = Modifier
                        .padding(top = 45.dp)
                        .clickableNoRipple {
                            NavHostController.navToWeb(url = Constant.Url.VIDEO_FUNCTION_STATEMENT)
                        },
                )
                // 版权举报
                Text(
                    text = stringResource(R.string.copyright_report),
                    fontSize = 16.sp,
                    fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                        ?.let { FontFamily(it) },
                    modifier = Modifier
                        .padding(top = 45.dp)
                        .clickableNoRipple {
                            context
                                .getString(R.string.coming_soon)
                                .toast()
                        },
                )
                // 标语
                Text(
                    text = stringResource(R.string.setting_slogan_en),
                    color = LocalThemeColors.current.textColorTertiary,
                    fontSize = 13.sp,
                    fontFamily = ResourcesCompat.getFont(context, R.font.lobster_1_4)
                        ?.let { FontFamily(it) },
                    modifier = Modifier
                        .padding(top = 45.dp),
                )
                // 标语
                Text(
                    text = stringResource(R.string.setting_slogan_zh_CN),
                    color = LocalThemeColors.current.textColorTertiary,
                    fontSize = 12.sp,
                    fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                        ?.let { FontFamily(it) },
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 80.dp),
                )
            }
        }
    }
}

@Composable
private fun SettingOption(
    option: String,
    optionOpen: Boolean,
    onOptionClick: () -> Unit = {},
) {
    val context = LocalContext.current
    Text(
        text = option,
        fontSize = 12.sp,
        fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
            ?.let { FontFamily(it) },
        modifier = Modifier.padding(top = 45.dp),
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        modifier = Modifier.padding(top = 12.dp)
    ) {
        SettingRadioButton(
            text = stringResource(id = R.string.open),
            isChecked = optionOpen
        ) {
            onOptionClick()
        }
        VerticalDivider(thickness = 1.dp, color = Black, modifier = Modifier.height(height = 12.dp))
        SettingRadioButton(
            text = stringResource(id = R.string.close),
            isChecked = !optionOpen
        ) {
            onOptionClick()
        }
    }
}

@Composable
private fun SettingRadioButton(
    text: String,
    isChecked: Boolean = false,
    onClick: () -> Unit = {},
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier.clickableNoRipple {
            onClick()
        },
    ) {
        Text(
            text = text,
            color = if (isChecked) LocalThemeColors.current.textColorSecondary else GrayDark,
            fontSize = 15.sp,
            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                ?.let { FontFamily(it) },
            modifier = Modifier.padding(horizontal = 8.dp),
        )
    }
}
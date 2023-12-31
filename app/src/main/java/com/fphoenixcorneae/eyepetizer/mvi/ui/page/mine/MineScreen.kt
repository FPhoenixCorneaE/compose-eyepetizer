package com.fphoenixcorneae.eyepetizer.mvi.ui.page.mine

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import com.fphoenixcorneae.eyepetizer.R
import com.fphoenixcorneae.eyepetizer.const.Constant
import com.fphoenixcorneae.eyepetizer.ext.clickableNoRipple
import com.fphoenixcorneae.eyepetizer.ext.overScrollVertical
import com.fphoenixcorneae.eyepetizer.ext.rememberOverscrollFlingBehavior
import com.fphoenixcorneae.eyepetizer.mvi.ui.nav.NavHostController
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Gray
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.GrayDark
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.LocalThemeColors
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.White
import com.fphoenixcorneae.eyepetizer.mvi.ui.widget.SystemUiScaffold
import com.fphoenixcorneae.eyepetizer.mvi.ui.widget.VerticalDivider

/**
 * @desc：我的
 * @date：2023/08/09 16:02
 */
@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun MineScreen() {
    val context = LocalContext.current
    val lazyListState = rememberLazyListState()
    SystemUiScaffold {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .overScrollVertical(),
            state = lazyListState,
            flingBehavior = rememberOverscrollFlingBehavior { lazyListState },
        ) {
            stickyHeader {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(color = White),
                ) {
                    // 更多
                    Image(
                        painter = painterResource(id = R.drawable.ic_more_white_20dp),
                        contentDescription = null,
                        modifier = Modifier
                            .align(alignment = Alignment.End)
                            .size(size = 48.dp)
                            .padding(all = 14.dp)
                            .clickableNoRipple {
                                NavHostController.navToSetting()
                            },
                        colorFilter = ColorFilter.tint(color = LocalThemeColors.current.textColorSecondary),
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickableNoRipple {
                                NavHostController.navToLogin()
                            },
                    ) {
                        // 头像
                        Image(
                            painter = painterResource(id = R.drawable.ic_logo_gray_76dp),
                            contentDescription = null,
                            modifier = Modifier
                                .align(alignment = Alignment.CenterHorizontally)
                                .size(size = 76.dp)
                                .border(width = 1.dp, color = GrayDark, shape = CircleShape)
                                .background(color = Gray, shape = CircleShape),
                        )
                        // 登录提示
                        Text(
                            text = stringResource(R.string.mine_login_prompt),
                            color = GrayDark,
                            fontSize = 12.sp,
                            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                                ?.let { FontFamily(it) },
                            modifier = Modifier
                                .align(alignment = Alignment.CenterHorizontally)
                                .padding(top = 18.dp),
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = 32.dp)
                            .fillMaxWidth()
                            .height(height = 26.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        // 收藏
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
                            modifier = Modifier.clickableNoRipple {
                                NavHostController.navToLogin()
                            },
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_favorite_border_white_20dp),
                                contentDescription = null,
                                modifier = Modifier.size(size = 20.dp),
                                tint = LocalThemeColors.current.textColorTertiary,
                            )
                            Text(
                                text = stringResource(R.string.collect),
                                color = LocalThemeColors.current.textColorTertiary,
                                fontSize = 14.sp,
                                fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                                    ?.let { FontFamily(it) },
                            )
                        }
                        VerticalDivider(thickness = 0.2.dp, color = GrayDark)
                        // 缓存
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
                            modifier = Modifier.clickableNoRipple {
                                NavHostController.navToLogin()
                            },
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_cache_white_20dp),
                                contentDescription = null,
                                modifier = Modifier.size(size = 20.dp),
                                tint = LocalThemeColors.current.textColorTertiary,
                            )
                            Text(
                                text = stringResource(R.string.cache),
                                color = LocalThemeColors.current.textColorTertiary,
                                fontSize = 14.sp,
                                fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                                    ?.let { FontFamily(it) },
                            )
                        }
                    }
                    // 分割线
                    Divider(thickness = 0.2.dp, color = GrayDark, modifier = Modifier.padding(top = 18.dp))
                }
            }
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(space = 14.dp),
                    modifier = Modifier.padding(top = 14.dp, bottom = 28.dp),
                ) {
                    MineItem(text = stringResource(R.string.my_follow)) {
                        NavHostController.navToLogin()
                    }
                    MineItem(text = stringResource(R.string.watch_record)) {
                        NavHostController.navToLogin()
                    }
                    MineItem(text = stringResource(R.string.notification_switch)) {
                        NavHostController.navToLogin()
                    }
                    MineItem(text = stringResource(R.string.my_badge)) {
                        NavHostController.navToLogin()
                    }
                    MineItem(text = stringResource(R.string.feedback)) {
                        NavHostController.navToWeb(url = Constant.Url.AUTHOR_OPEN)
                    }
                    MineItem(text = stringResource(R.string.want_contribute)) {
                        NavHostController.navToWeb(url = Constant.Url.AUTHOR_OPEN)
                    }
                    Text(
                        text = String.format(
                            format = stringResource(R.string.version_show_format),
                            Constant.EYEPETIZER_VERSION_NAME,
                        ),
                        color = GrayDark,
                        fontSize = 11.sp,
                        fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                            ?.let { FontFamily(it) },
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 14.dp)
                            .fillMaxWidth(),
                    )
                }
            }
        }
    }
}

@Composable
fun MineItem(text: String, onClick: () -> Unit) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 40.dp)
            .clickableNoRipple {
                onClick()
            },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            color = LocalThemeColors.current.textColorTertiary,
            fontSize = 14.sp,
            fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                ?.let { FontFamily(it) },
        )
    }
}

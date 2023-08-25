package com.fphoenixcorneae.eyepetizer.mvi.ui.page.community

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.content.res.ResourcesCompat
import com.fphoenixcorneae.eyepetizer.R
import com.fphoenixcorneae.eyepetizer.ext.clickableNoRipple
import com.fphoenixcorneae.eyepetizer.mvi.ui.nav.NavHostController
import com.fphoenixcorneae.eyepetizer.mvi.ui.nav.NavRoute
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Gray
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.LocalThemeColors
import com.fphoenixcorneae.eyepetizer.mvi.ui.widget.SystemUiScaffold
import com.fphoenixcorneae.eyepetizer.mvi.ui.widget.TabRow
import kotlinx.coroutines.launch

/**
 * @desc：社区
 * @date：2023/08/09 16:02
 */
@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun CommunityScreen() {
    SystemUiScaffold(statusBarColor = LocalThemeColors.current.colorPrimary) {
        Column(modifier = Modifier.fillMaxSize()) {
            val context = LocalContext.current
            val pagerState = rememberPagerState(initialPage = 0)
            val coroutineScope = rememberCoroutineScope()
            val tabs = listOf(
                stringResource(R.string.commend),
                stringResource(R.string.follow),
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .zIndex(zIndex = 0.2f),
            ) {
                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    tabs = tabs,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    containerColor = LocalThemeColors.current.colorPrimary,
                    contentColor = LocalThemeColors.current.textColorTertiary,
                    selectedContentColor = LocalThemeColors.current.textColorSecondary,
                    fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                        ?.let { FontFamily(it) },
                    dividerColor = Gray,
                ) {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(it)
                    }
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_search_black_24dp),
                    contentDescription = null,
                    modifier = Modifier
                        .align(alignment = Alignment.CenterEnd)
                        .size(size = 44.dp)
                        .padding(12.dp)
                        .clickableNoRipple {
                            NavHostController
                                .get()
                                .navigate(NavRoute.SEARCH)
                        },
                )
            }
            HorizontalPager(
                pageCount = tabs.size,
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
            ) {
                if (it == 0) {
                    CommendScreen()
                } else {
                    FollowScreen()
                }
            }
        }
    }
}
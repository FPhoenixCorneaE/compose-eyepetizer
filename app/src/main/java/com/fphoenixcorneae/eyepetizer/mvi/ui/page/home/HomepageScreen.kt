package com.fphoenixcorneae.eyepetizer.mvi.ui.page.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.content.res.ResourcesCompat
import com.fphoenixcorneae.eyepetizer.R
import com.fphoenixcorneae.eyepetizer.mvi.ui.nav.NavRoute
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Gray
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.LocalThemeColors
import com.fphoenixcorneae.eyepetizer.mvi.ui.widget.SystemUiScaffold
import com.fphoenixcorneae.eyepetizer.mvi.ui.widget.TabRow
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

/**
 * @desc：首页
 * @date：2023/08/09 15:53
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomepageScreen() {
    SystemUiScaffold(statusBarColor = LocalThemeColors.current.colorPrimary) {
        Column(modifier = Modifier.fillMaxSize()) {
            val context = LocalContext.current
            val animatedNavController = rememberAnimatedNavController()
            var selectedTabIndex by remember { mutableStateOf(1) }
            TabRow(
                selectedTabIndex = selectedTabIndex,
                tabs = listOf(
                    stringResource(R.string.discovery),
                    stringResource(R.string.commend),
                    stringResource(R.string.daily)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .zIndex(zIndex = 0.2f),
                containerColor = LocalThemeColors.current.colorPrimary,
                contentColor = LocalThemeColors.current.textColorTertiary,
                selectedContentColor = LocalThemeColors.current.textColorSecondary,
                fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_db1_gb_regular)
                    ?.let { FontFamily(it) },
                dividerColor = Gray,
            ) {
                selectedTabIndex = it
                when (it) {
                    0 -> animatedNavController.navigate(NavRoute.Homepage.DISCOVERY)
                    1 -> animatedNavController.navigate(NavRoute.Homepage.COMMEND)
                    else -> animatedNavController.navigate(NavRoute.Homepage.DAILY)
                }
            }
            AnimatedNavHost(
                navController = animatedNavController,
                startDestination = NavRoute.Homepage.COMMEND,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.dp)
                    .weight(weight = 1f)
            ) {
                composable(
                    route = NavRoute.Homepage.DISCOVERY,
                    enterTransition = { fadeIn() },
                    exitTransition = { fadeOut() },
                ) {
                    DiscoveryScreen()
                }
                composable(
                    route = NavRoute.Homepage.COMMEND,
                    enterTransition = { fadeIn() },
                    exitTransition = { fadeOut() },
                ) {
                    CommendScreen()
                }
                composable(
                    route = NavRoute.Homepage.DAILY,
                    enterTransition = { fadeIn() },
                    exitTransition = { fadeOut() },
                ) {
                    DailyScreen()
                }
            }
        }
    }
}
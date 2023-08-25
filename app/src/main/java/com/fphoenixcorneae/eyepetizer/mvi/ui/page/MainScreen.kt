package com.fphoenixcorneae.eyepetizer.mvi.ui.page

import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fphoenixcorneae.common.ext.exitApp
import com.fphoenixcorneae.eyepetizer.mvi.ui.nav.NavHostController
import com.fphoenixcorneae.eyepetizer.mvi.ui.nav.NavRoute
import com.fphoenixcorneae.eyepetizer.mvi.ui.page.community.CommunityScreen
import com.fphoenixcorneae.eyepetizer.mvi.ui.page.home.HomepageScreen
import com.fphoenixcorneae.eyepetizer.mvi.ui.page.mine.MineScreen
import com.fphoenixcorneae.eyepetizer.mvi.ui.page.notification.NotificationScreen
import com.fphoenixcorneae.eyepetizer.mvi.ui.widget.BottomNavigationBar
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

/**
 * @desc：主页
 * @date：2023/08/09 13:39
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        val animatedNavController = rememberAnimatedNavController()
        var selectedIndex by rememberSaveable { mutableStateOf(0) }
        AnimatedNavHost(
            navController = animatedNavController,
            startDestination = NavRoute.Main.HOMEPAGE,
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 0.dp)
                .weight(weight = 1f)
        ) {
            composable(
                route = NavRoute.Main.HOMEPAGE,
                enterTransition = { fadeIn() },
                exitTransition = { fadeOut() },
            ) {
                HomepageScreen()
            }
            composable(
                route = NavRoute.Main.COMMUNITY,
                enterTransition = { fadeIn() },
                exitTransition = { fadeOut() },
            ) {
                CommunityScreen()
            }
            composable(
                route = NavRoute.Main.NOTIFICATION,
                enterTransition = { fadeIn() },
                exitTransition = { fadeOut() },
            ) {
                NotificationScreen()
            }
            composable(
                route = NavRoute.Main.MINE,
                enterTransition = { fadeIn() },
                exitTransition = { fadeOut() },
            ) {
                MineScreen()
            }
        }
        BottomNavigationBar(selectedIndex = selectedIndex) {
            if (it != 2) {
                selectedIndex = it
            }
            when (it) {
                0 -> animatedNavController.navigate(NavRoute.Main.HOMEPAGE)
                1 -> animatedNavController.navigate(NavRoute.Main.COMMUNITY)
                3 -> animatedNavController.navigate(NavRoute.Main.NOTIFICATION)
                4 -> animatedNavController.navigate(NavRoute.Main.MINE)
                else -> NavHostController.get().navigate(NavRoute.LOGIN)
            }
        }
    }
    BackHandler {
        exitApp()
    }
}
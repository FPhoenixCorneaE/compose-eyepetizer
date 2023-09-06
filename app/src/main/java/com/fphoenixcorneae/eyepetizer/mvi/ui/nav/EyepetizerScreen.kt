package com.fphoenixcorneae.eyepetizer.mvi.ui.nav

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.fphoenixcorneae.eyepetizer.const.Constant
import com.fphoenixcorneae.eyepetizer.mvi.ui.page.MainScreen
import com.fphoenixcorneae.eyepetizer.mvi.ui.page.SplashScreen
import com.fphoenixcorneae.eyepetizer.mvi.ui.page.detail.UgcDetailScreen
import com.fphoenixcorneae.eyepetizer.mvi.ui.page.detail.VideoDetailScreen
import com.fphoenixcorneae.eyepetizer.mvi.ui.page.login.LoginScreen
import com.fphoenixcorneae.eyepetizer.mvi.ui.page.search.SearchScreen
import com.fphoenixcorneae.eyepetizer.mvi.ui.page.setting.SettingScreen
import com.fphoenixcorneae.eyepetizer.mvi.ui.page.web.WebScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import kotlin.math.roundToInt

/**
 * @desc：
 * @date：2023/08/09 13:35
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun EyepetizerScreen() {
    AnimatedNavHost(navController = NavHostController.get(), startDestination = NavRoute.SPLASH) {
        composable(
            route = NavRoute.SPLASH,
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { fadeOut() },
        ) {
            SplashScreen()
        }
        composable(
            route = NavRoute.MAIN,
            enterTransition = { slideInVertically(initialOffsetY = { (it * 0.2f).roundToInt() }) + fadeIn() },
            exitTransition = { slideOutVertically(targetOffsetY = { (it * 0.2f).roundToInt() }) + fadeOut() },
            popEnterTransition = { slideInVertically(initialOffsetY = { (it * 0.2f).roundToInt() }) + fadeIn() },
            popExitTransition = { slideOutVertically(targetOffsetY = { (it * 0.2f).roundToInt() }) + fadeOut() },
        ) {
            MainScreen()
        }
        composable(
            route = NavRoute.LOGIN,
            enterTransition = { slideInVertically(initialOffsetY = { (it * 0.2f).roundToInt() }) + fadeIn() },
            exitTransition = { slideOutVertically(targetOffsetY = { (it * 0.2f).roundToInt() }) + fadeOut() },
            popEnterTransition = { slideInVertically(initialOffsetY = { (it * 0.2f).roundToInt() }) + fadeIn() },
            popExitTransition = { slideOutVertically(targetOffsetY = { (it * 0.2f).roundToInt() }) + fadeOut() },
        ) {
            LoginScreen()
        }
        composable(
            route = "${NavRoute.VIDEO_DETAIL}/{${Constant.Key.VIDEO_ID}}",
            arguments = listOf(navArgument(Constant.Key.VIDEO_ID) {
                type = NavType.StringType
                nullable = true
            }),
            enterTransition = { slideInVertically(initialOffsetY = { (it * 0.2f).roundToInt() }) + fadeIn() },
            exitTransition = { slideOutVertically(targetOffsetY = { (it * 0.2f).roundToInt() }) + fadeOut() },
            popEnterTransition = { slideInVertically(initialOffsetY = { (it * 0.2f).roundToInt() }) + fadeIn() },
            popExitTransition = { slideOutVertically(targetOffsetY = { (it * 0.2f).roundToInt() }) + fadeOut() },
        ) { backStackEntry ->
            VideoDetailScreen(
                videoId = backStackEntry.arguments?.getString(Constant.Key.VIDEO_ID).orEmpty(),
            )
        }
        composable(
            route = "${NavRoute.WEB}?${Constant.Key.WEB_URL}={${Constant.Key.WEB_URL}}",
            arguments = listOf(navArgument(Constant.Key.WEB_URL) { type = NavType.StringType }),
            enterTransition = { slideInVertically(initialOffsetY = { (it * 0.2f).roundToInt() }) + fadeIn() },
            exitTransition = { slideOutVertically(targetOffsetY = { (it * 0.2f).roundToInt() }) + fadeOut() },
            popEnterTransition = { slideInVertically(initialOffsetY = { (it * 0.2f).roundToInt() }) + fadeIn() },
            popExitTransition = { slideOutVertically(targetOffsetY = { (it * 0.2f).roundToInt() }) + fadeOut() },
        ) { backStackEntry ->
            WebScreen(
                url = backStackEntry.arguments?.getString(Constant.Key.WEB_URL)
                    .orEmpty(),
            )
        }
        composable(
            route = NavRoute.SEARCH,
            enterTransition = { slideInVertically(initialOffsetY = { -it }) + fadeIn() },
            exitTransition = { slideOutVertically(targetOffsetY = { -it }) + fadeOut() },
            popEnterTransition = { slideInVertically(initialOffsetY = { -it }) + fadeIn() },
            popExitTransition = { slideOutVertically(targetOffsetY = { -it }) + fadeOut() },
        ) {
            SearchScreen()
        }
        composable(
            route = "${NavRoute.UGC_DETAIL}?${Constant.Key.ID}={${Constant.Key.ID}}",
            arguments = listOf(
                navArgument(Constant.Key.ID) {
                    type = NavType.IntType
                },
            ),
            enterTransition = { slideInVertically(initialOffsetY = { it }) + fadeIn() },
            exitTransition = { slideOutVertically(targetOffsetY = { it }) + fadeOut() },
            popEnterTransition = { slideInVertically(initialOffsetY = { it }) + fadeIn() },
            popExitTransition = { slideOutVertically(targetOffsetY = { it }) + fadeOut() },
        ) { backStackEntry ->
            UgcDetailScreen(
                id = backStackEntry.arguments?.getInt(Constant.Key.ID) ?: 0,
            )
        }
        composable(
            route = NavRoute.SETTING,
            enterTransition = { slideInVertically(initialOffsetY = { (it * 0.2f).roundToInt() }) + fadeIn() },
            exitTransition = { slideOutVertically(targetOffsetY = { (it * 0.2f).roundToInt() }) + fadeOut() },
            popEnterTransition = { slideInVertically(initialOffsetY = { (it * 0.2f).roundToInt() }) + fadeIn() },
            popExitTransition = { slideOutVertically(targetOffsetY = { (it * 0.2f).roundToInt() }) + fadeOut() },
        ) {
            SettingScreen()
        }
    }
}
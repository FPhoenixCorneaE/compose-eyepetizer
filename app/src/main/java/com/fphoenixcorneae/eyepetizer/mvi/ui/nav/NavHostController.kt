package com.fphoenixcorneae.eyepetizer.mvi.ui.nav

import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import com.fphoenixcorneae.eyepetizer.const.Constant

/**
 * @desc：
 * @date：2023/08/09 11:39
 */
object NavHostController {
    private var navHostController = ThreadLocal<NavHostController>()

    fun setNavHostController(navHostController: NavHostController) {
        this.navHostController.set(navHostController)
    }

    fun get() = navHostController.get()!!

    fun navToMain() {
        get().navigate(route = NavRoute.MAIN)
    }

    fun navToLogin() {
        get().navigate(route = NavRoute.LOGIN)
    }

    fun navToVideoDetail(videoId: String?) {
        get().navigate(
            route = "${NavRoute.VIDEO_DETAIL}/${videoId}",
            navOptions = navOptions {
                launchSingleTop = true
            },
        )
    }

    fun navToWeb(url: String?) {
        get().navigate(route = "${NavRoute.WEB}?${Constant.Key.WEB_URL}=${url}")
    }

    fun navToUgcDetail(id: Int) {
        get().navigate(route = "${NavRoute.UGC_DETAIL}?${Constant.Key.ID}=${id}")
    }
}
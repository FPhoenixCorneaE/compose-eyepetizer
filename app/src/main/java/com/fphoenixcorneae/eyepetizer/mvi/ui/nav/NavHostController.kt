package com.fphoenixcorneae.eyepetizer.mvi.ui.nav

import androidx.navigation.NavHostController

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
}
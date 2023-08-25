package com.fphoenixcorneae.eyepetizer.mvi.ui.nav

/**
 * @desc：导航路由
 * @date：2023/08/09 11:47
 */
object NavRoute {
    const val SPLASH = "splash"
    const val MAIN = "main"
    const val LOGIN = "login"
    const val VIDEO_DETAIL = "video_detail"
    const val WEB = "web"
    const val SEARCH = "search"

    /**
     * @desc：主页
     * @date：2023/03/17 10:45
     */
    object Main {
        const val HOMEPAGE = "main_homepage"
        const val COMMUNITY = "main_community"
        const val NOTIFICATION = "main_notification"
        const val MINE = "main_mine"
    }

    /**
     * @desc：首页
     * @date：2023/08/09 15:56
     */
    object Homepage {
        const val DISCOVERY = "homepage_discovery"
        const val COMMEND = "homepage_commend"
        const val DAILY = "homepage_daily"
    }
}
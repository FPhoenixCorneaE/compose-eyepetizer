package com.fphoenixcorneae.eyepetizer.https

/**
 * @desc：
 * @date：2023/08/10 15:42
 */
object EyepetizerApi {
    const val BASE_URL = "http://baobab.kaiyanapp.com/"
    const val PAGE_SIZE = 50

    // 首页
    const val HOMEPAGE_DISCOVERY = "${BASE_URL}api/v7/index/tab/discovery"
    const val HOMEPAGE_COMMEND = "${BASE_URL}api/v5/index/tab/allRec"
    const val HOMEPAGE_DAILY = "${BASE_URL}api/v5/index/tab/feed"

    // 社区
    const val COMMUNITY_COMMEND = "${BASE_URL}api/v7/community/tab/rec"
    const val COMMUNITY_FOLLOW = "${BASE_URL}api/v6/community/tab/follow"

    // 视频详情
    const val VIDEO_DETAIL_COMMENTS = "${BASE_URL}api/v2/replies/video?videoId="
}
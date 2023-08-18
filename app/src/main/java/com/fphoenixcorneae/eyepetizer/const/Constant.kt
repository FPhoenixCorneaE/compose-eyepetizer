package com.fphoenixcorneae.eyepetizer.const

/**
 * @desc：常量
 * @date：2023/08/17 15:33
 */
object Constant {

    /**
     * @desc：Key
     * @date：2023/08/17 15:33
     */
    object Key {
        const val VIDEO_ID = "video_id"
    }

    /**
     * @desc：ActionUrl
     * @date：2023/08/17 15:41
     */
    object ActionUrl {
        const val TAG = "eyepetizer://tag/"
        const val DETAIL = "eyepetizer://detail/"
        const val RANK_LIST = "eyepetizer://ranklist/"
        const val WEB_VIEW = "eyepetizer://webview/?title="
        const val REPLIES_HOT = "eyepetizer://replies/hot?"
        const val TOPIC_DETAIL = "eyepetizer://topic/detail?"
        const val COMMON_TITLE = "eyepetizer://common/?title"
        const val LIGHT_TOPIC_DETAIL = "eyepetizer://lightTopic/detail/"
        const val COMMUNITY_TOPIC_SQUARE = "eyepetizer://community/topicSquare"
        const val HOMEPAGE_NOTIFICATION = "eyepetizer://homepage/notification?tabIndex=0"
        const val HOMEPAGE_SELECTED = "eyepetizer://homepage/selected?tabIndex=2&newTabIndex=-3"
        const val COMMUNITY_TAG_SQUARE_TAB_ZERO = "eyepetizer://community/tagSquare?tabIndex=0"
        const val COMMUNITY_TOPIC_SQUARE_TAB_ZERO = "eyepetizer://community/topicSquare?tabIndex=0"
    }

    /**
     * @desc：
     * @date：2023/08/18 17:20
     */
    object PlayTag {
        const val DISCOVERY = "discovery"
        const val COMMEND = "commend"
        const val DAILY = "daily"
        const val VIDEO_DETAIL = "video_detail"
    }
}
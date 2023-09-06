package com.fphoenixcorneae.eyepetizer.const

/**
 * @desc：常量
 * @date：2023/08/17 15:33
 */
object Constant {
    const val EYEPETIZER_VERSION_NAME = "6.3.1"
    const val EYEPETIZER_VERSION_CODE = 6030012

    /**
     * @desc：Key
     * @date：2023/08/17 15:33
     */
    object Key {
        const val VIDEO_ID = "video_id"
        const val WEB_URL = "web_url"
        const val ID = "id"
        const val COMMUNITY_COMMEND_LIST = "community_commend_list"
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
        const val FOLLOW = "follow"
        const val UGC_DETAIL = "ugc_detail"
    }

    /**
     * @desc：
     * @date：2023/08/23 11:27
     */
    object Url {
        const val AUTHOR_LOGIN = "http://open.eyepetizer.net/#!/login"
        const val AUTHOR_OPEN = "http://open.eyepetizer.net/#!/landing"
        const val FORGET_PASSWORD = "http://open.eyepetizer.net/#!/forget"
        const val LEGAL_NOTICES = "http://www.kaiyanapp.com/legal_notices.html"
        const val USER_REGISTER = "http://open.eyepetizer.net/#!/register"
        const val USER_SERVICE_AGREEMENT = "http://www.eyepetizer.net/agreement.html"
        const val VIDEO_FUNCTION_STATEMENT = "http://www.eyepetizer.net/right.html"
    }

    /**
     * @desc：设置选项
     * @date：2023/09/06 10:40
     */
    object SettingOption {
        const val DAILY_UPDATE_REMINDER = "daily_update_reminder"
        const val WIFI_FOLLOW_AUTO_PLAY = "wifi_follow_auto_play"
        const val TRANSLATE = "translate"
    }
}
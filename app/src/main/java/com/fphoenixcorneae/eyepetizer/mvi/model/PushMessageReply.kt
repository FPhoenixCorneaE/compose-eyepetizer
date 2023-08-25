package com.fphoenixcorneae.eyepetizer.mvi.model

import androidx.annotation.Keep

/**
 * @desc：
 * @date：2023/08/24 17:35
 */
@Keep
data class PushMessageReply(
    val messageList: List<Message>? = null,
    val nextPageUrl: String? = null, // null
    val updateTime: Long = 0 // 1625204370116
) {
    @Keep
    data class Message(
        val actionUrl: String? = null, // eyepetizer://webview/?title=%E5%BC%80%E6%BC%94%E5%94%B1%E4%BC%9A%E5%90%97%EF%BC%9F%E6%9D%A5%E7%BB%84%E4%B8%AA%E4%B9%90%E9%98%9F%E5%90%A7%EF%BC%81&url=eyepetizer%3A%2F%2Ftag%2F16220210108%3FtabIndex%3D1
        val content: String? = null, // 你的乐队=你现在的心情+刚刚吃的东西
        val date: Long = 0, // 1625204370116
        val icon: String? = null, // http://img.wdjimg.com/image/video/418d281e65bf010c38c7b07bdd7b6a94_0_0.png
        val id: Int = 0, // 0
        val ifPush: Boolean = false, // false
        val pushStatus: Int = 0, // 0
        val title: String? = null, // 官方通知
        val uid: Any? = null, // null
        val viewed: Boolean = false // false
    )
}
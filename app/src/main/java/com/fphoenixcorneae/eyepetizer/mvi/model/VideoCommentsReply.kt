package com.fphoenixcorneae.eyepetizer.mvi.model

import androidx.annotation.Keep

/**
 * @desc：视频评论
 * @date：2023/08/17 17:24
 */
@Keep
data class VideoCommentsReply(
    val adExist: Boolean = false, // false
    val count: Int = 0, // 6
    val itemList: List<Item>? = null,
    val nextPageUrl: String? = null, // null
    val total: Int = 0, // 5
) {
    @Keep
    data class Item(
        val adIndex: Int = 0, // -1
        val `data`: Data? = null,
        val id: Int = 0, // 0
        val tag: Any? = null, // null
        val trackingData: Any? = null, // null
        val type: String? = null, // textCard
    ) {
        @Keep
        data class Data(
            val actionUrl: String? = null, // null
            val adTrack: Any? = null, // null
            val cover: Any? = null, // null
            val createTime: Long = 0, // 1690767718000
            val dataType: String? = null, // TextCard
            val follow: Any? = null, // null
            val hot: Boolean = false, // false
            val id: Long = 0, // 1685828040149500004
            val imageUrl: String? = null,
            val likeCount: Int = 0, // 0
            val liked: Boolean = false, // false
            val message: String? = null, // 太酷了
            val parentReply: ParentReply? = null, // null
            val parentReplyId: Int = 0, // 0
            val recommendLevel: String? = null, // not_recommend
            val replyStatus: String? = null, // PUBLISHED
            val rootReplyId: Long = 0, // 1685828040149500004
            val sequence: Int = 0, // 6
            val showConversationButton: Boolean = false, // false
            val showParentReply: Boolean = false, // true
            val sid: String? = null, // 1685828040149500004
            val subTitle: Any? = null, // null
            val text: String? = null, // 最新评论
            val type: String? = null, // header4
            val ugcVideoId: Any? = null, // null
            val ugcVideoUrl: Any? = null, // null
            val user: User? = null,
            val userBlocked: Boolean = false, // false
            val userType: Any? = null, // null
            val videoId: Int = 0, // 319157
            val videoTitle: String? = null, // 抓拍冲浪瞬间：我与大海的浪漫约定
        ) {
            @Keep
            data class ParentReply(
                val id: Long = 0,
                val imageUrl: Any? = null,
                val message: String? = null,
                val replyStatus: String? = null,
                val user: User? = null,
            )

            @Keep
            data class User(
                val actionUrl: String? = null, // eyepetizer://pgc/detail/302647283/?title=%E5%B9%BB%E6%83%B3%E9%87%8C&userType=NORMAL&tabIndex=0
                val area: Any? = null, // null
                val avatar: String? = null, // http://img.kaiyanapp.com/117b3a30bc12535885f189cc64681062.png
                val birthday: Any? = null, // null
                val city: Any? = null, // null
                val country: Any? = null, // null
                val cover: Any? = null, // null
                val description: Any? = null, // null
                val expert: Boolean = false, // false
                val followed: Boolean = false, // false
                val gender: Any? = null, // null
                val ifPgc: Boolean = false, // false
                val job: Any? = null, // null
                val library: String? = null, // BLOCK
                val limitVideoOpen: Boolean = false, // false
                val nickname: String? = null, // 幻想里
                val registDate: Long = 0, // 1486399432000
                val releaseDate: Long = 0, // 1639135667000
                val uid: Int = 0, // 302647283
                val university: Any? = null, // null
                val userType: String? = null, // NORMAL
            )
        }
    }
}
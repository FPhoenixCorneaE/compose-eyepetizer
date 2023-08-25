package com.fphoenixcorneae.eyepetizer.mvi.model

import androidx.annotation.Keep
import kotlinx.parcelize.RawValue

/**
 * @desc：
 * @date：2023/08/23 14:52
 */
@Keep
data class CommunityReply(
    val adExist: Boolean = false, // false
    val count: Int = 0, // 11
    val itemList: List<Item>? = null,
    val nextPageUrl: String? = null, // http://baobab.kaiyanapp.com/api/v7/community/tab/rec?startScore=1661712555000&pageCount=2
    val total: Int = 0, // 0
) {
    @Keep
    data class Item(
        val adIndex: Int = 0, // -1
        val `data`: Data? = null,
        val id: Int = 0, // 0
        val tag: Any? = null, // null
        val trackingData: Any? = null, // null
        val type: String? = null, // horizontalScrollCard
    ) {
        @Keep
        data class Data(
            val actionUrl: String? = null,
            val adTrack: Any? = null, // null
            val autoPlay: Boolean = false,
            val content: Content? = null,
            val count: Int = 0, // 2
            val dataType: String? = null, // ItemCollection
            val footer: Any? = null, // null
            val header: Header? = null,
            val itemList: List<Item>? = null,
            val label: Item.Data.Label? = null,
        ) {
            @Keep
            data class Content(
                val adIndex: Int = 0, // -1
                val `data`: Data? = null,
                val id: Int = 0, // 0
                val tag: Any? = null, // null
                val trackingData: Any? = null, // null
                val type: String? = null, // video
            ) {
                @Keep
                data class Data(
                    val addWatermark: Boolean = false, // true
                    val author: Author? = null,
                    val area: String? = null, // 成都市
                    val checkStatus: String? = null, // CHECKED
                    val city: String? = null, // 成都市
                    val collected: Boolean = false, // false
                    val consumption: Consumption? = null,
                    val cover: Cover? = null,
                    val createTime: Long = 0, // 1661916434000
                    val dataType: String? = null, // UgcVideoBean
                    val description: String? = null, // 时隔六年，他的声音，再次唤醒了我们对平凡生活的向往。
                    val duration: Int = 0, // 99
                    val height: Int = 0, // 1080
                    val id: Int = 0, // 315396
                    val ifMock: Boolean = false, // false
                    val latitude: Double = 0.0, // 30.5690633
                    val library: String? = null, // DEFAULT
                    val longitude: Double = 0.0, // 104.1779758
                    val owner: Owner? = null,
                    val playUrl: String? = null, // http://baobab.kaiyanapp.com/api/v1/playUrl?vid=315396&resourceType=ugc_video&editionType=default&source=aliyun&playUrlType=url_oss&udid=22befd1adc2fa3c14a4d39719f2b007a3
                    val playUrlWatermark: String? = null, // http://baobab.kaiyanapp.com/api/v1/playUrl?vid=315396&resourceType=ugc_video&editionType=default&source=aliyun&playUrlType=play_url_watermark&udid=22befd1adc2fa3c14a4d39719f2b007a3
                    val privateMessageActionUrl: String? = null, // eyepetizer://privateMessage/detail?privateMessageId=-1&anotherUid=303343704&nick=%E4%B9%8C%E8%8B%8F%E5%B0%91%E5%B9%B4
                    val reallyCollected: Boolean = false, // false
                    val recentOnceReply: RecentOnceReply? = null,
                    val releaseTime: Long = 0, // 1661916434000
                    val resourceType: String? = null, // ugc_video
                    val selectedTime: Any? = null, // null
                    val source: String? = null,
                    val status: Int = 0, // 1
                    val tags: List<Tag>? = null,
                    val title: String? = null,
                    val transId: Any? = null, // null
                    val type: String? = null, // COMMON
                    val uid: Int = 0, // 303343704
                    val updateTime: Long = 0, // 1662480155000
                    val url: String? = null, // http://ali-img.kaiyanapp.com/c41ba8977bbc9f172fb9eb0f360e8aa8.png?imageMogr2/quality/100!/format/jpg
                    val urls: List<String>? = null,
                    val urlsWithWatermark: List<String>? = null,
                    val validateResult: String? = null, // DEFAULT
                    val validateStatus: String? = null, // SUBMIT
                    val validateTaskId: String? = null, // vi7TDqQqeQ4YZ4Eh9gipw9so-1wL7Mj
                    val width: Int = 0, // 1920
                ) {
                    @Keep
                    data class Author(
                        val adTrack: @RawValue Any? = null,
                        val approvedNotReadyVideoCount: Int = 0,
                        val description: String? = null,
                        val expert: Boolean = false,
                        val follow: Follow? = null,
                        val icon: String? = null,
                        val id: Int = 0,
                        val ifPgc: Boolean = false,
                        val latestReleaseTime: Long = 0,
                        val link: String? = null,
                        val name: String? = null,
                        val recSort: Int = 0,
                        val shield: Shield? = null,
                        val videoNum: Int = 0,
                    ) {

                        @Keep
                        data class Follow(
                            val followed: Boolean = false,
                            val itemId: Int = 0,
                            val itemType: String? = null,
                        )

                        @Keep
                        data class Shield(
                            val itemId: Int = 0,
                            val itemType: String? = null,
                            val shielded: Boolean = false,
                        )
                    }

                    @Keep
                    data class Consumption(
                        val collectionCount: Int = 0, // 63
                        val realCollectionCount: Int = 0, // 5
                        val replyCount: Int = 0, // 0
                        val shareCount: Int = 0, // 0
                    )

                    @Keep
                    data class Cover(
                        val blurred: Any? = null, // null
                        val detail: String? = null, // http://ali-img.kaiyanapp.com/b91c10d2018fc84b2c7678c23693fc7d.jpeg?imageMogr2/quality/60/format/jpg
                        val feed: String? = null, // http://ali-img.kaiyanapp.com/b91c10d2018fc84b2c7678c23693fc7d.jpeg?imageMogr2/quality/60/format/jpg
                        val homepage: Any? = null, // null
                        val sharing: Any? = null, // null
                    )

                    @Keep
                    data class Owner(
                        val actionUrl: String? = null, // eyepetizer://pgc/detail/303343704/?title=%E4%B9%8C%E8%8B%8F%E5%B0%91%E5%B9%B4&userType=NORMAL&tabIndex=0
                        val area: Any? = null, // null
                        val avatar: String? = null, // http://ali-img.kaiyanapp.com/f7892c6ac9d6e9611cbad07deeef172f.jpeg?imageMogr2/quality/60/format/jpg
                        val birthday: Long = 0, // 820857600000
                        val city: String? = null,
                        val country: String? = null,
                        val cover: String? = null, // http://img.kaiyanapp.com/f9a3fddd3f0941404f4b1d30235c2952.png?imageMogr2/quality/60/format/jpg
                        val description: String? = null, // 陌上花开，缓缓归矣
                        val expert: Boolean = false, // false
                        val followed: Boolean = false, // false
                        val gender: String? = null, // male
                        val ifPgc: Boolean = false, // false
                        val job: String? = null,
                        val library: String? = null, // BLOCK
                        val limitVideoOpen: Boolean = false, // false
                        val nickname: String? = null, // 乌苏少年
                        val registDate: Long = 0, // 1571538610000
                        val releaseDate: Long = 0, // 1679324559000
                        val uid: Int = 0, // 303343704
                        val university: String? = null,
                        val userType: String? = null, // NORMAL
                    )

                    @Keep
                    data class RecentOnceReply(
                        val actionUrl: String? = null, // eyepetizer://pgc/detail/301331974/?title=%E5%8F%AB%E6%88%91%E5%95%A5&userType=NORMAL&tabIndex=0
                        val contentType: Any? = null, // null
                        val dataType: String? = null, // SimpleHotReplyCard
                        val message: String? = null, // 往往这种就是最吸引人的
                        val nickname: String? = null, // 叫我啥
                    )

                    @Keep
                    data class Tag(
                        val actionUrl: String? = null, // eyepetizer://tag/1604/?title=%E8%B7%9F%E7%9D%80%E7%94%B5%E5%BD%B1%E5%8E%BB%E6%97%85%E8%A1%8C
                        val adTrack: Any? = null, // null
                        val bgPicture: String? = null, // http://img.kaiyanapp.com/0c5a833dd888f324ec20458babce2ed0.jpeg?imageMogr2/quality/60/format/jpg
                        val childTagIdList: Any? = null, // null
                        val childTagList: Any? = null, // null
                        val communityIndex: Int = 0, // 2
                        val desc: String? = null, // 打卡荧幕上的同款风景
                        val haveReward: Boolean = false, // false
                        val headerImage: String? = null, // http://img.kaiyanapp.com/0c5a833dd888f324ec20458babce2ed0.jpeg?imageMogr2/quality/60/format/jpg
                        val id: Int = 0, // 1604
                        val ifNewest: Boolean = false, // false
                        val name: String? = null, // 跟着电影去旅行
                        val newestEndTime: Long = 0, // 1587555537000
                        val tagRecType: String? = null, // NORMAL
                    )
                }
            }

            @Keep
            data class Header(
                val actionUrl: String? = null, // eyepetizer://pgc/detail/303343704/?title=%E4%B9%8C%E8%8B%8F%E5%B0%91%E5%B9%B4&userType=NORMAL&tabIndex=0
                val followType: String? = null, // user
                val icon: String? = null, // http://img.kaiyanapp.com/f7892c6ac9d6e9611cbad07deeef172f.jpeg?imageMogr2/quality/60/format/jpg
                val iconType: String? = null, // round
                val id: Int = 0, // 315396
                val issuerName: String? = null, // 乌苏少年
                val labelList: Any? = null, // null
                val showHateVideo: Boolean = false, // false
                val tagId: Int = 0, // 0
                val tagName: Any? = null, // null
                val time: Long = 0, // 1661916434000
                val topShow: Boolean = false, // false
            )

            @Keep
            data class Item(
                val adIndex: Int = 0, // -1
                val `data`: Data? = null,
                val id: Int = 0, // 0
                val tag: Any? = null, // null
                val trackingData: Any? = null, // null
                val type: String? = null, // squareCardOfCommunityContent
            ) {
                @Keep
                data class Data(
                    val actionUrl: String? = null, // eyepetizer://community/tagSquare?tabIndex=0
                    val adTrack: List<Any>? = null,
                    val autoPlay: Boolean = false, // false
                    val bgPicture: String? = null, // http://img.kaiyanapp.com/04eef7e9f3b14a597bd04a8d81a4c8f3.png?imageMogr2/quality/60/format/jpg
                    val dataType: String? = null, // SquareContentCard
                    val description: String? = null,
                    val header: Header? = null,
                    val id: Int = 0, // 2261
                    val image: String? = null, // http://img.kaiyanapp.com/3301ea081957934e8916b514ba4aa02a.jpeg?imageMogr2/quality/60/format/jpg
                    val label: Label? = null,
                    val labelList: List<Label>? = null,
                    val shade: Boolean = false, // false
                    val subTitle: String? = null, // 发布你的作品和日常
                    val title: String? = null, // 主题创作广场
                ) {
                    @Keep
                    data class Header(
                        val actionUrl: Any? = null, // null
                        val cover: Any? = null, // null
                        val description: Any? = null, // null
                        val font: Any? = null, // null
                        val icon: Any? = null, // null
                        val id: Int = 0, // 0
                        val label: Any? = null, // null
                        val labelList: Any? = null, // null
                        val rightText: Any? = null, // null
                        val subTitle: Any? = null, // null
                        val subTitleFont: Any? = null, // null
                        val textAlign: String? = null, // left
                        val title: Any? = null, // null
                    )

                    @Keep
                    data class Label(
                        val actionUrl: String? = null, // null
                        val card: String? = null, // 广告
                        val detail: Any? = null, // null
                        val text: String? = null, // 广告
                    )
                }
            }
        }
    }
}
package com.fphoenixcorneae.eyepetizer.mvi.model

import androidx.annotation.Keep

/**
 * @desc：首页
 * @date：2023/08/10 15:27
 */
@Keep
data class HomepageReply(
    val adExist: Boolean = false, // false
    val count: Int = 0, // 14
    val itemList: List<Item>? = null,
    val nextPageUrl: String? = null, // http://baobab.kaiyanapp.com/api/v5/index/tab/allRec?page=1&isTag=true&adIndex=5
    val total: Int = 0, // 0
) {
    @Keep
    data class Item(
        val adIndex: Int = 0, // -1
        val `data`: Data? = null,
        val id: Int = 0, // 0
        val tag: Any? = null, // null
        val trackingData: Any? = null, // null
        val type: String? = null, // squareCardCollection
    ) {
        @Keep
        data class Data(
            val actionUrl: String? = null, // eyepetizer://tag/44/?title=5%20%E5%88%86%E9%92%9F%E6%96%B0%E7%9F%A5
            val ad: Boolean = false, // false
            val adTrack: List<Any>? = null,
            val author: Author? = null,
            val brandWebsiteInfo: Any? = null, // null
            val campaign: Any? = null, // null
            val category: String? = null, // 运动
            val collected: Boolean = false, // false
            val consumption: Consumption? = null,
            val content: Content? = null,
            val count: Int = 0, // 10
            val cover: Cover? = null,
            val dataType: String? = null, // ItemCollection
            val date: Long = 0, // 1451102400000
            val description: String? = null, // 多亏了现代科技的发展，这些爱作死的人不仅可以将自己的作死实况记录下来，还可以分享给更多的人了。这支混剪视频集合了2015 年那些爱作死、不怕死的大神们的各式精彩花样。From Martin Scoreback
            val descriptionEditor: String? = null, // 多亏了现代科技的发展，这些爱作死的人不仅可以将自己的作死实况记录下来，还可以分享给更多的人了。这支混剪视频集合了2015 年那些爱作死、不怕死的大神们的各式精彩花样。From Martin Scoreback
            val descriptionPgc: String? = null, // 爬上 650 米高的上海中心大厦
            val detail: Detail? = null,
            val duration: Int = 0, // 289
            val favoriteAdTrack: Any? = null, // null
            val follow: Author.Follow? = null, // null
            val footer: Any? = null, // null
            val header: Header? = null,
            val icon: String? = null,
            val iconType: String? = null,
            val id: Int = 0, // 0
            val idx: Int = 0, // 0
            val ifLimitVideo: Boolean = false, // false
            val image: String? = null,
            val itemList: List<Item>? = null,
            val label: Item.Data.Label? = null, // null
            val labelList: List<Item.Data.Label>? = null,
            val lastViewTime: Any? = null, // null
            val library: String? = null, // DAILY
            val playInfo: List<PlayInfo>? = null,
            val playUrl: String? = null, // http://baobab.kaiyanapp.com/api/v1/playUrl?vid=3284&resourceType=video&editionType=default&source=aliyun&playUrlType=url_oss&udid=
            val played: Boolean = false, // false
            val playlists: Any? = null, // null
            val promotion: Any? = null, // null
            val provider: Provider? = null,
            val reallyCollected: Boolean = false, // false
            val recallSource: String? = null,
            val releaseTime: Long = 0, // 1451102400000
            val remark: String? = null, // https://www.xinpianchang.com/a12613208?from=ArticleList
            val resourceType: String? = null, // video
            val rightText: String? = null,
            val searchWeight: Int = 0, // 0
            val shareAdTrack: Any? = null, // null
            val slogan: Any? = null, // null
            val src: Int = 0, // 7
            val subTitle: Any? = null, // null
            val subtitles: List<Any>? = null,
            val tags: List<Tag>? = null,
            val text: String? = null, // 5 分钟新知
            val thumbPlayUrl: Any? = null, // null
            val title: String? = null, // 2015 最作死的那些人
            val titleList: List<String>? = null,
            val titlePgc: String? = null, // 爬上 650 米高的上海中心大厦
            val type: String? = null, // header5
            val videoPosterBean: VideoPosterBean? = null,
            val waterMarks: Any? = null, // null
            val webAdTrack: Any? = null, // null
            val webUrl: WebUrl? = null,
        ) {
            @Keep
            data class Author(
                val adTrack: Any? = null, // null
                val approvedNotReadyVideoCount: Int = 0, // 0
                val description: String? = null, // 开眼运动精选
                val expert: Boolean = false, // false
                val follow: Follow? = null,
                val icon: String? = null, // http://ali-img.kaiyanapp.com/f2449da39a584c982866b0636bd30c58.png?imageMogr2/quality/60/format/jpg
                val id: Int = 0, // 2003
                val ifPgc: Boolean = false, // true
                val latestReleaseTime: Long = 0, // 1691197227000
                val link: String? = null,
                val name: String? = null, // 开眼运动精选
                val recSort: Int = 0, // 0
                val shield: Shield? = null,
                val videoNum: Int = 0, // 663
            ) {
                @Keep
                data class Follow(
                    val followed: Boolean = false, // false
                    val itemId: Int = 0, // 2003
                    val itemType: String? = null, // author
                )

                @Keep
                data class Shield(
                    val itemId: Int = 0, // 2003
                    val itemType: String? = null, // author
                    val shielded: Boolean = false, // false
                )
            }

            @Keep
            data class Consumption(
                val collectionCount: Int = 0, // 46176
                val realCollectionCount: Int = 0, // 6003
                val replyCount: Int = 0, // 695
                val shareCount: Int = 0, // 57879
            )

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
                    val ad: Boolean = false, // false
                    val adTrack: List<Any>? = null,
                    val author: Author? = null,
                    val brandWebsiteInfo: Any? = null, // null
                    val campaign: Any? = null, // null
                    val category: String? = null, // 科技
                    val collected: Boolean = false, // false
                    val consumption: Consumption? = null,
                    val cover: Cover? = null,
                    val dataType: String? = null, // VideoBeanForClient
                    val date: Long = 0, // 1488162878000
                    val description: String? = null, // 通过行星及恒星的大小对比，能深刻体会到：人类，不太阳系真的太渺小了！所以，你在焦虑些什么呢？From MORN1415
                    val descriptionEditor: String? = null, // 通过行星及恒星的大小对比，能深刻体会到：人类，不太阳系真的太渺小了！所以，你在焦虑些什么呢？From MORN1415
                    val descriptionPgc: String? = null, // 该视频展现了行星及恒星的大小对比！整个画面十分震撼，人类，不太阳系真的太渺小了！
                    val duration: Int = 0, // 378
                    val favoriteAdTrack: Any? = null, // null
                    val id: Int = 0, // 14796
                    val idx: Int = 0, // 0
                    val ifLimitVideo: Boolean = false, // false
                    val label: Any? = null, // null
                    val labelList: List<Any>? = null,
                    val lastViewTime: Any? = null, // null
                    val library: String? = null, // DAILY
                    val playInfo: List<PlayInfo>? = null,
                    val playUrl: String? = null, // http://baobab.kaiyanapp.com/api/v1/playUrl?vid=14796&resourceType=video&editionType=default&source=aliyun&playUrlType=url_oss&udid=
                    val played: Boolean = false, // false
                    val playlists: Any? = null, // null
                    val promotion: Any? = null, // null
                    val provider: Provider? = null,
                    val reallyCollected: Boolean = false, // false
                    val recallSource: String? = null,
                    val releaseTime: Long = 0, // 1488162878000
                    val remark: String? = null,
                    val resourceType: String? = null, // video
                    val searchWeight: Int = 0, // 0
                    val shareAdTrack: Any? = null, // null
                    val slogan: String? = null, // 人类不过是尘埃
                    val src: Int = 0, // 7
                    val subtitles: List<Any>? = null,
                    val tags: List<Tag>? = null,
                    val thumbPlayUrl: Any? = null, // null
                    val title: String? = null, // 震撼你的视野：行星及恒星的大小对比
                    val titlePgc: String? = null, // 震撼你的视野新版行星及恒星的大小对比
                    val type: String? = null, // NORMAL
                    val videoPosterBean: VideoPosterBean? = null,
                    val waterMarks: Any? = null, // null
                    val webAdTrack: Any? = null, // null
                    val webUrl: WebUrl? = null,
                ) {
                    @Keep
                    data class Author(
                        val adTrack: Any? = null, // null
                        val approvedNotReadyVideoCount: Int = 0, // 0
                        val description: String? = null, // 跟随天文君一起旅行宇宙，探索未知的世界！公众号ID：tianwenyizu
                        val expert: Boolean = false, // false
                        val follow: Follow? = null,
                        val icon: String? = null, // http://ali-img.kaiyanapp.com/e0ad67901e14cc87c66558869bf6fbf4.png?imageMogr2/quality/60/format/jpg
                        val id: Int = 0, // 400
                        val ifPgc: Boolean = false, // true
                        val latestReleaseTime: Long = 0, // 1649682821000
                        val link: String? = null,
                        val name: String? = null, // 天文一族
                        val recSort: Int = 0, // 0
                        val shield: Shield? = null,
                        val videoNum: Int = 0, // 80
                    ) {
                        @Keep
                        data class Follow(
                            val followed: Boolean = false, // false
                            val itemId: Int = 0, // 400
                            val itemType: String? = null, // author
                        )

                        @Keep
                        data class Shield(
                            val itemId: Int = 0, // 400
                            val itemType: String? = null, // author
                            val shielded: Boolean = false, // false
                        )
                    }

                    @Keep
                    data class Consumption(
                        val collectionCount: Int = 0, // 75093
                        val realCollectionCount: Int = 0, // 11663
                        val replyCount: Int = 0, // 1271
                        val shareCount: Int = 0, // 104719
                    )

                    @Keep
                    data class Cover(
                        val blurred: String? = null, // http://ali-img.kaiyanapp.com/cee6447d64899aeba600fc91a2081d7a.jpeg?imageMogr2/quality/60/format/jpg
                        val detail: String? = null, // http://ali-img.kaiyanapp.com/3586a5420e4803557e221d5ebaeb8d04.png?imageMogr2/quality/60/format/jpg
                        val feed: String? = null, // http://ali-img.kaiyanapp.com/3586a5420e4803557e221d5ebaeb8d04.png?imageMogr2/quality/60/format/jpg
                        val homepage: String? = null, // http://img.kaiyanapp.com/30107a18b242f323476f88febe7eacbd.jpeg?imageView2/1/w/720/h/560/format/jpg/q/75|watermark/1/image/aHR0cDovL2ltZy5rYWl5YW5hcHAuY29tL2JsYWNrXzMwLnBuZw==/dissolve/100/gravity/Center/dx/0/dy/0|imageslim
                        val sharing: Any? = null, // null
                    )

                    @Keep
                    data class PlayInfo(
                        val height: Int = 0, // 480
                        val name: String? = null, // 标清
                        val type: String? = null, // normal
                        val url: String? = null, // http://baobab.kaiyanapp.com/api/v1/playUrl?vid=14796&resourceType=video&editionType=normal&source=aliyun&playUrlType=url_oss&udid=
                        val urlList: List<Url>? = null,
                        val width: Int = 0, // 854
                    ) {
                        @Keep
                        data class Url(
                            val name: String? = null, // aliyun
                            val size: Int = 0, // 53442646
                            val url: String? = null, // http://baobab.kaiyanapp.com/api/v1/playUrl?vid=14796&resourceType=video&editionType=normal&source=aliyun&playUrlType=url_oss&udid=
                        )
                    }

                    @Keep
                    data class Provider(
                        val alias: String? = null, // PGC
                        val icon: String? = null,
                        val name: String? = null, // PGC
                    )

                    @Keep
                    data class Tag(
                        val actionUrl: String? = null, // eyepetizer://tag/44/?title=5%20%E5%88%86%E9%92%9F%E6%96%B0%E7%9F%A5
                        val adTrack: Any? = null, // null
                        val bgPicture: String? = null, // http://img.kaiyanapp.com/f2e7359e81e217782f32cc3d482b3284.jpeg?imageMogr2/quality/60/format/jpg
                        val childTagIdList: Any? = null, // null
                        val childTagList: Any? = null, // null
                        val communityIndex: Int = 0, // 0
                        val desc: String? = null, // 大千世界，总有你不知道的
                        val haveReward: Boolean = false, // false
                        val headerImage: String? = null, // http://img.kaiyanapp.com/f2e7359e81e217782f32cc3d482b3284.jpeg?imageMogr2/quality/60/format/jpg
                        val id: Int = 0, // 44
                        val ifNewest: Boolean = false, // false
                        val name: String? = null, // 5 分钟新知
                        val newestEndTime: Any? = null, // null
                        val tagRecType: String? = null, // IMPORTANT
                    )

                    @Keep
                    data class VideoPosterBean(
                        val fileSizeStr: String? = null, // 3.01MB
                        val scale: Double = 0.0, // 0.725
                        val url: String? = null, // http://eyepetizer-videos.oss-cn-beijing.aliyuncs.com/video_poster_share/ea137fbaef4800c8b5b1b9b35ba56f93.mp4
                    )

                    @Keep
                    data class WebUrl(
                        val forWeibo: String? = null, // http://wandou.im/3m62x5
                        val raw: String? = null, // http://www.eyepetizer.net/detail.html?vid=14796
                    )
                }
            }

            @Keep
            data class Cover(
                val blurred: String? = null, // http://ali-img.kaiyanapp.com/10966a7fa0eee98759f050c7f5448628.jpeg?imageMogr2/quality/100
                val detail: String? = null, // http://ali-img.kaiyanapp.com/522d2216b5f8aca639c296d71ac78753.jpeg?imageMogr2/quality/100
                val feed: String? = null, // http://ali-img.kaiyanapp.com/522d2216b5f8aca639c296d71ac78753.jpeg?imageMogr2/quality/100
                val homepage: String? = null, // http://img.kaiyanapp.com/f061ac06b3f8e6d913c170d53e1e9303.jpeg?imageView2/1/w/720/h/560/format/jpg/q/75|watermark/1/image/aHR0cDovL2ltZy5rYWl5YW5hcHAuY29tL2JsYWNrXzMwLnBuZw==/dissolve/100/gravity/Center/dx/0/dy/0|imageslim
                val sharing: Any? = null, // null
            )

            @Keep
            data class Detail(
                val actionUrl: String? = null,
                val adTrack: List<Any>? = null,
                val adaptiveImageUrls: String? = null,
                val adaptiveUrls: String? = null,
                val canSkip: Boolean = false,
                val categoryId: Int = 0,
                val countdown: Boolean = false,
                val cycleCount: Int = 0,
                val description: String? = null,
                val displayCount: Int = 0,
                val displayTimeDuration: Int = 0,
                val icon: String? = null,
                val id: Long = 0,
                val ifLinkage: Boolean = false,
                val imageUrl: String? = null,
                val iosActionUrl: String? = null,
                val linkageAdId: Int = 0,
                val loadingMode: Int = 0,
                val openSound: Boolean = false,
                val position: Int = 0,
                val showActionButton: Boolean = false,
                val showImage: Boolean = false,
                val showImageTime: Int = 0,
                val timeBeforeSkip: Int = 0,
                val title: String? = null,
                val url: String? = null,
                val videoAdType: String? = null,
                val videoType: String? = null,
            )

            @Keep
            data class Header(
                val actionUrl: String? = null, // eyepetizer://feed?tabIndex=2
                val cover: Any? = null, // null
                val description: String? = null, // #科技 / 收录于 每日编辑精选
                val font: String? = null, // bigBold
                val icon: String? = null, // http://ali-img.kaiyanapp.com/e0ad67901e14cc87c66558869bf6fbf4.png?imageMogr2/quality/60/format/jpg
                val iconType: String? = null, // round
                val id: Int = 0, // 5
                val label: Any? = null, // null
                val labelList: Any? = null, // null
                val rightText: String? = null, // 查看往期
                val showHateVideo: Boolean = false, // false
                val subTitle: String? = null, // THURSDAY, AUGUST 10
                val subTitleFont: String? = null, // lobster
                val textAlign: String? = null, // left
                val time: Long = 0, // 1488162878000
                val title: String? = null, // 开眼编辑精选
            )

            @Keep
            data class Item(
                val adIndex: Int = 0, // -1
                val `data`: Data? = null,
                val id: Int = 0, // 0
                val tag: Any? = null, // null
                val trackingData: Any? = null, // null
                val type: String? = null, // followCard
            ) {
                @Keep
                data class Data(
                    val actionUrl: String? = null,
                    val adTrack: List<Any>? = null,
                    val autoPlay: Boolean = false,
                    val content: Content? = null,
                    val dataType: String? = null, // FollowCard
                    val description: String? = null,
                    val header: Header? = null,
                    val id: Int = 0,
                    val image: String? = null,
                    val label: Label? = null,
                    val labelList: List<Any>? = null,
                    val nickname: String? = null,
                    val resourceType: String? = null,
                    val shade: Boolean = false,
                    val title: String? = null,
                    val url: String? = null,
                    val urls: List<String>? = null,
                    val userCover: String? = null,
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
                            val ad: Boolean = false, // false
                            val adTrack: List<Any>? = null,
                            val author: Author? = null,
                            val brandWebsiteInfo: Any? = null, // null
                            val campaign: Any? = null, // null
                            val category: String? = null, // 旅行
                            val collected: Boolean = false, // false
                            val consumption: Consumption? = null,
                            val cover: Cover? = null,
                            val dataType: String? = null, // VideoBeanForClient
                            val date: Long = 0, // 1691629200000
                            val description: String? = null, // 时隔三年，日本的花火大会终于回归了！每年夏天日本各地都会举行花火大会，蒲郡是一个海滨小镇，能够燃放各种水上烟花，特别是今年的烟花大会，发射了 3 颗太平洋沿岸最大的 3 英寸烟花，重约 200 公斤。在这次绚烂的蒲郡花火大会上，夜空被染上了美丽的颜色，五颜六色的焰火十分绚烂夺目，吸引着无数当地人民和游客，夏日、海边、烟花是这夜最浪漫的代名词吧！ From Armadas
                            val descriptionEditor: String? = null, // 时隔三年，日本的花火大会终于回归了！每年夏天日本各地都会举行花火大会，蒲郡是一个海滨小镇，能够燃放各种水上烟花，特别是今年的烟花大会，发射了 3 颗太平洋沿岸最大的 3 英寸烟花，重约 200 公斤。在这次绚烂的蒲郡花火大会上，夜空被染上了美丽的颜色，五颜六色的焰火十分绚烂夺目，吸引着无数当地人民和游客，夏日、海边、烟花是这夜最浪漫的代名词吧！ From Armadas
                            val descriptionPgc: String? = null,
                            val duration: Int = 0, // 998
                            val favoriteAdTrack: Any? = null, // null
                            val id: Int = 0, // 319391
                            val idx: Int = 0, // 0
                            val ifLimitVideo: Boolean = false, // false
                            val label: Any? = null, // null
                            val labelList: List<Any>? = null,
                            val lastViewTime: Any? = null, // null
                            val library: String? = null, // DAILY
                            val playInfo: List<PlayInfo>? = null,
                            val playUrl: String? = null, // http://baobab.kaiyanapp.com/api/v1/playUrl?vid=319391&resourceType=video&editionType=default&source=aliyun&playUrlType=url_oss&udid=
                            val played: Boolean = false, // false
                            val playlists: Any? = null, // null
                            val promotion: Any? = null, // null
                            val provider: Provider? = null,
                            val reallyCollected: Boolean = false, // false
                            val recallSource: Any? = null, // null
                            val releaseTime: Long = 0, // 1691629206000
                            val remark: String? = null, // 达美乐飞檐走壁送外卖：这谁不心动？
                            val resourceType: String? = null, // video
                            val searchWeight: Int = 0, // 0
                            val shareAdTrack: Any? = null, // null
                            val slogan: Any? = null, // null
                            val src: Any? = null, // null
                            val subtitles: List<Any>? = null,
                            val tags: List<Tag>? = null,
                            val thumbPlayUrl: Any? = null, // null
                            val title: String? = null, // 日本花火大会，今夏最绚烂的风物诗
                            val titlePgc: String? = null, // 旅行
                            val type: String? = null, // NORMAL
                            val videoPosterBean: Any? = null, // null
                            val waterMarks: Any? = null, // null
                            val webAdTrack: Any? = null, // null
                            val webUrl: WebUrl? = null,
                        ) {
                            @Keep
                            data class Author(
                                val adTrack: Any? = null, // null
                                val approvedNotReadyVideoCount: Int = 0, // 0
                                val description: String? = null, // 在这个世界上的很多地方都有着美丽的景色，我们用相机用镜头记录下这一切。
                                val expert: Boolean = false, // false
                                val follow: Follow? = null,
                                val icon: String? = null, // http://ali-img.kaiyanapp.com/385d659e77af15fa4be37fe638c75917.jpeg?imageMogr2/quality/60/format/jpg
                                val id: Int = 0, // 1313
                                val ifPgc: Boolean = false, // true
                                val latestReleaseTime: Long = 0, // 1691629206000
                                val link: String? = null,
                                val name: String? = null, // 全球旅行视频精选
                                val recSort: Int = 0, // 0
                                val shield: Shield? = null,
                                val videoNum: Int = 0, // 2806
                            ) {
                                @Keep
                                data class Follow(
                                    val followed: Boolean = false, // false
                                    val itemId: Int = 0, // 1313
                                    val itemType: String? = null, // author
                                )

                                @Keep
                                data class Shield(
                                    val itemId: Int = 0, // 1313
                                    val itemType: String? = null, // author
                                    val shielded: Boolean = false, // false
                                )
                            }

                            @Keep
                            data class Consumption(
                                val collectionCount: Int = 0, // 136
                                val realCollectionCount: Int = 0, // 129
                                val replyCount: Int = 0, // 5
                                val shareCount: Int = 0, // 65
                            )

                            @Keep
                            data class Cover(
                                val blurred: String? = null, // http://ali-img.kaiyanapp.com/533fe1d6de2e0a9ce916be3436166812.jpeg?imageMogr2/quality/60/format/jpg
                                val detail: String? = null, // http://ali-img.kaiyanapp.com/3fd80dc181b94badeed80da105a93491.jpeg?imageMogr2/quality/60/format/jpg
                                val feed: String? = null, // http://ali-img.kaiyanapp.com/3fd80dc181b94badeed80da105a93491.jpeg?imageMogr2/quality/60/format/jpg
                                val homepage: String? = null, // http://img.kaiyanapp.com/3fd80dc181b94badeed80da105a93491.jpeg?imageView2/1/w/720/h/560/format/jpg/q/75|watermark/1/image/aHR0cDovL2ltZy5rYWl5YW5hcHAuY29tL2JsYWNrXzMwLnBuZw==/dissolve/100/gravity/Center/dx/0/dy/0|imageslim
                                val sharing: Any? = null, // null
                            )

                            @Keep
                            data class PlayInfo(
                                val height: Int = 0, // 720
                                val name: String? = null, // 高清
                                val type: String? = null, // high
                                val url: String? = null, // http://baobab.kaiyanapp.com/api/v1/playUrl?vid=319389&resourceType=video&editionType=high&source=aliyun&playUrlType=url_oss&udid=
                                val urlList: List<Url>? = null,
                                val width: Int = 0, // 1280
                            ) {
                                @Keep
                                data class Url(
                                    val name: String? = null, // aliyun
                                    val size: Int = 0, // 5466629
                                    val url: String? = null, // http://baobab.kaiyanapp.com/api/v1/playUrl?vid=319389&resourceType=video&editionType=high&source=aliyun&playUrlType=url_oss&udid=
                                )
                            }

                            @Keep
                            data class Provider(
                                val alias: String? = null, // youtube
                                val icon: String? = null, // http://ali-img.kaiyanapp.com/fa20228bc5b921e837156923a58713f6.png
                                val name: String? = null, // YouTube
                            )

                            @Keep
                            data class Tag(
                                val actionUrl: String? = null, // eyepetizer://tag/10/?title=%E8%B7%9F%E7%9D%80%E5%BC%80%E7%9C%BC%E7%9C%8B%E4%B8%96%E7%95%8C
                                val adTrack: Any? = null, // null
                                val bgPicture: String? = null, // http://img.kaiyanapp.com/7ea328a893aa1f092b9328a53494a267.png?imageMogr2/quality/60/format/jpg
                                val childTagIdList: Any? = null, // null
                                val childTagList: Any? = null, // null
                                val communityIndex: Int = 0, // 14
                                val desc: String? = null, // 去你想去的地方，发现世界的美
                                val haveReward: Boolean = false, // false
                                val headerImage: String? = null, // http://img.kaiyanapp.com/50dab5468ecd2dbe5eb99dab5d608a0a.jpeg?imageMogr2/quality/60/format/jpg
                                val id: Int = 0, // 10
                                val ifNewest: Boolean = false, // false
                                val name: String? = null, // 跟着开眼看世界
                                val newestEndTime: Any? = null, // null
                                val tagRecType: String? = null, // IMPORTANT
                            )

                            @Keep
                            data class WebUrl(
                                val forWeibo: String? = null, // https://m.eyepetizer.net/u1/video-detail?video_id=319391&resource_type=video&utm_campaign=routine&utm_medium=share&utm_source=weibo&uid=0
                                val raw: String? = null, // http://www.eyepetizer.net/detail.html?vid=319391
                            )
                        }
                    }

                    @Keep
                    data class Header(
                        val actionUrl: String? = null, // eyepetizer://pgc/detail/1313/?title=%E5%85%A8%E7%90%83%E6%97%85%E8%A1%8C%E8%A7%86%E9%A2%91%E7%B2%BE%E9%80%89&userType=PGC&tabIndex=1
                        val cover: Any? = null, // null
                        val description: Any? = null, // null
                        val font: Any? = null, // null
                        val icon: String? = null, // http://ali-img.kaiyanapp.com/385d659e77af15fa4be37fe638c75917.jpeg?imageMogr2/quality/60/format/jpg
                        val iconType: String? = null, // round
                        val id: Int = 0, // 319391
                        val label: Any? = null, // null
                        val labelList: Any? = null, // null
                        val rightText: Any? = null, // null
                        val showHateVideo: Boolean = false, // false
                        val subTitle: Any? = null, // null
                        val subTitleFont: Any? = null, // null
                        val textAlign: String? = null, // left
                        val time: Long = 0, // 1691629206000
                        val title: String? = null, // 日本花火大会，今夏最绚烂的风物诗
                    )

                    @Keep
                    data class Label(
                        val actionUrl: String? = null,
                        val text: String? = null,
                        val card: String? = null,
                        val detail: Any? = null,
                    )
                }
            }

            @Keep
            data class PlayInfo(
                val height: Int = 0, // 720
                val name: String? = null, // 高清
                val type: String? = null, // high
                val url: String? = null, // http://baobab.kaiyanapp.com/api/v1/playUrl?vid=3284&resourceType=video&editionType=high&source=aliyun&playUrlType=url_oss&udid=
                val urlList: List<Url>? = null,
                val width: Int = 0, // 1280
            ) {
                @Keep
                data class Url(
                    val name: String? = null, // aliyun
                    val size: Int = 0, // 77262598
                    val url: String? = null, // http://baobab.kaiyanapp.com/api/v1/playUrl?vid=3284&resourceType=video&editionType=high&source=aliyun&playUrlType=url_oss&udid=
                )
            }

            @Keep
            data class Provider(
                val alias: String? = null, // youtube
                val icon: String? = null, // http://ali-img.kaiyanapp.com/fa20228bc5b921e837156923a58713f6.png
                val name: String? = null, // YouTube
            )

            @Keep
            data class Tag(
                val actionUrl: String? = null, // eyepetizer://tag/44/?title=5%20%E5%88%86%E9%92%9F%E6%96%B0%E7%9F%A5
                val adTrack: Any? = null, // null
                val bgPicture: String? = null, // http://img.kaiyanapp.com/f2e7359e81e217782f32cc3d482b3284.jpeg?imageMogr2/quality/60/format/jpg
                val childTagIdList: Any? = null, // null
                val childTagList: Any? = null, // null
                val communityIndex: Int = 0, // 0
                val desc: String? = null, // 大千世界，总有你不知道的
                val haveReward: Boolean = false, // false
                val headerImage: String? = null, // http://img.kaiyanapp.com/f2e7359e81e217782f32cc3d482b3284.jpeg?imageMogr2/quality/60/format/jpg
                val id: Int = 0, // 44
                val ifNewest: Boolean = false, // false
                val name: String? = null, // 5 分钟新知
                val newestEndTime: Any? = null, // null
                val tagRecType: String? = null, // IMPORTANT
            )

            @Keep
            data class VideoPosterBean(
                val fileSizeStr: String? = null, // 6.04MB
                val scale: Double = 0.0, // 0.725
                val url: String? = null, // http://eyepetizer-videos.oss-cn-beijing.aliyuncs.com/video_poster_share/887ab12377e87ee5a192bd133405d29b.mp4
            )

            @Keep
            data class WebUrl(
                val forWeibo: String? = null, // http://wandou.im/vpibb
                val raw: String? = null, // http://www.eyepetizer.net/detail.html?vid=3284
            )
        }
    }
}
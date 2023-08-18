package com.fphoenixcorneae.eyepetizer.mvi.model

import androidx.annotation.Keep

/**
 * @desc：视频详情
 * @date：2023/08/17 17:09
 */
@Keep
data class VideoDetailReply(
    val ad: Boolean = false, // false
    val adTrack: List<Any?>? = null,
    val author: Author? = null,
    val brandWebsiteInfo: Any? = null, // null
    val campaign: Any? = null, // null
    val category: String? = null, // 旅行
    val collected: Boolean = false, // false
    val consumption: Consumption? = null,
    val cover: Cover? = null,
    val dataType: String? = null, // VideoBeanForClient
    val date: Long = 0, // 1691888410000
    val description: String? = null, // 每当谈起迪拜，第一印象会是奢侈得流油，在经济繁荣的同时，迪拜也有它的另一副面孔。跟随创作者的镜头来到这座辉煌的「未来之城」迪拜，饱览一场城市与大自然的盛宴。迪拜是阿拉伯联合酋长国的瑰宝，也是中东地区的经济和金融中心，是一座散发着富裕、宏伟和创新气息的城市，从高耸的摩天大楼和繁华度假村到原始海滩和文化宝藏，这座城市总能给人留下深刻印象，现代奇迹与古老传统在这里交相辉映。 From Dimitri Fafutis
    val descriptionEditor: String? = null, // 每当谈起迪拜，第一印象会是奢侈得流油，在经济繁荣的同时，迪拜也有它的另一副面孔。跟随创作者的镜头来到这座辉煌的「未来之城」迪拜，饱览一场城市与大自然的盛宴。迪拜是阿拉伯联合酋长国的瑰宝，也是中东地区的经济和金融中心，是一座散发着富裕、宏伟和创新气息的城市，从高耸的摩天大楼和繁华度假村到原始海滩和文化宝藏，这座城市总能给人留下深刻印象，现代奇迹与古老传统在这里交相辉映。 From Dimitri Fafutis
    val descriptionPgc: String? = null,
    val duration: Int = 0, // 283
    val favoriteAdTrack: Any? = null, // null
    val id: Int = 0, // 319343
    val idx: Int = 0, // 0
    val ifLimitVideo: Boolean = false, // false
    val label: Any? = null, // null
    val labelList: List<Any?>? = null,
    val lastViewTime: Any? = null, // null
    val library: String? = null, // DAILY
    val playInfo: List<PlayInfo?>? = null,
    val playUrl: String? = null, // http://baobab.kaiyanapp.com/api/v1/playUrl?vid=319343&resourceType=video&editionType=default&source=aliyun&playUrlType=url_oss&udid=22befd1adc2fa3c14a4d39719f2b007a3
    val played: Boolean = false, // false
    val playlists: Any? = null, // null
    val promotion: Any? = null, // null
    val provider: Provider? = null,
    val reallyCollected: Boolean = false, // false
    val recallSource: Any? = null, // null
    val releaseTime: Long = 0, // 1691888410000
    val remark: String? = null,
    val resourceType: String? = null, // video
    val searchWeight: Int = 0, // 0
    val shareAdTrack: Any? = null, // null
    val slogan: Any? = null, // null
    val src: Any? = null, // null
    val subtitles: List<Any?>? = null,
    val tags: List<Tag?>? = null,
    val thumbPlayUrl: Any? = null, // null
    val title: String? = null, // 迪拜：时光驻留，未来之城
    val titlePgc: String? = null, // 旅行f cc
    val type: String? = null, // NORMAL
    val videoPosterBean: Any? = null, // null
    val waterMarks: Any? = null, // null
    val webAdTrack: Any? = null, // null
    val webUrl: WebUrl? = null
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
        val latestReleaseTime: Long = 0, // 1692234043000
        val link: String? = null,
        val name: String? = null, // 全球旅行视频精选
        val recSort: Int = 0, // 0
        val shield: Shield? = null,
        val videoNum: Int = 0 // 2813
    ) {
        @Keep
        data class Follow(
            val followed: Boolean = false, // false
            val itemId: Int = 0, // 1313
            val itemType: String? = null // author
        )

        @Keep
        data class Shield(
            val itemId: Int = 0, // 1313
            val itemType: String? = null, // author
            val shielded: Boolean = false // false
        )
    }

    @Keep
    data class Consumption(
        val collectionCount: Int = 0, // 126
        val realCollectionCount: Int = 0, // 113
        val replyCount: Int = 0, // 5
        val shareCount: Int = 0 // 59
    )

    @Keep
    data class Cover(
        val blurred: String? = null, // http://ali-img.kaiyanapp.com/423ab3561cc397d2b3b8ed7b31d817a5.jpeg?imageMogr2/quality/60/format/jpg
        val detail: String? = null, // http://ali-img.kaiyanapp.com/bca429479294f7d491c9ffa2565e4598.jpeg?imageMogr2/quality/60/format/jpg
        val feed: String? = null, // http://ali-img.kaiyanapp.com/bca429479294f7d491c9ffa2565e4598.jpeg?imageMogr2/quality/60/format/jpg
        val homepage: String? = null, // http://img.kaiyanapp.com/bca429479294f7d491c9ffa2565e4598.jpeg?imageView2/1/w/720/h/560/format/jpg/q/75|watermark/1/image/aHR0cDovL2ltZy5rYWl5YW5hcHAuY29tL2JsYWNrXzMwLnBuZw==/dissolve/100/gravity/Center/dx/0/dy/0|imageslim
        val sharing: Any? = null // null
    )

    @Keep
    data class PlayInfo(
        val height: Int = 0, // 720
        val name: String? = null, // 高清
        val type: String? = null, // high
        val url: String? = null, // http://baobab.kaiyanapp.com/api/v1/playUrl?vid=319343&resourceType=video&editionType=high&source=aliyun&playUrlType=url_oss&udid=22befd1adc2fa3c14a4d39719f2b007a3
        val urlList: List<Url?>? = null,
        val width: Int = 0 // 1280
    ) {
        @Keep
        data class Url(
            val name: String? = null, // aliyun
            val size: Int = 0, // 45026701
            val url: String? = null // http://baobab.kaiyanapp.com/api/v1/playUrl?vid=319343&resourceType=video&editionType=high&source=aliyun&playUrlType=url_oss&udid=22befd1adc2fa3c14a4d39719f2b007a3
        )
    }

    @Keep
    data class Provider(
        val alias: String? = null, // youtube
        val icon: String? = null, // http://ali-img.kaiyanapp.com/fa20228bc5b921e837156923a58713f6.png
        val name: String? = null // YouTube
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
        val tagRecType: String? = null // IMPORTANT
    )

    @Keep
    data class WebUrl(
        val forWeibo: String? = null, // https://m.eyepetizer.net/u1/video-detail?video_id=319343&resource_type=video&utm_campaign=routine&utm_medium=share&utm_source=weibo&uid=0
        val raw: String? = null // http://www.eyepetizer.net/detail.html?vid=319343
    )
}
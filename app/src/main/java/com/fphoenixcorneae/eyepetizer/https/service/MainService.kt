package com.fphoenixcorneae.eyepetizer.https.service

import com.fphoenixcorneae.eyepetizer.mvi.model.CommunityReply
import com.fphoenixcorneae.eyepetizer.mvi.model.HomepageReply
import com.fphoenixcorneae.eyepetizer.mvi.model.PushMessageReply
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * @desc：
 * @date：2023/08/10 15:18
 */
interface MainService {

    /**
     * 首页-发现/推荐/日报列表
     */
    @GET
    suspend fun getHomepageData(@Url url: String): HomepageReply?

    /**
     * 社区-推荐/关注列表
     */
    @GET
    suspend fun getCommunityData(@Url url: String): CommunityReply?

    /**
     * 通知-推送列表
     */
    @GET
    suspend fun getNotificationPushMessage(@Url url: String): PushMessageReply?

    /**
     * 搜索-热搜关键词
     */
    @GET("api/v3/queries/hot")
    suspend fun getHotSearchKeys(): List<String>?
}
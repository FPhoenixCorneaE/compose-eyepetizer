package com.fphoenixcorneae.eyepetizer.https.service

import com.fphoenixcorneae.eyepetizer.mvi.model.HomepageReply
import com.fphoenixcorneae.eyepetizer.mvi.model.VideoCommentsReply
import com.fphoenixcorneae.eyepetizer.mvi.model.VideoDetailReply
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * @desc：
 * @date：2023/08/10 15:18
 */
interface VideoService {

    /**
     * 视频详情-视频信息
     */
    @GET("api/v2/video/{id}")
    suspend fun getVideoDetail(@Path("id") videoId: String): VideoDetailReply?

    /**
     * 视频详情-推荐列表
     */
    @GET("api/v4/video/related")
    suspend fun getVideoRelated(@Query("id") videoId: String): HomepageReply?

    /**
     * 视频详情-评论列表
     */
    @GET
    suspend fun getVideoComments(@Url url: String): VideoCommentsReply?
}
package com.fphoenixcorneae.eyepetizer.https.service

import com.fphoenixcorneae.eyepetizer.mvi.model.HomepageReply
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
}
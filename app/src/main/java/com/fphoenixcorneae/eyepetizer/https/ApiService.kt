package com.fphoenixcorneae.eyepetizer.https

import com.fphoenixcorneae.compose.https.RetrofitFactory
import com.fphoenixcorneae.eyepetizer.https.interceptor.CommonParamsInterceptor
import com.fphoenixcorneae.eyepetizer.https.interceptor.HeaderInterceptor
import com.fphoenixcorneae.eyepetizer.https.service.MainService
import com.fphoenixcorneae.eyepetizer.https.service.VideoService

private val retrofitFactory by lazy {
    RetrofitFactory.Builder().apply {
        baseUrl = EyepetizerApi.BASE_URL
        headerInterceptor = HeaderInterceptor()
        commonParamsInterceptor = CommonParamsInterceptor()
    }.build()
}

/** 双重校验锁式-懒加载单例 封装 ApiService 方便直接快速调用简单的接口 */
val mainService: MainService by lazy {
    retrofitFactory.create(MainService::class.java)
}

val videoService: VideoService by lazy {
    retrofitFactory.create(VideoService::class.java)
}
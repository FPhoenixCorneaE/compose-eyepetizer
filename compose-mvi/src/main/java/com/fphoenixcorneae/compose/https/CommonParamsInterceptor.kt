package com.fphoenixcorneae.compose.https

import com.fphoenixcorneae.compose.ext.deviceModel
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @desc：
 * @date：2023/08/08 15:40
 */
class CommonParamsInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url
        val url = originalHttpUrl.newBuilder().apply {
            addQueryParameter("deviceModel", deviceModel)
        }.build()
        val request = originalRequest.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}
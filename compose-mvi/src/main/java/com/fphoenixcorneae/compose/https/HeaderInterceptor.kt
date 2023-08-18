package com.fphoenixcorneae.compose.https

import okhttp3.Interceptor
import okhttp3.Response

/**
 * @desc：
 * @date：2023/03/22 10:39
 */
class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val request = originalRequest.newBuilder().apply {
            header("Content-Type", "application/json")
        }.build()
        return chain.proceed(request)
    }
}
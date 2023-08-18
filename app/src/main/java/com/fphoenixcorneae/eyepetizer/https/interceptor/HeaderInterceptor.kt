package com.fphoenixcorneae.eyepetizer.https.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.util.Date

/**
 * @desc：请求头拦截器
 * @date：2023/08/10 15:48
 */
class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val request = originalRequest.newBuilder().apply {
            header("model", "Android")
            header("If-Modified-Since", "${Date()}")
            header("User-Agent", System.getProperty("http.agent") ?: "unknown")
        }.build()
        return chain.proceed(request)
    }
}
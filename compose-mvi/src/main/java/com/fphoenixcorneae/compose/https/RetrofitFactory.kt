package com.fphoenixcorneae.compose.https

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

/**
 * @desc：网络配置
 * @date：2023/03/22 10:06
 */
class RetrofitFactory {

    class Builder {
        var baseUrl: String = "https://www.baidu.com/"

        /** 请求头拦截器 */
        var headerInterceptor: Interceptor = HeaderInterceptor()

        /** 日志拦截器 */
        var loggingInterceptor: Interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        /** 通用参数拦截器 */
        var commonParamsInterceptor: Interceptor = CommonParamsInterceptor()

        fun build(): Retrofit = run {
            Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(buildOkHttpClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder().registerTypeAdapterFactory(GsonTypeAdapterFactory()).create()
                    )
                )
                .build()
        }

        private fun buildOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                // 添加请求header, 注意要设置在日志拦截器之前, 不然Log中会不显示header信息
                .addInterceptor(headerInterceptor)
                .addInterceptor(commonParamsInterceptor)
                // 日志拦截器
                .addInterceptor(loggingInterceptor)
                .sslSocketFactory(SSLSocketClient.getSocketFactory(), SSLSocketClient.x509TrustManager)
                .hostnameVerifier(SSLSocketClient.hostnameVerifier)
                .build()
        }
    }
}
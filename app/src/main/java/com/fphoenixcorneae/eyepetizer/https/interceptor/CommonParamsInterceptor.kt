package com.fphoenixcorneae.eyepetizer.https.interceptor

import com.fphoenixcorneae.common.ext.deviceModel
import com.fphoenixcorneae.common.ext.getUniqueDeviceId
import com.fphoenixcorneae.common.ext.realScreenHeight
import com.fphoenixcorneae.common.ext.realScreenWidth
import com.fphoenixcorneae.common.ext.sdkVersionCode
import com.fphoenixcorneae.common.ext.uniqueDeviceId
import com.fphoenixcorneae.common.util.RomUtil
import com.fphoenixcorneae.eyepetizer.https.EyepetizerApi
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @desc：通用参数
 * @date：2023/08/10 15:51
 */
class CommonParamsInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url
        val url = originalHttpUrl.newBuilder().apply {
            addQueryParameter("deviceModel", deviceModel)
            addQueryParameter("udid", getUniqueDeviceId(useCache = false))
            addQueryParameter("size", "${realScreenWidth}x${realScreenHeight}")
            addQueryParameter("first_channel", RomUtil.romInfo?.name ?: "unknown")
            addQueryParameter("last_channel", RomUtil.romInfo?.name ?: "unknown")
            addQueryParameter("system_version_code", "$sdkVersionCode")
            // 首页推荐接口附加 vc、vn 这两个字段后，请求无响应。
            if (!originalHttpUrl.toString().contains(EyepetizerApi.HOMEPAGE_COMMEND)) {
                // 开眼当前应用程序的版本号
                addQueryParameter("vc", "6030012")
                // 开眼当前应用程序的版本名
                addQueryParameter("vn", "6.3.1")
            }
        }.build()
        val request = originalRequest.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}
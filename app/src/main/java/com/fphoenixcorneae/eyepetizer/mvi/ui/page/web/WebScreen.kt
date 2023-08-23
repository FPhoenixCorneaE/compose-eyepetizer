package com.fphoenixcorneae.eyepetizer.mvi.ui.page.web

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.MutableContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.net.Uri
import android.os.Build
import android.os.Looper
import android.util.Base64
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.JavascriptInterface
import android.webkit.MimeTypeMap
import android.webkit.URLUtil
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fphoenixcorneae.eyepetizer.R
import com.fphoenixcorneae.eyepetizer.ext.clickableNoRipple
import com.fphoenixcorneae.eyepetizer.mvi.ui.nav.NavHostController
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Gray
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.LocalThemeColors
import com.fphoenixcorneae.eyepetizer.mvi.ui.widget.SystemUiScaffold
import com.fphoenixcorneae.eyepetizer.mvi.ui.widget.Toolbar
import com.google.accompanist.web.*
import com.google.accompanist.web.WebView as AccompanistWebView

/**
 * @desc：网页
 * @date：2023/05/17 10:37
 */
@SuppressLint("SetJavaScriptEnabled")
@Preview
@Composable
fun WebScreen(
    url: String = "https://www.baidu.com/",
) {
    val context = LocalContext.current
    var webView by remember { mutableStateOf<WebView?>(null) }
    val state = rememberWebViewState(url)
    val navigator = rememberWebViewNavigator()
    var progress by remember { mutableStateOf(0f) }
    // 用于判断当前是否是预览模式
    val runningInPreview = LocalInspectionMode.current
    SystemUiScaffold {
        Column {
            var titleText by remember { mutableStateOf("") }
            Toolbar(titleText = titleText)
            // 进度条
            AnimatedVisibility(visible = (progress > 0f && progress < 1f)) {
                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp),
                    color = MaterialTheme.colorScheme.onSecondary,
                    backgroundColor = Color.White
                )
            }
            AccompanistWebView(
                state = state,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                captureBackPresses = false,
                navigator = navigator,
                onCreated = { webView ->
                    if (runningInPreview) {
                        // webView.settings breaks the preview 有这东西才能让你预览起来
                        return@AccompanistWebView
                    }
                    // 开启自动适应
                    webView.settings.useWideViewPort = true
                    webView.settings.loadWithOverviewMode = true
                    // 启用js
                    webView.settings.javaScriptEnabled = true
                    webView.addJavascriptInterface(OnJsInterface(), "android")
                    WebViewHelper.setDownloadListener(webView)
                    WebViewHelper.setOnLongClickListener(webView)
                },
                onDispose = { webView ->
                    // 释放
                    webView.removeJavascriptInterface("android")
                    WebViewManager.recycle(webView)
                },
                client = object : AccompanistWebViewClient() {
                    override fun shouldInterceptRequest(
                        view: WebView?,
                        request: WebResourceRequest?,
                    ): WebResourceResponse? {
                        if (view != null && request != null) {
                            when {
                                WebViewHelper.isAssetsResource(request) -> {
                                    return WebViewHelper.assetsResourceRequest(view.context, request)
                                }
                                WebViewHelper.isCacheResource(request) -> {
                                    return WebViewHelper.cacheResourceRequest(view.context, request)
                                }
                            }
                        }
                        return super.shouldInterceptRequest(view, request)
                    }

                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?,
                    ): Boolean {
                        if (view != null && request != null && request.url != null) {
                            if ("http" != request.url.scheme && "https" != request.url.scheme) {
                                try {
                                    view.context.startActivity(Intent(Intent.ACTION_VIEW, request.url))
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                                return true
                            }
                        }
                        return false
                    }
                },
                chromeClient = object : AccompanistWebChromeClient() {

                    override fun onReceivedTitle(view: WebView?, title: String?) {
                        super.onReceivedTitle(view, title)
                        titleText = title.orEmpty()
                    }

                    override fun onProgressChanged(view: WebView?, newProgress: Int) {
                        super.onProgressChanged(view, newProgress)
                        progress = (newProgress / 100f).coerceIn(0f, 1f)
                    }
                },
                factory = { context -> WebViewManager.obtain(context).also { webView = it } }
            )
            Divider(color = Gray, thickness = 0.5.dp)
            // 自定义导航栏
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(MaterialTheme.colorScheme.background),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // 返回按钮
                Image(
                    painter = painterResource(R.drawable.ic_back_black_44dp),
                    contentDescription = null,
                    modifier = Modifier
                        .size(36.dp)
                        .padding(10.dp)
                        .clickableNoRipple {
                            webView?.let {
                                if (!WebViewHelper.goBack(it, url)) {
                                    NavHostController.get().navigateUp()
                                }
                            }
                        },
                )
                // 前进按钮
                Image(
                    painter = painterResource(R.drawable.ic_back_black_44dp),
                    contentDescription = null,
                    modifier = Modifier
                        .size(36.dp)
                        .padding(10.dp)
                        .rotate(180f)
                        .clickableNoRipple {
                            if (navigator.canGoForward) {
                                navigator.navigateForward()
                            }
                        },
                )
                // 刷新按钮
                Image(
                    painter = painterResource(R.drawable.ic_refresh_white_24dp),
                    contentDescription = null,
                    modifier = Modifier
                        .size(36.dp)
                        .padding(8.dp)
                        .clickableNoRipple {
                            navigator.reload()
                        },
                    colorFilter = ColorFilter.tint(color = LocalThemeColors.current.textColorSecondary)
                )
                // 从外部浏览器打开按钮
                Image(
                    painter = painterResource(R.mipmap.ic_browser),
                    contentDescription = null,
                    modifier = Modifier
                        .size(36.dp)
                        .padding(8.dp)
                        .clickableNoRipple {
                            runCatching {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(state.content.getCurrentUrl()))
                                intent.addCategory(Intent.CATEGORY_BROWSABLE)
                                context.startActivity(intent)
                            }.onFailure {
                                it.printStackTrace()
                            }
                        },
                )
            }
        }
    }
}

object WebViewHelper {

    fun setDownloadListener(webView: WebView) {
        webView.setDownloadListener { url, _, _, _, _ ->
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                intent.addCategory(Intent.CATEGORY_BROWSABLE)
                webView.context.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun setOnLongClickListener(webView: WebView) {
        webView.setOnLongClickListener {
            val result = webView.hitTestResult
            when (result.type) {
                WebView.HitTestResult.IMAGE_TYPE, WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE -> {
                    result.extra?.let { data ->
                        if (URLUtil.isValidUrl(data)) {
                            // 下载图片
                        } else {
                            var str = data
                            if (str.contains(",")) {
                                str = str.split(",")[1]
                            }
                            val array = Base64.decode(str, Base64.NO_WRAP)
                            val bitmap = BitmapFactory.decodeByteArray(array, 0, array.size)
                            // 保存图片
                        }
                    }
                    true
                }
                else -> false
            }
        }
    }

    fun goBack(webView: WebView, originalUrl: String): Boolean {
        val canBack = webView.canGoBack()
        if (canBack) webView.goBack()
        val backForwardList = webView.copyBackForwardList()
        val currentIndex = backForwardList.currentIndex
        if (currentIndex == 0) {
            val currentUrl = backForwardList.currentItem?.url
            val currentHost = Uri.parse(currentUrl).host
            //栈底不是链接则直接返回
            if (currentHost.isNullOrBlank()) return false
            //栈底链接不是原始链接则直接返回
            if (originalUrl != currentUrl) return false
        }
        return canBack
    }

    fun snapshotVisible(webView: WebView, callback: (Bitmap) -> Unit) {
        Thread {
            try {
                webView.isVerticalScrollBarEnabled = false
            } catch (e: Exception) {
                e.printStackTrace()
            }
            var contentHeight = webView.contentHeight
            webView.measure(0, 0)
            val measuredHeight = webView.measuredHeight
            if (contentHeight > webView.height && measuredHeight > contentHeight) {
                contentHeight = measuredHeight
            }
            val saveBitmap =
                Bitmap.createBitmap(webView.width, contentHeight, Bitmap.Config.ARGB_8888)
            val tempBitmap =
                Bitmap.createBitmap(webView.width, webView.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas()
            val paint = Paint()
            val src = Rect()//代表图片矩形范围
            val des = RectF()//代表Canvas的矩形范围(显示位置)
            var scrollY = 0f
            while (scrollY < contentHeight) {
                canvas.setBitmap(tempBitmap)
                webView.scrollTo(0, scrollY.toInt())
                webView.draw(canvas)
                Thread.sleep(50)
                val top = scrollY
                scrollY += webView.height
                if (scrollY > contentHeight) {
                    val surplusY = webView.height - (scrollY - contentHeight)
                    src.set(
                        0,
                        (tempBitmap.height - surplusY).toInt(),
                        tempBitmap.width,
                        tempBitmap.height
                    )
                    des.set(0f, top, tempBitmap.width.toFloat(), top + surplusY)
                } else {
                    src.set(0, 0, tempBitmap.width, tempBitmap.height)
                    des.set(0f, top, tempBitmap.width.toFloat(), top + tempBitmap.height.toFloat())
                }
                canvas.setBitmap(saveBitmap)
                canvas.drawBitmap(tempBitmap, src, des, paint)
            }
            callback.invoke(saveBitmap)
        }.start()
    }

    fun isAssetsResource(webRequest: WebResourceRequest): Boolean {
        val url = webRequest.url.toString()
        return url.startsWith("file:///android_asset/")
    }

    fun isCacheResource(webRequest: WebResourceRequest): Boolean {
        val url = webRequest.url.toString()
        val extension = getExtensionFromUrl(url)
        return extension == "ico" || extension == "bmp" || extension == "gif"
                || extension == "jpeg" || extension == "jpg" || extension == "png"
                || extension == "svg" || extension == "webp" || extension == "css"
                || extension == "js" || extension == "json" || extension == "eot"
                || extension == "otf" || extension == "ttf" || extension == "woff"
    }

    fun assetsResourceRequest(
        context: Context,
        webRequest: WebResourceRequest,
    ): WebResourceResponse? {
        try {
            val url = webRequest.url.toString()
            val filenameIndex = url.lastIndexOf("/") + 1
            val filename = url.substring(filenameIndex)
            val suffixIndex = url.lastIndexOf(".")
            val suffix = url.substring(suffixIndex + 1)
            val webResourceResponse = WebResourceResponse(
                getMimeTypeFromUrl(url),
                "UTF-8",
                context.assets.open("$suffix/$filename")
            )
            webResourceResponse.responseHeaders = mapOf("access-control-allow-origin" to "*")
            return webResourceResponse
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun cacheResourceRequest(
        context: Context,
        webRequest: WebResourceRequest,
    ): WebResourceResponse? {
        try {
//            val url = webRequest.url.toString()
//            val cachePath = CacheUtils.getDirPath(context, "web_cache")
//            val filePathName = cachePath + File.separator + url.encodeUtf8().md5().hex()
//            val file = File(filePathName)
//            if (!file.exists() || !file.isFile) {
//                runBlocking {
//                    download(HttpRequest(url).apply {
//                        webRequest.requestHeaders.forEach { putHeader(it.key, it.value) }
//                    }, filePathName)
//                }
//            }
//            if (file.exists() && file.isFile) {
//                val webResourceResponse = WebResourceResponse(
//                    getMimeTypeFromUrl(url),
//                    "UTF-8",
//                    file.inputStream()
//                )
//                webResourceResponse.responseHeaders = mapOf("access-control-allow-origin" to "*")
//                return webResourceResponse
//            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun getExtensionFromUrl(url: String): String {
        try {
            if (url.isNotBlank() && url != "null") {
                return MimeTypeMap.getFileExtensionFromUrl(url)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    private fun getMimeTypeFromUrl(url: String): String {
        try {
            val extension = getExtensionFromUrl(url)
            if (extension.isNotBlank() && extension != "null") {
                if (extension == "json") {
                    return "application/json"
                }
                return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension) ?: "*/*"
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "*/*"
    }

}

@SuppressLint("SetJavaScriptEnabled", "RequiresFeature")
class WebViewManager private constructor() {

    companion object {
        @Volatile
        private var INSTANCE: WebViewManager? = null

        private fun instance() = INSTANCE ?: synchronized(this) {
            INSTANCE ?: WebViewManager().also { INSTANCE = it }
        }

        fun prepare(context: Context) {
            instance().prepare(context)
        }

        fun obtain(context: Context): WebView {
            return instance().obtain(context)
        }

        fun recycle(webView: WebView) {
            instance().recycle(webView)
        }

        fun destroy() {
            instance().destroy()
        }
    }

    private val webViewCache: MutableList<WebView> = ArrayList(1)

    private fun create(context: Context): WebView {
        val webView = WebView(context)
        webView.setBackgroundColor(android.graphics.Color.TRANSPARENT)
        webView.overScrollMode = WebView.OVER_SCROLL_NEVER
        val webSetting = webView.settings
        webSetting.allowFileAccess = true
        webSetting.cacheMode = WebSettings.LOAD_DEFAULT
        webSetting.domStorageEnabled = true
        webSetting.setGeolocationEnabled(true)
        webSetting.javaScriptEnabled = true
        webSetting.loadWithOverviewMode = true
        webSetting.setSupportZoom(true)
        webSetting.displayZoomControls = false
        webSetting.useWideViewPort = true
        webSetting.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
//            val isAppDarkMode = webView.context.isNightMode()
//            WebSettingsCompat.setForceDark(
//                webView.settings,
//                if (isAppDarkMode) WebSettingsCompat.FORCE_DARK_ON else WebSettingsCompat.FORCE_DARK_OFF
//            )
        }
        return webView
    }

    fun prepare(context: Context) {
        if (webViewCache.isEmpty()) {
            Looper.myQueue().addIdleHandler {
                webViewCache.add(create(MutableContextWrapper(context)))
                false
            }
        }
    }

    fun obtain(context: Context): WebView {
        if (webViewCache.isEmpty()) {
            webViewCache.add(create(MutableContextWrapper(context)))
        }
        val webView = webViewCache.removeFirst()
        val contextWrapper = webView.context as MutableContextWrapper
        contextWrapper.baseContext = context
        webView.clearHistory()
        webView.resumeTimers()
        return webView
    }

    fun recycle(webView: WebView) {
        try {
            webView.stopLoading()
            webView.loadDataWithBaseURL("about:blank", "", "text/html", "utf-8", null)
            webView.clearHistory()
            webView.pauseTimers()
            val parent = webView.parent
            if (parent != null) {
                (parent as ViewGroup).removeView(webView)
            }
            val contextWrapper = webView.context as MutableContextWrapper
            contextWrapper.baseContext = webView.context.applicationContext
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (!webViewCache.contains(webView)) {
                webViewCache.add(webView)
            }
        }
    }

    fun destroy() {
        try {
            webViewCache.forEach {
                it.removeAllViews()
                it.destroy()
                webViewCache.remove(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}

/**
 * @desc：JavascriptInterface
 * @date：2023/05/12 17:45
 */
class OnJsInterface {
    @JavascriptInterface
    fun anyMethod(message: String) {

    }
}


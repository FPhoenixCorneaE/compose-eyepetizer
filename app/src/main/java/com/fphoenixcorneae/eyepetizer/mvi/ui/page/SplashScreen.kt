package com.fphoenixcorneae.eyepetizer.mvi.ui.page

import android.Manifest
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentActivity
import com.fphoenixcorneae.eyepetizer.R
import com.fphoenixcorneae.eyepetizer.ext.requestPermissionsUsingPermissionX
import com.fphoenixcorneae.eyepetizer.mvi.ui.nav.NavHostController
import com.fphoenixcorneae.eyepetizer.mvi.ui.nav.NavRoute
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.White20
import com.fphoenixcorneae.eyepetizer.mvi.ui.widget.SystemUiScaffold
import kotlinx.coroutines.delay

/**
 * @desc：闪屏
 * @date：2023/08/08 16:10
 */
@Composable
fun SplashScreen() {
    val context = LocalContext.current
    var allPermissionsGranted by remember { mutableStateOf(false) }
    val logoSloganAlpha by animateFloatAsState(
        targetValue = if (allPermissionsGranted) 1f else 0.5f,
        animationSpec = tween(durationMillis = 3000)
    )
    val bgSplashScale by animateFloatAsState(
        targetValue = if (allPermissionsGranted) 1.05f else 1f,
        animationSpec = tween(durationMillis = 3000)
    )
    LaunchedEffect(key1 = logoSloganAlpha) {
        if (logoSloganAlpha == 1f) {
            // 动画结束
            delay(1500)
            // 跳转首页
            NavHostController.get().navigate(NavRoute.MAIN)
        }
    }
    SystemUiScaffold(statusBarsPadding = false, isDarkFont = false) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .paint(painterResource(id = R.mipmap.img_bg_window), contentScale = ContentScale.FillBounds)
        ) {
            AnimatedVisibility(visible = allPermissionsGranted, enter = fadeIn()) {
                SplashUi(logoSloganAlpha = logoSloganAlpha, bgSplashScale = bgSplashScale)
            }
        }
    }
    SideEffect {
        requestPermissionsUsingPermissionX(
            activity = context as FragmentActivity,
            permissions = listOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE),
            explainMessage = context.getString(R.string.permission_explain),
            forwardToSettingsMessage = context.getString(R.string.permission_explain),
        ) { allGranted, _, _ ->
            if (allGranted) {
                allPermissionsGranted = true
            }
        }
    }
}

/**
 * @desc：
 * @date：2023/08/09 10:58
 */
@Preview
@Composable
private fun SplashUi(
    logoSloganAlpha: Float = 0.5f,
    bgSplashScale: Float = 1f,
) {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.mipmap.img_bg_splash),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .scale(scale = bgSplashScale),
            contentScale = ContentScale.FillBounds,
        )
        Image(
            painter = painterResource(id = R.mipmap.ic_logo_slogan),
            contentDescription = null,
            modifier = Modifier
                .width(134.dp)
                .height(40.dp)
                .align(alignment = Alignment.TopCenter)
                .offset(y = 230.dp)
                .alpha(alpha = logoSloganAlpha)
        )
        Column(
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .padding(bottom = 48.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.splash_slogan_en),
                color = White20,
                fontSize = 15.sp,
                fontFamily = ResourcesCompat.getFont(context, R.font.lobster_1_4)
                    ?.let { FontFamily(it) },
            )
            Text(
                text = stringResource(R.string.splash_slogan_zh_CN),
                color = White20,
                fontSize = 13.sp,
                fontFamily = ResourcesCompat.getFont(context, R.font.fz_lan_ting_hei_s_l_gb_regular)
                    ?.let { FontFamily(it) },
            )
        }
    }
}
package com.fphoenixcorneae.eyepetizer.mvi.ui.page

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import com.fphoenixcorneae.eyepetizer.mvi.ui.nav.EyepetizerScreen
import com.fphoenixcorneae.eyepetizer.mvi.ui.nav.NavHostController
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.ComposeEyepetizerTheme
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.LocalThemeColors
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

/**
 * @desc：
 * @date：2023/08/08 17:14
 */
@OptIn(ExperimentalAnimationApi::class)
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeEyepetizerTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = LocalThemeColors.current.backgroundColor) {
                    NavHostController.setNavHostController(navHostController = rememberAnimatedNavController())
                    EyepetizerScreen()
                }
            }
        }
    }
}
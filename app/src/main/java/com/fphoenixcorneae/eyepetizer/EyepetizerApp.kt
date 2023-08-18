package com.fphoenixcorneae.eyepetizer

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex

/**
 * @desc：
 * @date：2023/08/08 11:39
 */
class EyepetizerApp : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}
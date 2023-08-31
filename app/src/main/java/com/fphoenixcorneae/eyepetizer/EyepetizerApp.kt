package com.fphoenixcorneae.eyepetizer

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.tencent.mmkv.MMKV

/**
 * @desc：
 * @date：2023/08/08 11:39
 */
class EyepetizerApp : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
    }
}
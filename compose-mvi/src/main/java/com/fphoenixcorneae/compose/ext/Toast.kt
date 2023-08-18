package com.fphoenixcorneae.compose.ext

import android.view.Gravity
import android.widget.Toast
import com.fphoenixcorneae.compose.startup.applicationContext

/**
 * 默认的Toast
 */
fun CharSequence.toast(duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(applicationContext, this, duration).show()
}

/**
 * 居中的Toast
 */
fun CharSequence.toastCenter(duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(applicationContext, this, duration)
        .apply {
            setGravity(Gravity.CENTER, 0, 0)
        }.show()
}
package com.fphoenixcorneae.eyepetizer.ext

import com.fphoenixcorneae.common.ext.HHmmFormat
import com.fphoenixcorneae.common.ext.yyyyMMddFormat
import com.fphoenixcorneae.common.ext.yyyyMMddHHmmFormat

fun Long.toReplyTimeString(): String = run {
    val diff = System.currentTimeMillis() - this
    if (diff > -60 * 1000) {
        if (diff < 24 * 60 * 60 * 1000) {
            HHmmFormat.format(this)
        } else {
            yyyyMMddFormat.format(this)
        }
    } else {
        yyyyMMddHHmmFormat.format(this)
    }
}
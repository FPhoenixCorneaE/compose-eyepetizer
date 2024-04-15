package com.fphoenixcorneae.eyepetizer.ext

/**
 * 获取转换后的时间样式。
 *
 * @return 处理后的时间样式，示例：06:50
 */
fun Int.toVideoDuration(): String {
    val minute = 1 * 60
    val hour = 60 * minute
    return if (this < hour) {
        String.format("%02d:%02d", this / minute, this % 60)
    } else {
        String.format("%02d:%02d:%02d", this / hour, (this % hour) / minute, this % 60)
    }
}

fun Int.floorMod(other: Int): Int = when (other) {
    0 -> this
    else -> this - floorDiv(other) * other
}
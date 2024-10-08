package com.leovp.module.common.utils

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

/**
 * Author: Michael Leo
 * Date: 2023/7/28 15:16
 */

/**
 * See the source from Google.
 *
 * Make sure the result is between 0 and [other] - 1.
 *
 * https://bit.ly/3ri7hlp
 */
fun Int.floorMod(other: Int): Int = when (other) {
    0 -> this
    else -> this - floorDiv(other) * other
}

fun Long.toCounterBadgeText(limitation: Int = 99): String {
    return when {
        this < 1L -> ""
        this in 1L..limitation -> this.toString()
        this > 9999L -> "${this / 10000}w+"
        else -> "$limitation+"
    }
}

fun Int.toCounterBadgeText(limitation: Int = 99): String {
    return this.toLong().toCounterBadgeText(limitation)
}

val monthDateFormat =
    SimpleDateFormat("MM-dd", Locale.CHINA).apply { timeZone = TimeZone.getDefault() }

fun Long.formatTimestampShort(): String {
    val minutes = TimeUnit.MILLISECONDS.toMinutes(this)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(this) - TimeUnit.MINUTES.toSeconds(minutes)
    return String.format(Locale.ENGLISH, "%02d:%02d", minutes, seconds)
}

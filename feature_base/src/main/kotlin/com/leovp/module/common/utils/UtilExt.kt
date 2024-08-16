package com.leovp.module.common.utils

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

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

fun Int.toBadgeText(limitation: Int = 99): String {
    return when {
        this <= 0 -> ""
        this in 1..limitation -> this.toString()
        else -> "$limitation+"
    }
}

val monthDateFormat =
    SimpleDateFormat("MM-dd", Locale.CHINA).apply { timeZone = TimeZone.getDefault() }

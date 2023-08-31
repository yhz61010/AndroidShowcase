package com.leovp.module.common.utils

/**
 * Author: Michael Leo
 * Date: 2023/7/28 15:16
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
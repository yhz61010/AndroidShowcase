package com.leovp.androidshowcase.util

/**
 * Author: Michael Leo
 * Date: 2023/7/28 15:16
 */

fun Int.floorMod(other: Int): Int = when (other) {
    0 -> this
    else -> this - floorDiv(other) * other
}
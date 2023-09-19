package com.leovp.module.common.utils

import com.leovp.log.LogContext
import com.leovp.module.common.GlobalConst

/**
 * Author: Michael Leo
 * Date: 2023/9/19 16:08
 */

inline fun debugLog(
    tag: String = "DEBUG",
    fullOutput: Boolean = false,
    outputType: Int = -1,
    generateMsg: () -> String?
) {
    if (GlobalConst.DEBUG) {
        LogContext.log.d(tag, generateMsg(), fullOutput = fullOutput, outputType = outputType)
    }
}
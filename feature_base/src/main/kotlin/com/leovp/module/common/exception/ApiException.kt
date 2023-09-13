package com.leovp.module.common.exception

/**
 * Author: Michael Leo
 * Date: 2023/9/13 16:22
 */
class ApiException(message: String? = null, cause: Throwable? = null) : Exception(message, cause) {
    constructor(
        code: Int,
        message: String? = null,
        cause: Throwable? = null,
    ) : this("$code:$message", cause)
}
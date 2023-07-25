package com.leovp.module.common

/**
 * A generic class that holds a value or an exception
 */
sealed class RequestResult<out R> {
    data class Success<out T>(val data: T) : RequestResult<T>()
    data class Error(val exception: Exception) : RequestResult<Nothing>()
}

fun <T> RequestResult<T>.successOr(fallback: T): T {
    return (this as? RequestResult.Success<T>)?.data ?: fallback
}

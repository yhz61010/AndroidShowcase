@file:Suppress("unused")

package com.leovp.module.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext

/**
 * A generic class that holds a value or an exception
 */
sealed interface Result<out R> {
    data class Success<out T>(val data: T) : Result<T>
    data class Failure(val exception: Throwable) : Result<Nothing>

    val isSuccess: Boolean get() = this is Success
    val isFailure: Boolean get() = this is Failure
}

fun <R, T : R> Result<T>.getOrDefault(defaultValue: T): R = when {
    isFailure -> defaultValue
    else -> (this as Result.Success<T>).data
}

fun <T> Result<T>.getOrNull(): T? = when {
    isFailure -> null
    else -> (this as Result.Success<T>).data
}

fun <T> Result<T>.getOrThrow(): T = when {
    isFailure -> throw (this as Result.Failure).exception
    else -> (this as Result.Success<T>).data
}

inline fun <R, T : R> Result<T>.getOrElse(onFailure: (exception: Throwable) -> R): R {
    return when (val exception = exceptionOrNull()) {
        null -> (this as Result.Success<T>).data
        else -> onFailure(exception)
    }
}

inline fun <T, R> Result<T>.map(transform: (value: T) -> R): Result<R> {
    return when (val exception = exceptionOrNull()) {
        null -> Result.Success(transform((this as Result.Success<T>).data))
        else -> Result.Failure(exception)
    }
}

fun <T> Result<T>.exceptionOrNull(): Throwable? = when {
    isFailure -> (this as Result.Failure).exception
    else -> null
}

inline fun <T> Result<T>.onSuccess(action: (value: T) -> Unit): Result<T> {
    if (isSuccess) action((this as Result.Success<T>).data)
    return this
}

inline fun <T> Result<T>.onFailure(action: (exception: Throwable) -> Unit): Result<T> {
    exceptionOrNull()?.let { action(it) }
    return this
}

inline fun <T, R> Result<T>.fold(
    onSuccess: (value: T) -> R, onFailure: (exception: Throwable) -> R
): R {
    return when (val exception = exceptionOrNull()) {
        null -> onSuccess((this as Result.Success<T>).data)
        else -> onFailure(exception)
    }
}

// ----------

suspend inline fun <reified R> result(
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    crossinline block: suspend CoroutineScope.() -> R,
): Result<R> = supervisorScope {
    runCatching {
        Result.Success(withContext(dispatcher) { block() })
    }.getOrElse { Result.Failure(it) }
}

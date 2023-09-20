package com.leovp.module.common.http.interceptors

import com.leovp.log.LogContext
import com.leovp.log.base.AbsLog.Companion.OUTPUT_TYPE_HTTP_HEADER_COOKIE
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Author: Michael Leo
 * Date: 2023/9/6 09:19
 */
class HeaderInterceptor(private val headerMap: Map<String, String>? = null) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.request().let {
        val request = headerMap?.let { headers ->
            val builder = it.newBuilder()
            for ((k, v) in headers) {
                LogContext.log.d(
                    "Assign cookie: $k=$v",
                    outputType = OUTPUT_TYPE_HTTP_HEADER_COOKIE
                )
                builder.addHeader(k, v)
            }
            builder.build()
        } ?: it
        chain.proceed(request)
    }
}


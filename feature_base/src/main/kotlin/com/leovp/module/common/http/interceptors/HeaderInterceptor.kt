package com.leovp.module.common.http.interceptors

import android.R.id.message
import com.leovp.log.base.LogOutType
import com.leovp.module.common.log.d
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
                d {
                    tag = "Interceptor"
                    outputType = LogOutType.HTTP_HEADER
                    block = { "Assign cookie: $k=$v" }
                }
                builder.addHeader(k, v)
            }
            builder.build()
        } ?: it
        chain.proceed(request)
    }
}


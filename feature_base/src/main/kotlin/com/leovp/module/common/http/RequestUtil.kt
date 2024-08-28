package com.leovp.module.common.http

import android.content.Context
import com.drake.net.NetConfig
import com.drake.net.interceptor.RetryInterceptor
import com.drake.net.okhttp.setConverter
import com.leovp.module.common.GlobalConst
import com.leovp.module.common.http.converters.SerializationConverter
import com.leovp.module.common.http.interceptors.AuthenticationInterceptor
import com.leovp.module.common.http.interceptors.HeaderInterceptor
import com.leovp.module.common.http.interceptors.HttpLoggingInterceptor
import com.leovp.module.common.http.interceptors.UserAgentInterceptor
import java.util.concurrent.TimeUnit

/**
 * Author: Michael Leo
 * Date: 2023/9/5 17:18
 */
object RequestUtil {
    fun initNetEngine(
        baseUrl: String,
        context: Context? = null,
        headerMap: Map<String, String>? = null,
    ) {
        NetConfig.initialize(baseUrl, context) {
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(10, TimeUnit.SECONDS)
            writeTimeout(10, TimeUnit.SECONDS)
            addInterceptor(AuthenticationInterceptor(GlobalConst.API_TOKEN))
            addInterceptor(UserAgentInterceptor())
            addInterceptor(HeaderInterceptor(headerMap))
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            addInterceptor(RetryInterceptor(3))
            // setConverter(GsonConverter())
            setConverter(SerializationConverter())
        }
    }
}

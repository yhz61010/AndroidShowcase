package com.leovp.module.common.http.converters

import com.drake.net.convert.NetConverter
import com.drake.net.exception.ConvertException
import com.drake.net.exception.RequestParamsException
import com.drake.net.exception.ServerResponseException
import com.drake.net.request.kType
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import okhttp3.Response
import java.lang.reflect.Type
import kotlin.reflect.KType

/**
 * Author: Michael Leo
 * Date: 2023/9/6 10:32
 */
class SerializationConverter : NetConverter {

    companion object {
        @OptIn(ExperimentalSerializationApi::class)
        val jsonDecoder = Json {
            explicitNulls = false
            ignoreUnknownKeys = true
            coerceInputValues = false
        }
    }

    override fun <R> onConvert(succeed: Type, response: Response): R? {
        try {
            return NetConverter.onConvert<R>(succeed, response)
        } catch (e: ConvertException) {
            val code = response.code
            when {
                code in 200..299 -> {
                    val bodyString = response.body?.string() ?: return null
                    val kType = response.request.kType
                        ?: throw ConvertException(response, "Request does not contain KType")
                    return bodyString.parseBody<R>(kType)
                }

                code in 400..499 -> throw RequestParamsException(response, code.toString())
                code >= 500 -> throw ServerResponseException(response, code.toString())
                else -> throw ConvertException(response)
            }
        }
    }

    fun <R> String.parseBody(succeed: KType): R? {
        return jsonDecoder.decodeFromString(Json.serializersModule.serializer(succeed), this) as R
    }
}
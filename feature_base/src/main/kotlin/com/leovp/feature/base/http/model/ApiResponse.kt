package com.leovp.feature.base.http.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.feature.base.http.model.ApiResponseModel.Companion.isReloginCode
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Date: 2023/9/15 14:05
 * Author: Michael Leo
 */

@Keep
@Serializable
@OptIn(InternalSerializationApi::class)
@Immutable
open class ApiResponse(
    @SerialName("code") val code: Int = 0,
    @SerialName("message") val message: String? = null,
) {
    open fun isBizSuccess(): Boolean = code == 200

    open fun shouldRelogin(): Boolean = isReloginCode(code)

    fun getBizErrorPair(): Pair<Int, String>? =
        if (this.isBizSuccess()) {
            null
        } else {
            Pair(this.code, this.message ?: "[Empty message]")
        }

}

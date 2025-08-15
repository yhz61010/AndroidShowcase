package com.leovp.feature_discovery.data.datasource.api.response

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.feature.base.http.model.ApiResponseResult
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

/**
 * Author: Michael Leo
 * Date: 2024/9/23 17:35
 */

@Keep
@Immutable
@OptIn(InternalSerializationApi::class)
@Serializable
data class MusicAvailableResponse(val success: Boolean) : ApiResponseResult()

// {
//   "code": 200,
//   "success": false,
//   "message": "亲爱的,暂无版权"
// }
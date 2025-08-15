package com.leovp.feature_discovery.data.datasource.api.response

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.feature_discovery.data.datasource.api.model.CommentApiModel
import com.leovp.feature.base.http.model.ApiResponseResult
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

/**
 * Author: Michael Leo
 * Date: 2024/9/19 13:47
 */

@Keep
@Immutable
@OptIn(InternalSerializationApi::class)
@Serializable
data class CommentResponse(
    val isMusician: Boolean = false,
    val userId: Long = 0,
    val total: Long = 0,
    val more: Boolean = false,
    val comments: List<CommentApiModel> = emptyList(),
    val topComments: List<CommentApiModel> = emptyList(),
    val hotComments: List<CommentApiModel> = emptyList(),
) : ApiResponseResult()
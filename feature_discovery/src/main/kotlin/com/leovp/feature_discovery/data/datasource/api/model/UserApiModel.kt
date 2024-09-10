package com.leovp.feature_discovery.data.datasource.api.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

/**
 * Author: Michael Leo
 * Date: 2024/9/19 13:51
 */


@Keep
@Immutable
@OptIn(InternalSerializationApi::class)
@Serializable
data class UserApiModel(
    val userId: Long,
    val nickname: String,
    val avatarUrl: String,
    val remarkName: String? = null,
)
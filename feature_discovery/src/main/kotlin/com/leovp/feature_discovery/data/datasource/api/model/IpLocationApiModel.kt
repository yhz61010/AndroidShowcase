package com.leovp.feature_discovery.data.datasource.api.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

/**
 * Author: Michael Leo
 * Date: 2024/9/19 13:57
 */

@Keep
@Immutable
@OptIn(InternalSerializationApi::class)
@Serializable
data class IpLocationApiModel(
    val ip: String?,
    val location: String,
    val userId: Long,
)
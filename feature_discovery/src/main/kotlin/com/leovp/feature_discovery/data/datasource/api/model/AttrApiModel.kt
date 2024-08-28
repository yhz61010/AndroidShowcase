package com.leovp.feature_discovery.data.datasource.api.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Author: Michael Leo
 * Date: 2023/9/6 11:00
 */

@Keep
@Immutable
@OptIn(InternalSerializationApi::class)
@Serializable
data class AttrApiModel(
    val country: String?, // nullable from api
    val artist: String?, // nullable from api

    val page: Int?,
    val perPage: Int?,
    val total: Int?,
    val totalPages: Int?,

    @SerialName("for")
    val forField: String?,

    val rank: Int?,
)
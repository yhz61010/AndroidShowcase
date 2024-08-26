package com.leovp.feature_discovery.data.datasource.api.model


import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Immutable
@OptIn(InternalSerializationApi::class)
@Serializable
data class OpenSearchQueryApiModel(
    @SerialName("role")
    val role: String,
    @SerialName("searchTerms")
    val searchTerms: String,
    @SerialName("startPage")
    val startPage: String,
    @SerialName("#text")
    val text: String
)
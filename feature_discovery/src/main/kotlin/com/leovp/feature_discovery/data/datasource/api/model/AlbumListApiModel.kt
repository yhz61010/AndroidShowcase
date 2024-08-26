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
data class AlbumListApiModel(
    @SerialName("album")
    val album: List<AlbumApiModel>
)
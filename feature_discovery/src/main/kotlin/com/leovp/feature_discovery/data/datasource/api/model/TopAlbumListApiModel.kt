package com.leovp.feature_discovery.data.datasource.api.model


import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
  * Author: Michael Leo
  * Date: 2023/9/15 16:12
  */

@Keep
@Immutable
@OptIn(InternalSerializationApi::class)
@Serializable
data class TopAlbumListApiModel(
    @SerialName("album")
    val album: List<TopAlbumApiModel>,

    @SerialName("@attr") val attr: AttrApiModel,
)
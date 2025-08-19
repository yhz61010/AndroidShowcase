package com.leovp.discovery.data.datasource.api.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.discovery.domain.model.AlbumModel
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@Keep
@Immutable
@OptIn(InternalSerializationApi::class)
@Serializable
data class AlbumApiModel(
    val id: Long,
    val name: String,
    val picUrl: String,
)

fun AlbumApiModel.toDomainModel(): AlbumModel =
    AlbumModel(
        id = this.id,
        name = this.name,
        picUrl = this.picUrl,
    )

package com.leovp.discovery.data.datasource.api.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.discovery.domain.model.ArtistModel
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

/**
 * Author: Michael Leo
 * Date: 2023/9/6 11:00
 */

@Keep
@Immutable
@OptIn(InternalSerializationApi::class)
@Serializable
data class ArtistApiModel(
    val id: Long,
    val name: String,
    val picId: Long = 0,
    val picUrl: String = "",
)

fun ArtistApiModel.toDomainModel(): ArtistModel =
    ArtistModel(
        id = this.id,
        name = this.name,
        picId = this.picId,
        picUrl = this.picUrl,
    )

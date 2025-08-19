package com.leovp.discovery.data.datasource.api.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.discovery.domain.model.PlaylistModel
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

/**
 * Author: Michael Leo
 * Date: 2024/9/11 15:51
 */

@Keep
@Immutable
@OptIn(InternalSerializationApi::class)
@Serializable
data class PlaylistApiModel(
    val id: Long,
    val type: Int,
    val name: String,
    val copywriter: String?,
    val picUrl: String,
    val canDislike: Boolean,
    val trackNumberUpdateTime: Long,
    val playCount: Long,
    val trackCount: Long,
    val highQuality: Boolean,
    val alg: String,
)

fun PlaylistApiModel.toDomainModel(): PlaylistModel =
    PlaylistModel(
        id = this.id,
        type = this.type,
        picUrl = this.picUrl,
        name = this.name,
    )

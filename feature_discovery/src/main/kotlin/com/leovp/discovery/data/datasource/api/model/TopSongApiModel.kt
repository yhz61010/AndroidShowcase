package com.leovp.discovery.data.datasource.api.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.discovery.domain.model.TopSongModel
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

/**
 * Author: Michael Leo
 * Date: 2024/9/11 17:11
 */

@Keep
@Immutable
@OptIn(InternalSerializationApi::class)
@Serializable
data class TopSongApiModel(
    val id: Long,
    val name: String,
    val artists: List<ArtistApiModel>,
    val album: AlbumApiModel,
)

fun TopSongApiModel.toDomainModel(): TopSongModel =
    TopSongModel(
        id = this.id,
        name = this.name,
        album = this.album.toDomainModel(),
        artists = this.artists.map { it.toDomainModel() },
    )

package com.leovp.feature_discovery.data.datasource.api.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.feature_discovery.domain.enum.MarkType
import com.leovp.feature_discovery.domain.model.MusicItem
import com.leovp.feature_discovery.domain.model.SongItem
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
data class TrackApiModel(

    @SerialName("@attr") val attr: AttrApiModel?, // nullable for ?method=track.getInfo
    val artist: ArtistApiModel,
    val duration: Long?,
    @SerialName("image") val images: List<ImageApiModel>?,
    val listeners: Int,
    val mbid: String?, // nullable from api
    val name: String,
    val url: String,

    val album: AlbumApiModel?,
    @SerialName("playcount") val playCount: Int?,
)

fun TrackApiModel.toDomainModel(index: Int): MusicItem {
    val images = this.images?.filterNot { it.size == ImageSizeApiModel.UNKNOWN || it.url.isBlank() }
        ?.map { it.toDomainModel() }

    return MusicItem(
        id = index,
        thumbnail = images ?: emptyList(),
        title = name,
        subTitle = artist.name,
        markText = "$listeners",
        showTrailIcon = false,
        type = MarkType.Hot,
    )
}

fun TrackApiModel.toSongDomainModel(): SongItem {
    requireNotNull(this.mbid) { "mbid can not be null for track.getInfo" }
    val images = this.images?.filterNot { it.size == ImageSizeApiModel.UNKNOWN || it.url.isBlank() }
        ?.map { it.toDomainModel() }
    return SongItem(
        mbid = this.mbid,
        name = this.name,
        duration = this.duration ?: 0,
        artist = this.artist.name,
        albumImages = images ?: emptyList(),
        quality = SongItem.Quality.STANDARD,
    )
}
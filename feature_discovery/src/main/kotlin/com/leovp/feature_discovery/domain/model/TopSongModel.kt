package com.leovp.feature_discovery.domain.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.feature_discovery.domain.enum.MarkType
import com.leovp.feature.base.GlobalConst.ImageThumb

/**
 * Author: Michael Leo
 * Date: 2023/9/5 14:21
 */

@Keep
@Immutable
data class TopSongModel(
    val id: Long,
    val name: String,
    val album: AlbumModel,
    val artists: List<ArtistModel> = emptyList(),
) {
    var showTrailIcon: Boolean = false

    var markType: MarkType = MarkType.None

    fun getAlbumCoverUrl(
        width: ImageThumb = ImageThumb.TRACK_THUMB_WIDTH,
        height: ImageThumb = ImageThumb.TRACK_THUMB_HEIGHT
    ): String =
        this.album.picUrl + ("?param=${width.value}x${height.value}"
            .takeIf { width.value > 0 && height.value > 0 } ?: "")

    fun getDefaultArtistUrl(
        width: ImageThumb = ImageThumb.ALBUM_WIDTH,
        height: ImageThumb = ImageThumb.ALBUM_HEIGHT
    ): String =
        this.artists.first().picUrl + ("?param=${width.value}x${height.value}"
            .takeIf { width.value > 0 && height.value > 0 } ?: "")

    fun getDefaultArtistName(): String = this.artists.first().name
}

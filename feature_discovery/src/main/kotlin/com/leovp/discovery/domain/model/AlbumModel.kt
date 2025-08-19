package com.leovp.discovery.domain.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.feature.base.GlobalConst.ImageThumb
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

/**
 * Author: Michael Leo
 * Date: 2024/9/12 08:57
 */

@Keep
@Immutable
@OptIn(InternalSerializationApi::class)
@Serializable
data class AlbumModel(
    val id: Long,
    val name: String,
    val picUrl: String,
) {
    fun getAlbumCoverUrl(
        width: ImageThumb = ImageThumb.TRACK_LARGE_WIDTH,
        height: ImageThumb = ImageThumb.TRACK_LARGE_HEIGHT,
    ): String =
        this.picUrl + (
            "?param=${width.value}x${height.value}"
                .takeIf { width.value > 0 && height.value > 0 } ?: ""
        )
}

package com.leovp.discovery.domain.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.feature.base.GlobalConst.ImageThumb

/**
 * Author: Michael Leo
 * Date: 2023/7/24 15:29
 */

@Keep
@Immutable
data class PlaylistModel(
    val id: Long,
    val type: Int = 0,
    val name: String,
    val picUrl: String,
) {
    fun getPlayListPicUrl(
        width: ImageThumb = ImageThumb.PLAYLIST_WIDTH,
        height: ImageThumb = ImageThumb.PLAYLIST_HEIGHT,
    ): String =
        this.picUrl + (
            "?param=${width.value}x${height.value}"
                .takeIf { width.value > 0 && height.value > 0 } ?: ""
        )
}

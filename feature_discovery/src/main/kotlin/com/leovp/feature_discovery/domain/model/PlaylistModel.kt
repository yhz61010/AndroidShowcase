package com.leovp.feature_discovery.domain.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.module.common.GlobalConst.ImageThumb

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
    fun getThumbPicUrl(
        width: ImageThumb = ImageThumb.PLAYLIST_WIDTH,
        height: ImageThumb = ImageThumb.PLAYLIST_HEIGHT
    ): String =
        this.picUrl + ("?param=${width.value}y${height.value}"
            .takeIf { width.value > 0 && height.value > 0 } ?: "")
}
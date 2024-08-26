package com.leovp.feature_discovery.domain.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.feature_discovery.domain.enum.ImageSize

/**
  * Author: Michael Leo
  * Date: 2024/8/26 13:58
  */
@Keep
@Immutable
data class SongItem(
    val mbid: String,
    val name: String,
    val duration: Int,
    val artist: String,
    val albumImages: List<Image> = emptyList(),
) {
    fun getDefaultAlbumUrl() = this.albumImages.firstOrNull { it.size == ImageSize.EXTRA_LARGE }?.url
}


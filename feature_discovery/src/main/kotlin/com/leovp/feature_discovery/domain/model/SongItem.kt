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
    val duration: Long,
    val artist: String,
    val albumImages: List<Image> = emptyList(),
    val quality: Quality,

    val favoriteCount: Long = 0,
    val commentCount: Long = 0,

    var commentData: Comment? = null,
) {
    fun getDefaultAlbumUrl() =
        this.albumImages.firstOrNull { it.size == ImageSize.EXTRA_LARGE }?.url

    @Keep
    @Immutable
    data class Comment(
        val id: Long,
        val comment: String,
    )

    enum class Quality {
        STANDARD,
        HIGHER,
        EXHIGH,
        LOSSLESS,
        HIRES,
        JYEFFECT,
        SKY,
        JYMASTER,
    }
}


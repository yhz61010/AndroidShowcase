@file:Suppress("unused")
package com.leovp.feature_discovery.domain.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable

/**
 * Author: Michael Leo
 * Date: 2024/8/26 13:58
 */
@Keep
@Immutable
data class SongModel(
    val id: Long,
    val name: String,
    val duration: Long,
    val artists: List<ArtistModel>,
    val album: AlbumModel,
    val quality: Quality,

    val markText: String? = null,
) {
    var redCountModel: RedCountModel? = null

    var commentsModel: CommentsModel? = null

    @Keep
    @Immutable
    data class Comment(
        val id: Long,
        val comment: String,
    )

    @Keep
    @Immutable
    data class CommentsModel(
        val totalComments: Long = 0,
        val comments: List<Comment> = emptyList(),
        val topComments: List<Comment> = emptyList(),
        val hotComments: List<Comment> = emptyList(),
    )

    @Keep
    @Immutable
    data class RedCountModel(
        val count: Long,
        val countDesc: String?
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


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
    val fee: Int,

    val markText: String? = null,
) {
    var redCountModel: RedCountModel? = null

    var commentsModel: CommentsModel? = null

    var urlModel: UrlModel? = null

    fun getUrlSuccess(): Boolean = urlModel?.success() == true
    fun getUrlCode(): Int = urlModel?.code ?: -1
    fun getUrl(): String? = urlModel?.url

    @Keep
    @Immutable
    data class MusicAvailableModel(val success: Boolean, val message: String? = null)

    @Keep
    @Immutable
    data class UrlModel(
        val id: Long,
        val br: Int,
        val size: Long,
        val md5: String?,
        val type: String?,
        val gain: Float,
        val peak: Int?,
        val level: String?,
        val encodeType: String?,
        val url: String?,
        val time: Long,
        val code: Int,
        val fee: Int,
        val payed: Int,
    ) {
        fun success(): Boolean = code == 200
    }

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

    enum class Quality(val levelIdx: Int) {
        Standard(0),
        Higher(1),
        Exhigh(2),
        Lossless(3),
        Hires(4),
        Jyeffect(5),
        Sky(6),
        Dolby(7),
        Jymaster(8),
    }

    fun getSongArtist(def: String = ""): String = artists.firstOrNull()?.name ?: def

    fun getSongRedCount(): Long = redCountModel?.count ?: 0
    fun getSongRedCountStr(): String? = redCountModel?.countDesc
    fun getSongCommentCount(): Long = commentsModel?.totalComments ?: 0

    fun getSongFullName(defArtist: String = "", defTrack: String = ""): String =
        "${getSongArtist(defArtist)}-$name"
}


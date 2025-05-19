package com.leovp.feature_discovery.data.datasource.api.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.feature_discovery.domain.model.SongModel
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Author: Michael Leo
 * Date: 2024/9/18 14:52
 */

@Keep
@Immutable
@OptIn(InternalSerializationApi::class)
@Serializable
data class SongDetailApiModel(
    /** 歌曲 ID */
    val id: Long,
    /** 歌曲名称 */
    val name: String,
    /**
     * single: enum,
     *   0: 有专辑信息或者是 DJ 节目
     *   1: 未知专辑
     */
    val single: Int = 0,
    /**
     * Album, 专辑，
     * 如果是 DJ 节目(djId != 0)或者无专辑信息(single == 1)，则专辑 ID 为 0
     */
    @SerialName("al") val album: AlbumApiModel,
    @SerialName("ar") val artists: List<ArtistApiModel>,
    /** u64: 歌曲时长 */
    val dt: Long,

    /**
     * djId: u64
     * 0: 不是DJ节目
     * 其他：是DJ节目，表示 DJ ID
     */
    val djId: Long = 0L,

    /**
     * fee: enum
     *   0: 免费或无版权
     *   1: VIP 歌曲
     *   4: 购买专辑
     *   8: 非会员可免费播放低音质，会员可播放高音质及下载
     *   fee 为 1 或 8 的歌曲均可单独购买 2 元单曲
     */
    val fee: Int,
)

fun SongDetailApiModel.toDomainModel(): SongModel {
    return SongModel(
        id = this.id,
        name = this.name,
        duration = this.dt,
        artists = this.artists.map { it.toDomainModel() },
        album = this.album.toDomainModel(),
        quality = SongModel.Quality.Standard,
        fee = this.fee,
    )
}
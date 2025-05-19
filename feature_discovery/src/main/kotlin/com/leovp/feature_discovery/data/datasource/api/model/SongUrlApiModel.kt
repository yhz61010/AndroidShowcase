package com.leovp.feature_discovery.data.datasource.api.model


import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.feature_discovery.domain.model.SongModel
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable
import kotlin.Int

/**
 * Author: Michael Leo
 * Date: 2024/9/23 10:41
 */

@Keep
@Immutable
@OptIn(InternalSerializationApi::class)
@Serializable
data class SongUrlApiModel(
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
    val musicId: Long?,
    val code: Int,
    val fee: Int,
    val payed: Int,
)

fun SongUrlApiModel.toDomainModel(): SongModel.UrlModel {
    return SongModel.UrlModel(
        id = id,
        br = br,
        size = size,
        md5 = md5,
        type = type,
        gain = gain,
        peak = peak,
        level = level,
        encodeType = encodeType,
        url = url,
        time = time,
        code = code,
        fee = fee,
        payed = payed,
    )
}

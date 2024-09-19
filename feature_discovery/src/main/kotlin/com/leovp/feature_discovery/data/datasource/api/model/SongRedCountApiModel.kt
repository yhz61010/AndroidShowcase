package com.leovp.feature_discovery.data.datasource.api.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.feature_discovery.domain.model.SongModel
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

/**
 * Author: Michael Leo
 * Date: 2024/9/19 18:53
 */

@Keep
@Immutable
@OptIn(InternalSerializationApi::class)
@Serializable
data class SongRedCountApiModel(
    val count: Long,
    val countDesc: String?,
)

fun SongRedCountApiModel.toDomainModel(): SongModel.RedCountModel {
    return SongModel.RedCountModel(
        count = count,
        countDesc = countDesc
    )
}
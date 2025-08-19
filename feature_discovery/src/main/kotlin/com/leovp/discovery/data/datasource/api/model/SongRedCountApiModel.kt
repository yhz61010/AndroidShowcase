package com.leovp.discovery.data.datasource.api.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.discovery.domain.model.SongModel
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

fun SongRedCountApiModel.toDomainModel(): SongModel.RedCountModel =
    SongModel.RedCountModel(
        count = count,
        countDesc = countDesc,
    )

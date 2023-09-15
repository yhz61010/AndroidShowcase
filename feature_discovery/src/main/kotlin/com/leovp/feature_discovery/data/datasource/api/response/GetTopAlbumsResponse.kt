package com.leovp.feature_discovery.data.datasource.api.response

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.feature_discovery.data.datasource.api.model.TopAlbumListApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Author: Michael Leo
 * Date: 2023/9/15 14:43
 */

@Keep
@Immutable
@Serializable
data class GetTopAlbumsResponse(@SerialName("topalbums") val topalbums: TopAlbumListApiModel)
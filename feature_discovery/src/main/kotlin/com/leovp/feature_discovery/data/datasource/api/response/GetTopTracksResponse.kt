package com.leovp.feature_discovery.data.datasource.api.response

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.feature_discovery.data.datasource.api.model.TrackListApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Author: Michael Leo
 * Date: 2023/9/6 14:52
 */

@Keep
@Immutable
@Serializable
data class GetTopTracksResponse(
    @SerialName("toptracks") val toptracks: TrackListApiModel,
)
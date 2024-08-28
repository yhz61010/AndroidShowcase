package com.leovp.feature_discovery.data.datasource.api.response

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.feature_discovery.data.datasource.api.model.TrackApiModel
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

/**
  * Author: Michael Leo
  * Date: 2024/8/26 15:09
  */

@Keep
@Immutable
@OptIn(InternalSerializationApi::class)
@Serializable
data class GetTrackInfoResponse(
    val track: TrackApiModel
)
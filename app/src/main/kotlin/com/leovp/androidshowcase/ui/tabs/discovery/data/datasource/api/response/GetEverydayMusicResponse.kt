package com.leovp.androidshowcase.ui.tabs.discovery.data.datasource.api.response

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.androidshowcase.ui.tabs.discovery.data.datasource.api.model.TrackListApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Author: Michael Leo
 * Date: 2023/9/6 10:07
 */

@Keep
@Immutable
@Serializable
data class GetEverydayMusicResponse(
    @SerialName("tracks") val tracks: TrackListApiModel,
)
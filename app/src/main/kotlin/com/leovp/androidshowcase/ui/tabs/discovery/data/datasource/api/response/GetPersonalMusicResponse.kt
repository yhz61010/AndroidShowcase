package com.leovp.androidshowcase.ui.tabs.discovery.data.datasource.api.response

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.androidshowcase.ui.tabs.discovery.data.datasource.api.model.TracksX2
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Author: Michael Leo
 * Date: 2023/9/6 14:52
 */

@Keep
@Immutable
@Serializable
data class GetPersonalMusicResponse(
    @SerialName("toptracks") val toptracks: TracksX2,
)
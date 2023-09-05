package com.leovp.androidshowcase.ui.tabs.discovery.data.datasource.api.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
  * Author: Michael Leo
  * Date: 2023/9/6 16:00
  */

@Keep
@Immutable
@Serializable
data class TracksX2(
    @SerialName("@attr") val attr: Attr,
    val track: List<Track2>
)
package com.leovp.feature_discovery.domain.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.feature_discovery.domain.enum.ImageSize
import kotlinx.serialization.Serializable

/**
  * Author: Michael Leo
  * Date: 2023/9/6 13:09
  */

@Keep
@Immutable
@Serializable
data class Image(
    val url: String,
    val size: ImageSize,
)
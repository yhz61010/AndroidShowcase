package com.leovp.feature_discovery.domain.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

/**
  * Author: Michael Leo
  * Date: 2024/9/12 13:11
  */
@Keep
@Immutable
@OptIn(InternalSerializationApi::class)
@Serializable
data class BannerModel(
    val bannerId: String,
    val pic: String,
    val url: String?,
    val titleColor: String,
    val typeTitle: String,
)

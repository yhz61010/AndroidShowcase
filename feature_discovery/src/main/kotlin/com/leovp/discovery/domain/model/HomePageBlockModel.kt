package com.leovp.discovery.domain.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

/**
 * Author: Michael Leo
 * Date: 2024/9/12 09:39
 */

@Keep
@Immutable
@OptIn(InternalSerializationApi::class)
@Serializable
data class HomePageBlockModel(
    val banners: List<BannerModel> = emptyList(),
)

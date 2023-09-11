package com.leovp.feature_discovery.domain.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable

/**
 * Author: Michael Leo
 * Date: 2023/9/5 14:20
 */

@Keep
@Immutable
data class CarouselItem(
    val id: Int,
    val thumbnail: String
)

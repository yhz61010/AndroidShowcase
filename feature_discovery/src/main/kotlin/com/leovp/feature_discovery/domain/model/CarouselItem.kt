package com.leovp.feature_discovery.domain.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.feature_discovery.domain.enum.ImageSize

/**
 * Author: Michael Leo
 * Date: 2023/9/5 14:20
 */

@Keep
@Immutable
data class CarouselItem(
    val id: Int,
    val thumbnail: List<Image> = emptyList(),
) {
    fun getDefaultImageUrl() = this.thumbnail.firstOrNull { it.size == ImageSize.EXTRA_LARGE }?.url
}


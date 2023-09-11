package com.leovp.feature_discovery.domain.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import com.leovp.feature_discovery.domain.enum.ImageSize

/**
 * Author: Michael Leo
 * Date: 2023/9/5 14:21
 */

@Keep
@Immutable
data class EverydayItem(
    val id: Int,
    val thumbnail: List<Image> = emptyList(),
    val type: String,
    val title: String,
    val icon: ImageVector?
) {
    fun getDefaultImageUrl() = this.thumbnail.firstOrNull { it.size == ImageSize.EXTRA_LARGE }?.url
}

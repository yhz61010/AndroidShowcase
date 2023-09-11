package com.leovp.feature_discovery.domain.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.feature_discovery.domain.enum.ImageSize
import com.leovp.feature_discovery.domain.enum.MarkType

/**
 * Author: Michael Leo
 * Date: 2023/7/24 15:29
 */

@Keep
@Immutable
data class MusicItem(
    val id: Int,
    val thumbnail: List<Image> = emptyList(),
    val title: String,
    val subTitle: String,
    val markText: String,
    val showTrailIcon: Boolean,
    val type: MarkType
) {
    val markTextWithText = false

    fun getMarkTextString(suffix: String): String = "${this.markText}$suffix"

    fun getDefaultImageUrl() = this.thumbnail.firstOrNull { it.size == ImageSize.EXTRA_LARGE }?.url
}


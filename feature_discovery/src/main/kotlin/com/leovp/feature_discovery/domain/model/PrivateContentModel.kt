package com.leovp.feature_discovery.domain.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.module.common.GlobalConst.ImageThumb
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

/**
 * Author: Michael Leo
 * Date: 2023/9/5 14:20
 */

@Keep
@Immutable
@OptIn(InternalSerializationApi::class)
@Serializable
data class PrivateContentModel(
    val id: Long,
    val name: String,
    val url: String = "",
    val picUrl: String,
) {
    var type: Int = 0
    var typeName: String = ""

    fun getThumbPicUrl(
        width: ImageThumb = ImageThumb.BANNER_WIDTH,
        height: ImageThumb = ImageThumb.BANNER_HEIGHT
    ): String =
        this.picUrl + ("?param=${width.value}y${height.value}"
            .takeIf { width.value > 0 && height.value > 0 } ?: "")
}
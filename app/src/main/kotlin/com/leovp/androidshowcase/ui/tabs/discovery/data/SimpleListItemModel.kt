package com.leovp.androidshowcase.ui.tabs.discovery.data

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import com.leovp.androidshowcase.ui.tabs.discovery.iters.MarkType

/**
 * Author: Michael Leo
 * Date: 2023/7/24 15:29
 */

@Keep
@Immutable
data class SimpleListItemModel(
    val id: Int,
    val thumbnail: String,
    val title: String,
    val subTitle: String,
    val markText: String,
    val showTrailIcon: Boolean,
    val type: MarkType
)

@Keep
@Immutable
data class CarouselItemModel(
    val id: Int,
    val thumbnail: String
)


@Keep
@Immutable
data class EverydayItemModel(
    val id: Int,
    val thumbnail: String,
    val type: String,
    val title: String,
    val icon: ImageVector?
)

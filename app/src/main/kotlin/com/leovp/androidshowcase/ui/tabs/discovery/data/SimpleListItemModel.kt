package com.leovp.androidshowcase.ui.tabs.discovery.data

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.androidshowcase.ui.tabs.discovery.iters.MarkType

/**
 * Author: Michael Leo
 * Date: 2023/7/24 15:29
 */
@Keep
@Immutable
data class SimpleListItemModel(
    val thumbnail: String,
    val title: String,
    val subTitle: String,
    val markText: String,
    val showTrailIcon: Boolean,
    val type: MarkType
)

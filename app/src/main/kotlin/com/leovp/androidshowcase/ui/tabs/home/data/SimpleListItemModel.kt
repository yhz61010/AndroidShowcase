package com.leovp.androidshowcase.ui.tabs.home.data

import androidx.compose.runtime.Immutable
import com.leovp.androidshowcase.ui.tabs.home.iters.MarkType

/**
 * Author: Michael Leo
 * Date: 2023/7/24 15:29
 */
@Immutable
data class SimpleListItemModel(
    val thumbnail: String,
    val title: String,
    val subTitle: String,
    val markText: String,
    val showTrailIcon: Boolean,
    val type: MarkType
)

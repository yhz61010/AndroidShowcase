package com.leovp.androidshowcase.domain.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.leovp.feature.base.ui.Screen

/**
 * Author: Michael Leo
 * Date: 2023/9/12 13:33
 */

@Keep
@Immutable
data class UnreadModel(
    val key: Screen,
    val value: Int,
)

package com.leovp.discovery.presentation.discovery.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.leovp.discovery.domain.enum.MarkType
import com.leovp.discovery.domain.model.TopSongModel
import com.leovp.discovery.ui.theme.mark_hot_bg
import com.leovp.discovery.ui.theme.mark_quality_border
import com.leovp.discovery.ui.theme.mark_vip_bg
import com.leovp.discovery.ui.theme.mark_vip_border

/**
 * Author: Michael Leo
 * Date: 2025/8/20 15:20
 */

fun Modifier.supportingBorder(
    data: TopSongModel,
    defModifier: Modifier,
    border: Dp,
    shape: CornerBasedShape
): Modifier = this.then(
    when (data.markType) {
        MarkType.Hot -> defModifier
        MarkType.Special -> Modifier.border(border, mark_quality_border, shape)
        MarkType.HiRes, MarkType.Vip -> Modifier.border(border, mark_vip_border, shape)
        else -> defModifier
    }
)

fun Modifier.supportingBackground(data: TopSongModel, shape: CornerBasedShape): Modifier =
    this.then(
        when (data.markType) {
            MarkType.HiRes -> Modifier.background(mark_vip_bg, shape)
            MarkType.Hot -> Modifier.background(mark_hot_bg, shape)
            else -> Modifier
        }
    )

fun Modifier.supportingPadding(data: TopSongModel): Modifier = this.then(
    when (data.markType) {
        MarkType.HiRes -> Modifier.padding(horizontal = 4.dp, vertical = 0.dp)
        MarkType.Vip -> Modifier.padding(horizontal = 2.dp)
        MarkType.Hot -> Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
        else -> Modifier.padding(horizontal = 4.dp)
    }
)

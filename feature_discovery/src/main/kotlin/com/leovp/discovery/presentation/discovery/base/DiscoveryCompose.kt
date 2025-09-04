package com.leovp.discovery.presentation.discovery.base

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.leovp.discovery.domain.enum.MarkType
import com.leovp.discovery.domain.model.TopSongModel
import com.leovp.discovery.ui.theme.mark_hot_bg
import com.leovp.discovery.ui.theme.mark_hot_text_color
import com.leovp.discovery.ui.theme.mark_quality_border
import com.leovp.discovery.ui.theme.mark_quality_text_color
import com.leovp.discovery.ui.theme.mark_vip_bg
import com.leovp.discovery.ui.theme.mark_vip_border
import com.leovp.discovery.ui.theme.mark_vip_text_color
import com.leovp.discovery.ui.theme.place_holder2_bg_color
import com.leovp.discovery.ui.theme.place_holder_err_bg_color

/**
 * Author: Michael Leo
 * Date: 2025/8/20 15:20
 */

fun Modifier.supportingBorder(
    data: TopSongModel,
    defModifier: Modifier,
    border: Dp,
    shape: CornerBasedShape,
): Modifier =
    this.then(
        when (data.markType) {
            MarkType.Hot -> defModifier
            MarkType.Special -> Modifier.border(border, mark_quality_border, shape)
            MarkType.HiRes, MarkType.Vip ->
                Modifier.border(
                    border,
                    mark_vip_border,
                    shape,
                )

            else -> defModifier
        },
    )

fun Modifier.supportingBackground(
    data: TopSongModel,
    shape: CornerBasedShape,
): Modifier =
    this.then(
        when (data.markType) {
            MarkType.HiRes -> Modifier.background(mark_vip_bg, shape)
            MarkType.Hot -> Modifier.background(mark_hot_bg, shape)
            else -> Modifier
        },
    )

fun Modifier.supportingPadding(data: TopSongModel): Modifier =
    this.then(
        when (data.markType) {
            MarkType.HiRes -> Modifier.padding(horizontal = 4.dp, vertical = 0.dp)
            MarkType.Vip -> Modifier.padding(horizontal = 2.dp)
            MarkType.Hot -> Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
            else -> Modifier.padding(horizontal = 4.dp)
        },
    )

@Composable
fun SupportingContent(data: TopSongModel) {
    val supportBorderWidth = 0.4.dp
    val supportSmallRounded = MaterialTheme.shapes.small
    val hotModifier =
        Modifier.border(
            supportBorderWidth,
            mark_hot_bg,
            supportSmallRounded,
        )
    Row(verticalAlignment = Alignment.CenterVertically) {
        val borderModifier =
            Modifier.supportingBorder(
                data = data,
                defModifier = hotModifier,
                border = supportBorderWidth,
                shape = supportSmallRounded,
            )
        val backgroundModifier = Modifier.supportingBackground(data, supportSmallRounded)
        val paddingModifier = Modifier.supportingPadding(data)
        val textColor =
            when (data.markType) {
                MarkType.HiRes, MarkType.Hot -> mark_hot_text_color
                MarkType.Special -> mark_quality_text_color
                MarkType.Vip -> mark_vip_text_color
                else -> mark_hot_text_color
            }
        if (data.markType != MarkType.None) {
            Text(
                modifier =
                    Modifier
                        .then(borderModifier)
                        .then(backgroundModifier)
                        .then(paddingModifier),
                text = data.markType.text,
                color = textColor,
                fontSize =
                    when (data.markType) {
                        MarkType.Vip -> 8.sp
                        else -> 9.sp
                    },
                fontWeight = FontWeight.Black,
                fontFamily =
                    when (data.markType) {
                        MarkType.HiRes, MarkType.Vip -> FontFamily.SansSerif
                        else -> null
                    },
                lineHeight = 14.sp,
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
        Text(
            text = data.getDefaultArtistName(),
            style = MaterialTheme.typography.labelMedium,
            color = Color.Gray,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
fun ListItemImage(
    imageUrl: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp,
) {
    AsyncImage(
        model =
            ImageRequest
                .Builder(
                    LocalContext.current,
                ).data(imageUrl)
                .crossfade(true)
                .build(),
        contentDescription = contentDescription,
        placeholder = ColorPainter(place_holder2_bg_color),
        error = ColorPainter(place_holder_err_bg_color),
        contentScale = ContentScale.Fit,
        // filterQuality = FilterQuality.Low,
        modifier =
            modifier
                .shadow(elevation)
                .clip(RoundedCornerShape(8.dp)),
    )
}

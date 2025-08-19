package com.leovp.discovery.presentation.player.composable

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.leovp.compose.utils.toCounterBadgeText
import com.leovp.discovery.R
import com.leovp.discovery.domain.model.SongModel
import com.leovp.kotlin.exts.formatTimestampShort
import com.leovp.log.base.d
import com.smarttoolfactory.slider.ColorfulSlider
import com.smarttoolfactory.slider.MaterialSliderDefaults
import com.smarttoolfactory.slider.SliderBrushColor
import kotlinx.coroutines.flow.StateFlow

/**
 * Author: Michael Leo
 * Date: 2025/8/19 18:20
 */

private const val TAG = "PlayerScreen"

@Composable
fun TrackBadge(
    @DrawableRes id: Int,
    count: Long,
    onClick: () -> Unit,
    countStr: String? = null,
) {
    Box(modifier = Modifier.clickable(onClick = onClick)) {
        Icon(
            painter = painterResource(id = id),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary,
        )
        Badge(
            modifier =
                Modifier
                    .align(Alignment.TopEnd)
                    .padding(20.dp, 0.dp, 0.dp, 0.dp),
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ) {
            Text(
                modifier = Modifier.background(Color.Transparent),
                text = countStr ?: count.toCounterBadgeText(9999),
            )
        }
    }
}

/**
 * @param positionState The value of the slider in milliseconds.
 * @param duration The duration in milliseconds.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeekbarItem(
    positionState: StateFlow<Float>,
    duration: Float,
    quality: SongModel.Quality,
    onPositionChange: (Float) -> Unit,
    onQualityClick: () -> Unit,
) {
    val position = positionState.collectAsStateWithLifecycle().value
    SideEffect {
        d(TAG) { "Play position=$position/$duration" }
    }
    val res = LocalResources.current
    val qualityIdx = quality.ordinal
    val qualityName =
        res.getStringArray(
            R.array.dis_player_song_quality_name,
        )[qualityIdx]

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(8.dp, 0.dp),
    ) {
        // SideEffect {
        //     d(TAG) { "=> Enter SeekbarItem <=  quality=$quality
        //     duration=$duration  position=$position" }
        // }
        ColorfulSlider(
            value = position,
            onValueChange = onPositionChange,
            modifier =
                Modifier
                    .padding(0.dp)
                    .semantics { contentDescription = "Seekbar" }
                    .fillMaxWidth()
                    .requiredHeight(12.dp),
            valueRange = 0f..duration,
            onValueChangeFinished = {
                // launch some business logic update with the state you hold
                // viewModel.updateSelectedSliderValue(sliderPosition)
            },
            trackHeight = 2.dp,
            thumbRadius = 4.dp,
            colors =
                MaterialSliderDefaults.materialColors(
                    thumbColor =
                        SliderBrushColor(
                            MaterialTheme.colorScheme.onPrimary,
                        ),
                    activeTrackColor =
                        SliderBrushColor(
                            MaterialTheme.colorScheme.primaryContainer,
                        ),
                    inactiveTrackColor =
                        SliderBrushColor(
                            MaterialTheme.colorScheme.primaryContainer.copy(
                                alpha = 0.55f,
                            ),
                        ),
                ),
        ) // end of Slider
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(2.dp, 0.dp)
                    .alpha(0.85f),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            DurationItem(text = position.toLong().formatTimestampShort())
            DurationItem(text = qualityName, onClick = onQualityClick)
            DurationItem(text = duration.toLong().formatTimestampShort())
        } // end of duration row
    }
}

@Composable
fun DurationItem(
    text: String,
    onClick: (() -> Unit)? = null,
) {
    Text(
        modifier = onClick?.let { Modifier.clickable { it() } } ?: Modifier,
        text = text,
        color = MaterialTheme.colorScheme.onPrimary,
        style = MaterialTheme.typography.labelMedium,
    )
}

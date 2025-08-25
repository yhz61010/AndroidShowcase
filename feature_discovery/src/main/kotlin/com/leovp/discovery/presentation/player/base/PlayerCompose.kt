package com.leovp.discovery.presentation.player.base

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.leovp.android.exts.toast
import com.leovp.compose.utils.toCounterBadgeText
import com.leovp.discovery.R
import com.leovp.discovery.domain.model.SongModel
import com.leovp.discovery.presentation.player.base.PlayerContract.PlayerUiEvent
import com.leovp.discovery.presentation.player.base.PlayerContract.PlayerUiState
import com.leovp.discovery.ui.theme.mark_vip_bg2
import com.leovp.kotlin.exts.formatTimestampShort
import com.leovp.log.base.d
import com.leovp.ui.theme.ImmersiveTheme
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
private fun DurationItem(
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

@Composable
fun TitleContent(
    uiState: PlayerUiState.Content,
    defArtist: String,
    defTrack: String,
) {
    val songTitle =
        uiState.songInfo?.getSongFullName(
            defArtist = defArtist,
            defTrack = defTrack,
        ) ?: ""

    Text(
        text = songTitle,
        color = MaterialTheme.colorScheme.onPrimary,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
private fun CommentItem(
    commentsData: SongModel.Comment,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val inlineMoreContentId = "more"
    val text =
        buildAnnotatedString {
            append(commentsData.comment)
            appendInlineContent(inlineMoreContentId, "[more]")
        }
    val inlineContent =
        mapOf(
            inlineMoreContentId to
                InlineTextContent(
                    placeholder =
                        Placeholder(
                            width = 22.sp,
                            height = 22.sp,
                            placeholderVerticalAlign = PlaceholderVerticalAlign.Center,
                        ),
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        modifier = Modifier.size(22.dp),
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                },
        )
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = text,
            inlineContent = inlineContent,
            modifier =
                Modifier
                    .background(
                        color = mark_vip_bg2,
                        shape = MaterialTheme.shapes.extraLarge,
                    )
                    .clickable(onClick = onClick)
                    .padding(horizontal = 12.dp, vertical = 4.dp)
                    .alpha(0.6f),
            color = MaterialTheme.colorScheme.onPrimary,
            style =
                MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Black,
                ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
fun ExtraControllerItem(
    onMirrorClick: () -> Unit,
    onDownloadClick: () -> Unit,
    onInfoClick: () -> Unit,
) {
    val size = 28.dp
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(8.dp, 0.dp, 8.dp, 0.dp)
                .alpha(0.4f),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        ControllerIconButton(
            R.drawable.dis_music_cast_24px,
            onMirrorClick,
            size,
        )
        ControllerIconButton(
            R.drawable.dis_download_24px,
            onDownloadClick,
            size,
        )
        ControllerIconButton(R.drawable.dis_more_horiz_24px, onInfoClick, size)
    }
}

@Composable
fun CoverAndHotCommentContent(
    songInfo: SongModel?,
    onHotCommentClick: () -> Unit,
) {
    val ctx = LocalContext.current
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(360.dp),
    ) {
        Box {
            AsyncImage(
                model =
                    ImageRequest
                        .Builder(ctx)
                        .data(
                            songInfo?.album?.getAlbumCoverUrl(),
                        ).crossfade(true)
                        .build(),
                contentDescription = null,
                placeholder =
                    ColorPainter(
                        MaterialTheme.colorScheme.onPrimaryContainer,
                    ),
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize(),
                alignment = Alignment.TopCenter,
            )
            val commentsData = songInfo?.commentsModel
            val commentList: List<SongModel.Comment> =
                commentsData?.hotComments ?: emptyList()
            if (commentList.isNotEmpty()) {
                CommentItem(
                    commentsData = commentList.first(),
                    onClick = {
                        ctx.toast("You click on Hot Comment.")
                        onHotCommentClick()
                    },
                    modifier =
                        Modifier
                            .align(Alignment.BottomCenter)
                            .padding(0.dp, 10.dp),
                )
            }
        }
    }
}

@Composable
fun ControllerItem(
    onRepeatClick: () -> Unit,
    onBackwardClick: () -> Unit,
    onPlayPauseClick: () -> Unit,
    onForwardClick: () -> Unit,
    onPlaylistClick: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(8.dp, 8.dp)
                .alpha(0.9f),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        ControllerIconButton(R.drawable.dis_refresh_24px, onRepeatClick)
        ControllerIconButton(R.drawable.dis_skip_previous_24px, onBackwardClick)
        ControllerIconButton(
            R.drawable.dis_play_arrow_24px,
            onPlayPauseClick,
            58.dp,
        )
        ControllerIconButton(R.drawable.dis_skip_next_24px, onForwardClick)
        ControllerIconButton(R.drawable.dis_queue_music_24px, onPlaylistClick)
    }
}

@Composable
private fun ControllerIconButton(
    @DrawableRes resId: Int,
    onClick: () -> Unit,
    size: Dp = 32.dp,
) {
    IconButton(onClick = onClick) {
        Icon(
            modifier = Modifier.requiredSize(size),
            painter = painterResource(resId),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Composable
fun RowScope.TrackInfoItem(
    markText: String?,
    artist: String,
    track: String,
    onEvent: (PlayerUiEvent.SongEvent) -> Unit,
) {
    Column(modifier = Modifier.weight(0.55f)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val smallRounded = MaterialTheme.shapes.small
            Text(
                text = track,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            markText?.let {
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    modifier =
                        Modifier
                            .background(
                                color = mark_vip_bg2,
                                shape = smallRounded,
                            )
                            .padding(horizontal = 4.dp, vertical = 0.dp)
                            .clickable(onClick = { onEvent(PlayerUiEvent.SongEvent.MarkClick) })
                            .alpha(0.6f),
                    text = it,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 8.0.sp,
                    fontWeight = FontWeight.Black,
                )
            }
        }
        Spacer(modifier = Modifier.height(2.dp))
        Row(
            modifier =
                Modifier
                    .alpha(0.8f)
                    .clickable(
                        onClick = { onEvent(PlayerUiEvent.SongEvent.ArtistClick) },
                    ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.wrapContentSize(),
                text = artist,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
                modifier =
                    Modifier
                        .align(alignment = Alignment.CenterVertically)
                        .padding(
                            start = 0.dp,
                            top = 2.dp,
                            end = 0.dp,
                            bottom = 0.dp,
                        ),
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
            )
        }
    } // end of track and artist column
}

@Preview(name = "Daylight")
@Preview(name = "Night", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewPlayerCompose() {
    ImmersiveTheme(
        systemBarColor = Color.Transparent,
        dynamicColor = false,
        lightSystemBar = false,
    ) {
        Row {
            TrackInfoItem(
                markText = "Mark Text",
                artist = "Artist",
                track = "Track",
                onEvent = {},
            )
        }
    }
}

package com.leovp.feature_discovery.presentation

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Badge
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.leovp.android.exts.toast
import com.leovp.feature_discovery.testdata.PreviewPlayerModule
import com.leovp.feature_discovery.ui.theme.mark_vip_bg2
import com.leovp.feature_discovery.ui.theme.place_holder_bg_color
import com.leovp.module.common.exception.ApiException
import com.leovp.module.common.log.d
import com.leovp.module.common.presentation.viewmodel.viewModelProviderFactoryOf
import com.leovp.module.common.utils.previewInitLog
import com.leovp.ui.theme.AppTheme

/**
 * Author: Michael Leo
 * Date: 2024/8/21 13:48
 */
private const val TAG = "PlayerScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(
    viewModel: PlayerViewModel,
    artist: String,
    track: String,
    onMenuUpAction: () -> Unit,
    onShareAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    d(TAG) { "=> Enter PlayerScreen <=" }
    val ctx = LocalContext.current

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    LaunchedEffect(Unit) {
        viewModel.getData(artist = artist, album = track)
    }

    uiState.exception?.let {
        val apiException = it as ApiException
        val code = apiException.code
        val message = apiException.message
        ctx.toast("$code: $message", error = true)
    }

    val songInfo = uiState.songInfo

    // uiState.value.exception?.let {
    //     val apiException = it as ApiException
    //     val code = apiException.code
    //     val message = apiException.message
    //     ctx.toast("$code: $message", error = true)
    // }

    SideEffect {
        d(TAG) { "Player loading=${viewModel.loading}  name=${songInfo?.name}" }
    }
    // val context = LocalContext.current
    val topAppBarState = rememberTopAppBarState()
    // val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)
    val containerBg = MaterialTheme.colorScheme.primary
    Scaffold(
        // contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = containerBg,
        topBar = {
            CenterAlignedTopAppBar(
                // colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                //     containerColor = Color.Cyan
                // ),
                // WindowInsets.waterfall // WindowInsets.displayCutout // or all 0.dp
                // windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
                title = {
                    Text(
                        text = track,
                        color = MaterialTheme.colorScheme.onPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors().copy(
                    containerColor = containerBg,
                ),
                navigationIcon = {
                    IconButton(onClick = onMenuUpAction) {
                        Icon(
                            modifier = Modifier.requiredSize(32.dp),
                            painter = painterResource(
                                id = com.leovp.feature_discovery.R.drawable.dis_baseline_keyboard_arrow_down_24,
                            ),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onShareAction) {
                        Icon(
                            painter = painterResource(
                                id = com.leovp.feature_discovery.R.drawable.dis_share_circle_fill,
                            ),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            ) // end of CenterAlignedTopAppBar
        }, // end of topBar
    ) { contentPadding ->
        PlayerScreenContent(
            modifier = Modifier
                // .nestedScroll(scrollBehavior.nestedScrollConnection)
                // innerPadding takes into account the top and bottom bar
                .padding(contentPadding)
        )
    }
}

@Composable
fun TrackBadge(@DrawableRes id: Int, text: String) {
    Box {
        Icon(
            painter = painterResource(id = id),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary,
        )
        Badge(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(20.dp, 0.dp, 0.dp, 0.dp),
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            Text(
                modifier = Modifier.background(Color.Transparent),
                text = text,
            )
        }
    }
}

@Composable
fun CommentItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 12.dp, 16.dp, 0.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "热评：放给舍友听的时候，她只回一句这... >",
            modifier = Modifier
                .background(
                    color = mark_vip_bg2,
                    shape = MaterialTheme.shapes.extraLarge,
                )
                .padding(horizontal = 12.dp, vertical = 4.dp)
                .alpha(0.6f),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Black
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
fun TrackArtistItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 20.dp, 16.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(0.55f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val smallRounded = MaterialTheme.shapes.small
                Text(
                    text = "我想大声告诉你",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    modifier = Modifier
                        .background(
                            color = mark_vip_bg2,
                            shape = smallRounded,
                        )
                        .padding(horizontal = 4.dp, vertical = 0.dp)
                        .alpha(0.6f),
                    text = "VIP",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 8.0.sp,
                    fontWeight = FontWeight.Black,
                )
            }
            Row(
                modifier = Modifier
                    .alpha(0.8f)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.wrapContentSize(),
                    text = "梵凡",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    modifier = Modifier
                        .size(22.dp)
                        .rotate(-90f),
                    painter = painterResource(
                        id = com.leovp.feature_discovery.R.drawable.dis_baseline_keyboard_arrow_down_24,
                    ),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }
        } // end of track and artist column
        Row(
            modifier = Modifier.weight(0.45f),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TrackBadge(
                com.leovp.feature_discovery.R.drawable.dis_favorite_24px, "100w+"
            )
            Spacer(modifier = Modifier.width(28.dp))
            TrackBadge(com.leovp.feature_discovery.R.drawable.dis_chat_24px, "100w+")
        }
    } // end of music title row
}

@Composable
fun DurationItem(text: String, onClick: () -> Unit = {}) {
    Text(
        modifier = Modifier.clickable { onClick() },
        text = text,
        color = MaterialTheme.colorScheme.onPrimary,
        style = MaterialTheme.typography.labelMedium,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeekbarItem(posState: MutableState<Float>, maxValue: Float) {
    val sliderState = remember {
        SliderState(value = posState.value, valueRange = 0f..maxValue, onValueChangeFinished = {
            // launch some business logic update with the state you hold
            // viewModel.updateSelectedSliderValue(sliderPosition)
        })
    }
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    val colors = SliderDefaults.colors(
        thumbColor = MaterialTheme.colorScheme.primaryContainer,
        activeTrackColor = MaterialTheme.colorScheme.primaryContainer,
        inactiveTrackColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f),
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 24.dp, 8.dp, 0.dp)
    ) {
        Slider(state = sliderState,
               modifier = Modifier
                   .semantics { contentDescription = "Seekbar" }
                   .height(2.dp)
                   .fillMaxWidth(),
               interactionSource = interactionSource,
               thumb = {
                   Icon(
                       painter = painterResource(com.leovp.feature_discovery.R.drawable.dis_baseline_circle_24),
                       contentDescription = null,
                       modifier = Modifier
                           .padding(0.dp, 3.5f.dp)
                           .size(ButtonDefaults.IconSize)
                           .scale(0.7f, 0.7f),
                       tint = colors.thumbColor,
                   )
               },
               track = {
                   SliderDefaults.Track(
                       modifier = Modifier.scale(1f, 0.5f),
                       colors = colors,
                       sliderState = sliderState,
                   )
               }) // end of Slider
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 16.dp)
                .alpha(0.9f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DurationItem("00:00")
            DurationItem("高清臻音") {}
            DurationItem("04:37")
        } // end of duration row
    }
}

@Composable
fun ControllerIconButton(
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
fun ControllerItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 12.dp, 8.dp, 0.dp)
            .alpha(0.9f),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ControllerIconButton(com.leovp.feature_discovery.R.drawable.dis_refresh_24px, {})
        ControllerIconButton(com.leovp.feature_discovery.R.drawable.dis_skip_previous_24px, {})
        ControllerIconButton(com.leovp.feature_discovery.R.drawable.dis_play_arrow_24px, {}, 56.dp)
        ControllerIconButton(com.leovp.feature_discovery.R.drawable.dis_skip_next_24px, {})
        ControllerIconButton(com.leovp.feature_discovery.R.drawable.dis_queue_music_24px, {})
    }
}

@Composable
fun ExtraControllerItem() {
    val size = 28.dp
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 12.dp)
            .alpha(0.5f),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ControllerIconButton(com.leovp.feature_discovery.R.drawable.dis_music_cast_24px, {}, size)
        ControllerIconButton(com.leovp.feature_discovery.R.drawable.dis_download_24px, {}, size)
        ControllerIconButton(com.leovp.feature_discovery.R.drawable.dis_more_horiz_24px, {}, size)
    }
}

@Composable
fun PlayerScreenContent(
    modifier: Modifier = Modifier,
) {
    var posState = remember { mutableStateOf(60f) }

    Column(
        // contentPadding = PaddingValues(16.dp),
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(420.dp),
            verticalArrangement = Arrangement.Top,
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://lib.leovp.com/img/zhangshaohan-Aurora.jpg").crossfade(true)
                    .build(),
                contentDescription = null,
                placeholder = ColorPainter(place_holder_bg_color),
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize(),
            )
        }
        CommentItem()
        TrackArtistItem()
        SeekbarItem(posState, 357f)
        ControllerItem()
        Spacer(modifier = Modifier.weight(1f))
        ExtraControllerItem()
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewPlayerScreen() {
    previewInitLog()

    val viewModel: PlayerViewModel = viewModel(
        factory = viewModelProviderFactoryOf {
            PlayerViewModel(PreviewPlayerModule.previewPlayerUseCase)
        },
    )
    AppTheme {
        PlayerScreen(
            viewModel = viewModel,
            artist = "鄧麗君",
            track = "甜蜜蜜",
            onMenuUpAction = {},
            onShareAction = {},
        )
    }
}

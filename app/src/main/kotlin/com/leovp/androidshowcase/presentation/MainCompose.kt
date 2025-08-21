package com.leovp.androidshowcase.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.leovp.androidshowcase.R
import com.leovp.feature.base.ui.Screen
import com.leovp.log.base.d
import com.leovp.ui.theme.discovery_top_section_end_color
import com.leovp.ui.theme.discovery_top_section_middle2_color
import com.leovp.ui.theme.discovery_top_section_middle3_color
import com.leovp.ui.theme.discovery_top_section_start_color

/**
 * Author: Michael Leo
 * Date: 2025/8/20 17:15
 */

@Suppress("unused")
@Composable
internal fun LinearGradientBox(scrollState: LazyListState) {
    d(TAG) { "=> Enter LinearGradientBox <=" }
    val statusBarHeight =
        WindowInsets.statusBars
            .asPaddingValues()
            .calculateTopPadding()
            .value
    val targetHeight =
        LocalDensity.current.run {
            // (56 + 150 + 2 * 8 + statusBarHeight).dp.toPx()
            (56 + 2 * 8 + statusBarHeight).dp.toPx()
        }

    val firstVisibleItemIndex by remember {
        derivedStateOf { scrollState.firstVisibleItemIndex }
    }
    val firstVisibleItemScrollOffset by remember {
        derivedStateOf { scrollState.firstVisibleItemScrollOffset }
    }
    val scrollPercent: Float =
        if (firstVisibleItemIndex >
            0
        ) {
            1f
        } else {
            firstVisibleItemScrollOffset / targetHeight
        }

    Box(
        modifier =
            Modifier
                .testTag("linear-gradient-box")
                .fillMaxSize()
                .alpha(1 - scrollPercent),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(
                        brush =
                            Brush.linearGradient(
                                listOf(
                                    discovery_top_section_start_color,
                                    discovery_top_section_middle2_color,
                                    discovery_top_section_middle3_color,
                                    discovery_top_section_end_color,
                                ),
                                start = Offset(Float.POSITIVE_INFINITY, 0f),
                                end = Offset(0f, Float.POSITIVE_INFINITY),
                                tileMode = TileMode.Clamp,
                            ),
                    ),
        ) {
            Spacer(modifier = Modifier.statusBarsPadding())
            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}

@Composable
internal fun TabIcon(screen: Screen) {
    Icon(
        painter = painterResource(id = screen.iconResId),
        contentDescription = stringResource(screen.nameResId),
    )
}

@Composable
internal fun HomeTopMenu(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(painterResource(R.drawable.app_menu), null)
    }
}

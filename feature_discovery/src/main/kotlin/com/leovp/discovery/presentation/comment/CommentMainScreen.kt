package com.leovp.discovery.presentation.comment

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.leovp.discovery.domain.model.AlbumModel
import com.leovp.discovery.domain.model.ArtistModel
import com.leovp.discovery.domain.model.SongModel
import com.leovp.discovery.domain.model.SongModel.Quality
import com.leovp.feature.base.ui.CommentBottomNavigationItems
import com.leovp.feature.base.ui.LocalNavigationActions
import com.leovp.feature.base.ui.PreviewWrapper
import com.leovp.feature.base.R as BaseR

/**
 * Author: Michael Leo
 * Date: 2025/8/26 13:14
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentMainScreen(songInfo: SongModel) {
    // val context = LocalContext.current
    val navController = LocalNavigationActions.current

    val pagerScreenValues = CommentBottomNavigationItems.entries.toTypedArray()
    val pagerState =
        rememberPagerState(
            initialPage = CommentBottomNavigationItems.COMMENT.ordinal,
            initialPageOffsetFraction = 0f,
            pageCount = { pagerScreenValues.size },
        )

    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior =
        TopAppBarDefaults.enterAlwaysScrollBehavior(
            topAppBarState,
        )
    Scaffold(
        // contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            val titleResId = pagerScreenValues[pagerState.targetPage].screen.nameResId
            CenterAlignedTopAppBar(
                // colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                //     containerColor = Color.Cyan
                // ),
                // WindowInsets.waterfall // WindowInsets.displayCutout // or all 0.dp
                // windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
                title = {
                    Text(text = stringResource(titleResId))
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter =
                                painterResource(
                                    id = BaseR.drawable.bas_arrow_back,
                                ),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            ) // end of CenterAlignedTopAppBar
        }, // end of topBar
    ) { contentPadding ->
        CommentScreenPager(
            pagerState = pagerState,
            pagerScreenValues = pagerScreenValues,
            songInfo = songInfo,
            modifier =
                Modifier
                    // .nestedScroll(scrollBehavior.nestedScrollConnection)
                    // innerPadding takes into account the top and bottom bar
                    .padding(contentPadding),
        )
    }
}

@Composable
fun CommentScreenPager(
    pagerState: PagerState,
    pagerScreenValues: Array<CommentBottomNavigationItems>,
    songInfo: SongModel,
    modifier: Modifier = Modifier,
) {
    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        key = { index -> pagerScreenValues[index] },
    ) { page ->
        when (pagerScreenValues[page]) {
            CommentBottomNavigationItems.COMMENT ->
                CommentScreen(songInfo)

            CommentBottomNavigationItems.NOTE -> Unit
        }
    }
}

@Composable
fun CommentScreen(songInfo: SongModel) {
    Text(text = songInfo.name)
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewCommentScreen() {
    PreviewWrapper {
        CommentMainScreen(
            // widthSize = WindowWidthSizeClass.Compact,
            songInfo =
                SongModel(
                    id = 1,
                    name = "青花",
                    duration = 240000,
                    artists = listOf(ArtistModel(id = 1, name = "周传雄")),
                    album =
                        AlbumModel(
                            id = 1,
                            name = "Album Name",
                            picUrl = "",
                        ),
                    quality = Quality.Jymaster,
                    fee = 8,
                    markText = "VIP",
                ),
        )
    }
}

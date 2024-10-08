package com.leovp.feature_discovery.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.unit.dp
import com.leovp.feature_discovery.R
import com.leovp.module.common.utils.previewInitLog
import com.leovp.ui.theme.AppTheme

/**
 * Author: Michael Leo
 * Date: 2023/9/21 08:37
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    // widthSize: WindowWidthSizeClass,
    onMenuUpAction: () -> Unit,
) {
    // val context = LocalContext.current
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
    Scaffold(
        // contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            val title = stringResource(id = R.string.dis_search_screen_title)
            CenterAlignedTopAppBar(
                // colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                //     containerColor = Color.Cyan
                // ),
                // WindowInsets.waterfall // WindowInsets.displayCutout // or all 0.dp
                // windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
                title = {
                    Text(text = title)
                },
                navigationIcon = {
                    IconButton(onClick = onMenuUpAction) {
                        Icon(
                            painter = painterResource(
                                id = com.leovp.module.common.R.drawable.cmn_arrow_back
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
        SearchScreenContent(
            modifier = Modifier
                // .nestedScroll(scrollBehavior.nestedScrollConnection)
                // innerPadding takes into account the top and bottom bar
                .padding(contentPadding), state = rememberLazyListState()
        )
    }
}

@Composable
fun SearchScreenContent(
    modifier: Modifier = Modifier, state: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = modifier.fillMaxSize(),
        state = state
    ) {
        item {
            Text(text = "Search Screen")
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewSearchScreen() {
    previewInitLog()

    AppTheme {
        SearchScreen(
            // widthSize = WindowWidthSizeClass.Compact,
            onMenuUpAction = {},
        )
    }
}
package com.leovp.androidshowcase.ui.drawerscreens.membercenter

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leovp.androidshowcase.R
import com.leovp.androidshowcase.ui.theme.ImmersiveTheme
import com.leovp.androidshowcase.ui.theme.immersive_sys_ui

/**
 * Author: Michael Leo
 * Date: 2023/7/19 15:02
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemberCenterScreen(
    // widthSize: WindowWidthSizeClass,
    onMenuUpAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    // val context = LocalContext.current
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
    Scaffold(
        // contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            val title = stringResource(id = R.string.app_drawer_member_center)
            CenterAlignedTopAppBar(
                // colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                //     containerColor = Color.Cyan
                // ),
                // WindowInsets.waterfall // WindowInsets.displayCutout // or all 0.dp
                // windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = onMenuUpAction) {
                        Icon(
                            painter = painterResource(id = R.drawable.app_arrow_back),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { contentPadding ->
        MemberCenterContent(
            modifier = Modifier
                // .nestedScroll(scrollBehavior.nestedScrollConnection)
                // innerPadding takes into account the top and bottom bar
                .padding(contentPadding),
            state = rememberLazyListState()
        )
    }
}

@Composable
fun MemberCenterContent(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = modifier,
        state = state
    ) {
        memberCenterScreenContentItems()
    }
}

fun LazyListScope.memberCenterScreenContentItems() {
    item {
        Image(
            painterResource(R.drawable.app_beauty),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = MaterialTheme.shapes.medium),
            alignment = Alignment.TopStart,
            contentScale = ContentScale.FillWidth,
        )
    }
    repeat(100) {
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.padding(2.dp, 16.dp),
                    text = "item: $it"
                )
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewMemberCenterScreen() {
    ImmersiveTheme(systemBarColor = immersive_sys_ui, dynamicColor = false) {
        MemberCenterScreen(
            // widthSize = WindowWidthSizeClass.Compact,
            onMenuUpAction = {}
        )
    }
}
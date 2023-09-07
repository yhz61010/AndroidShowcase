package com.leovp.androidshowcase.ui.tabs.community

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

/**
 * Author: Michael Leo
 * Date: 2023/7/20 08:43
 */

@Composable
fun CommunityScreen(
    // onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Column(modifier) {
            Text(text = "Community Screen")
        }
    }
}

@Preview
@Composable
fun PreviewCommunityScreen() {
    CommunityScreen(/*onRefresh = {}*/)
}
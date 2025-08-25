package com.leovp.my.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.leovp.feature.base.ui.PreviewWrapper

/**
 * Author: Michael Leo
 * Date: 2023/7/20 09:58
 */

@Composable
fun MyScreen(
    // onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Column(modifier) {
            Text(text = "My Screen")
        }
    }
}

@Preview
@Composable
fun PreviewMyScreen() {
    PreviewWrapper {
        MyScreen()
    }
}

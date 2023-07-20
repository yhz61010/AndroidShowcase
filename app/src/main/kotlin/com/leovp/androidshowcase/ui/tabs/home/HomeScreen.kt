package com.leovp.androidshowcase.ui.tabs.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

/**
 * Author: Michael Leo
 * Date: 2023/7/18 15:06
 */

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Surface(modifier = modifier.fillMaxSize()) {
        Column(modifier) {
            Text(text = "Home Screen")
        }
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen()
}
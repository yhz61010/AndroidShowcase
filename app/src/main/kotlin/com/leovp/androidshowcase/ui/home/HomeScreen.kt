package com.leovp.androidshowcase.ui.home

import androidx.compose.foundation.layout.Column
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
    Column(modifier) {
        Text(text = "Home Screen")
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen()
}
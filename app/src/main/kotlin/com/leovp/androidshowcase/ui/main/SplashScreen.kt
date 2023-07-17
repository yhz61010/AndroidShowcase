package com.leovp.androidshowcase.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.leovp.androidshowcase.R
import kotlinx.coroutines.delay

/**
 * Author: Michael Leo
 * Date: 2023/7/17 16:07
 */

private const val SplashWaitTime: Long = 500

@Composable
fun SplashScreen(modifier: Modifier = Modifier, onTimeout: () -> Unit) {
    // Adds composition consistency. Use the value when LaunchedEffect is first called
    val currentOnTimeout by rememberUpdatedState(onTimeout)

    LaunchedEffect(Unit) {
        delay(SplashWaitTime)
        currentOnTimeout()
    }
    Image(
        painterResource(id = R.mipmap.app_ic_launcher_round),
        contentDescription = null,
        modifier
            .fillMaxSize()
            .wrapContentSize()
    )
}

@Preview
@Composable
fun PreviewSplashScreen() {
    SplashScreen {}
}

package com.leovp.androidshowcase.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leovp.androidshowcase.R
import com.leovp.compose.utils.previewInitLog
import kotlinx.coroutines.delay

/**
 * Author: Michael Leo
 * Date: 2023/7/17 16:07
 */

private const val SPLASH_WAIT_TIME: Long = 1000

@Composable
fun SplashScreen(onTimeout: () -> Unit, modifier: Modifier = Modifier) {
    // Adds composition consistency. Use the value when LaunchedEffect is first called
    val currentOnTimeout by rememberUpdatedState(onTimeout)

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = R.mipmap.app_ic_launcher_round),
            contentDescription = null,
            modifier = modifier.requiredSize(80.dp),
        )
    }

    LaunchedEffect(Unit) {
        delay(SPLASH_WAIT_TIME)
        currentOnTimeout()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSplashScreen() {
    previewInitLog()

    SplashScreen(onTimeout = {})
}

package com.leovp.androidshowcase.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController

/**
  * Author: Michael Leo
  * Date: 2023/7/14 13:07
  */

private const val TAG = "MA"

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This app draws behind the system bars, so we want to handle fitting system windows
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val widthSizeClass = calculateWindowSizeClass(this).widthSizeClass
            ShowcaseApp(widthSizeClass)
        }
    }
}

@Composable
fun MainScreen(widthSize: WindowWidthSizeClass, navController: NavHostController) {
    Surface(
        modifier = Modifier.windowInsetsPadding(
            WindowInsets.navigationBars.only(WindowInsetsSides.Start + WindowInsetsSides.End)
        ), color = MaterialTheme.colorScheme.primary
    ) {
        val transitionState = remember { MutableTransitionState(mutableStateOf(SplashState.Shown).value) }
        val transition = updateTransition(transitionState, label = "splashTransition")
        val splashAlpha by transition.animateFloat(
            transitionSpec = { tween(durationMillis = 100) }, label = "splashAlpha"
        ) {
            if (it == SplashState.Shown) 1f else 0f
        }
        val contentAlpha by transition.animateFloat(
            transitionSpec = { tween(durationMillis = 300) }, label = "contentAlpha"
        ) {
            if (it == SplashState.Shown) 0f else 1f
        }
        // val contentTopPadding by transition.animateDp(
        //     transitionSpec = { spring(stiffness = Spring.StiffnessLow) }, label = "contentTopPadding"
        // ) {
        //     if (it == SplashState.Shown) 100.dp else 0.dp
        // }

        Box {
            SplashScreen(modifier = Modifier.alpha(splashAlpha), onTimeout = {
                transitionState.targetState = SplashState.Completed
            })

            AppHome(
                widthSize = widthSize, modifier = Modifier.alpha(contentAlpha), navController = navController
            )
        }
    }
}

enum class SplashState { Shown, Completed }
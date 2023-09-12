package com.leovp.androidshowcase.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.leovp.androidshowcase.Screen
import com.leovp.androidshowcase.addAppDrawerGraph
import com.leovp.androidshowcase.addAppMainGraph
import com.leovp.androidshowcase.rememberNavigationActions
import com.leovp.module.common.GlobalConst
import com.leovp.module.common.http.RequestUtil

/**
 * Author: Michael Leo
 * Date: 2023/7/14 13:07
 */

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        RequestUtil.initNetEngine(
            baseUrl = GlobalConst.API_BASE_URL
        )

        // This app draws behind the system bars, so we want to handle fitting system windows
        WindowCompat.setDecorFitsSystemWindows(
            window,
            false
        )

        setContent {
            val widthSizeClass = calculateWindowSizeClass(this).widthSizeClass
            ShowcaseApp(widthSizeClass)
        }
    }
}


@Composable
fun ShowcaseApp(widthSizeClass: WindowWidthSizeClass) {
    val navController = rememberNavController()
    val navigationActions = rememberNavigationActions(navController = navController)
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = Modifier
    ) {
        addAppMainGraph(
            widthSizeClass = widthSizeClass,
            navigationActions = navigationActions
        )

        addAppDrawerGraph(
            // widthSizeClass = widthSizeClass,
            navigationActions = navigationActions
        )
    }
}
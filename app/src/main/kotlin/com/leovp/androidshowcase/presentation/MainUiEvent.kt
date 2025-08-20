@file:Suppress("unused")

package com.leovp.androidshowcase.presentation

import android.content.Context
import androidx.compose.material3.DrawerState
import com.leovp.android.exts.toast
import com.leovp.androidshowcase.ui.AppNavigationActions
import com.leovp.androidshowcase.ui.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Author: Michael Leo
 * Date: 2025/8/20 17:36
 */

fun createHomeTopAppBarEventHandler(
    coroutineScope: CoroutineScope,
    drawerState: DrawerState,
    context: Context,
    @Suppress("unused") navigationActions: AppNavigationActions,
): (MainViewModel.MainUiEvent.TopAppBarEvent) -> Unit =
    { event ->
        when (event) {
            MainViewModel.MainUiEvent.TopAppBarEvent.MenuClick -> {
                coroutineScope.launch { drawerState.open() }
            }

            MainViewModel.MainUiEvent.TopAppBarEvent.RecordingClick -> {
                context.toast("Recording is not yet implemented.")
            }
        }
    }

fun createHomeTopAppBarContentEventHandler(
    context: Context,
    navigationActions: AppNavigationActions,
): (MainViewModel.MainUiEvent.SearchEvent) -> Unit =
    { event ->
        when (event) {
            MainViewModel.MainUiEvent.SearchEvent.SearchClick -> {
                navigationActions.navigate(Screen.SearchScreen.route)
            }

            MainViewModel.MainUiEvent.SearchEvent.ScanClick -> {
                context.toast("Scan is not yet implemented.")
            }
        }
    }

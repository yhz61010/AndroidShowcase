package com.leovp.feature.base.event.composable

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.leovp.compose.composable.event.base.GenericEventHandler
import com.leovp.compose.composable.nav.AppNavigation
import com.leovp.feature.base.event.composable.base.EventDialogContent
import com.leovp.feature.base.event.composable.base.EventLoadingDialogContent
import com.leovp.mvvm.event.base.UiEvent
import kotlinx.coroutines.flow.Flow

/**
 * Author: Michael Leo
 * Date: 2025/8/21 10:43
 */
@Composable
fun CustomEventHandler(
    events: Flow<UiEvent>,
    navController: AppNavigation? = null,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    GenericEventHandler(
        events = events,
        navController = navController,
        snackbarHostState = snackbarHostState,
        loadingDialogContent = { EventLoadingDialogContent() },
        // hideLoadingContent = null,
        dialogContent = { dialogState ->
            EventDialogContent(dialogState)
        },
    )
}

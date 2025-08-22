package com.leovp.feature.base.event.composable

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.leovp.feature.base.event.UiEvent
import com.leovp.feature.base.event.composable.base.EventDialogContent
import com.leovp.feature.base.event.composable.base.EventLoadingDialogContent
import com.leovp.feature.base.ui.AppNavigationActions
import kotlinx.coroutines.flow.Flow

/**
 * Author: Michael Leo
 * Date: 2025/8/21 10:43
 */
@Composable
fun EventHandler(
    events: Flow<UiEvent>,
    navController: AppNavigationActions? = null,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    GenericEventHandler(
        events = events,
        navController = navController,
        snackbarHostState = snackbarHostState,
        showLoadingContent = { EventLoadingDialogContent() },
        hideLoadingContent = null,
        dialogContent = { dialogState, dialog ->
            EventDialogContent(dialogState, dialog)
        },
    )
}

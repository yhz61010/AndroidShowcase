@file:Suppress("unused")

package com.leovp.feature.base.event.composable

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.leovp.android.exts.toast
import com.leovp.feature.base.event.ToastDuration
import com.leovp.feature.base.event.UiEvent
import kotlinx.coroutines.flow.Flow

/**
 * Author: Michael Leo
 * Date: 2025/8/22 13:29
 */
@Composable
fun GenericEventHandler(
    events: Flow<UiEvent>,
    navController: NavController? = null,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    showLoadingContent: @Composable (() -> Unit)? = null,
    hideLoadingContent: @Composable (() -> Unit)? = null,
    dialogContent: @Composable (
        (
        dialogState: MutableState<UiEvent.ShowDialog?>,
        dialog: UiEvent.ShowDialog,
    ) -> Unit
    )? = null
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var showLoadingDialog by remember { mutableStateOf(false) }
    val dialogState = remember { mutableStateOf<UiEvent.ShowDialog?>(null) }

    LaunchedEffect(events, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            events.collect { event ->
                when (event) {
                    is UiEvent.ShowToast -> {
                        val longDuration = ToastDuration.LONG == event.duration
                        context.toast(msg = event.message, longDuration = longDuration)
                    }

                    is UiEvent.ShowSnackbar -> {
                        snackbarHostState
                            .showSnackbar(
                                message = event.message,
                                actionLabel = event.actionLabel,
                                duration = SnackbarDuration.Short,
                            ).let { result ->
                                if (result == SnackbarResult.ActionPerformed) {
                                    event.onAction?.invoke()
                                }
                            }
                    }

                    is UiEvent.Navigate -> {
                        navController?.navigate(event.route)
                    }

                    UiEvent.NavigateBack -> {
                        navController?.popBackStack()
                    }

                    UiEvent.ShowLoading -> {
                        showLoadingDialog = true
                    }

                    UiEvent.HideLoading -> {
                        showLoadingDialog = false
                    }

                    is UiEvent.ShowDialog -> {
                        dialogState.value = event
                    }
                }
            }
        }
    }

    // Loading Dialog
    if (showLoadingDialog) {
        showLoadingContent?.invoke()
    } else {
        hideLoadingContent?.invoke()
    }

    // Custom Dialog
    dialogState.value?.let { dialog ->
        dialogContent?.invoke(dialogState, dialog)
    }
}

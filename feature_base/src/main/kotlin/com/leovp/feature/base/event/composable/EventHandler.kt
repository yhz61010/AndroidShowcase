package com.leovp.feature.base.event.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.leovp.android.exts.toast
import com.leovp.feature.base.R
import com.leovp.feature.base.event.ToastDuration
import com.leovp.feature.base.event.UiEvent
import kotlinx.coroutines.flow.Flow

/**
 * Author: Michael Leo
 * Date: 2025/8/21 10:43
 */
@Composable
fun EventHandler(
    events: Flow<UiEvent>,
    navController: NavController? = null,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
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
        LoadingDialogContent()
    }

    // Custom Dialog
    dialogState.value?.let { dialog ->
        DialogContent(dialogState, dialog)
    }
}

@Composable
private fun LoadingDialogContent() {
    AlertDialog(
        onDismissRequest = { },
        title = { Text(stringResource(R.string.bas_dlg_loading_title)) },
        text = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
                Text(stringResource(R.string.bas_dlg_loading_text))
            }
        },
        confirmButton = { },
    )
}

@Composable
private fun DialogContent(
    dialogState: MutableState<UiEvent.ShowDialog?>,
    dialog: UiEvent.ShowDialog,
) {
    AlertDialog(
        onDismissRequest = { dialogState.value = null },
        title = { Text(dialog.title) },
        text = { Text(dialog.message) },
        confirmButton = {
            TextButton(
                onClick = {
                    dialog.onPositive()
                    dialogState.value = null
                },
            ) {
                Text(dialog.positiveButton)
            }
        },
        dismissButton =
            dialog.negativeButton?.let {
                {
                    TextButton(
                        onClick = {
                            dialog.onNegative()
                            dialogState.value = null
                        },
                    ) {
                        Text(it)
                    }
                }
            },
    )
}

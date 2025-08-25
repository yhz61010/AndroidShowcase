package com.leovp.feature.base.event.composable.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.leovp.compose.composable.event.UiEvent
import com.leovp.feature.base.R

/**
 * Author: Michael Leo
 * Date: 2025/8/22 13:23
 */

@Composable
fun EventLoadingDialogContent() {
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
fun EventDialogContent(
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

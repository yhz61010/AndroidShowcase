package com.leovp.feature.base.event.composable.base

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.leovp.compose.composable.event.UiEvent
import com.leovp.feature.base.R
import com.leovp.feature.base.ui.PreviewWrapper

/**
 * Author: Michael Leo
 * Date: 2025/8/22 13:23
 */

@Composable
fun EventLoadingDialogContent() {
    Column(
        modifier = Modifier
            .background(Color.Transparent)
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Dialog(
            onDismissRequest = { },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                // Use custom width
                usePlatformDefaultWidth = false,
                decorFitsSystemWindows = false,
            )
        ) {
            // The following lines will remove the dimmed background.
            val window = (LocalView.current.parent as DialogWindowProvider).window
            SideEffect {
                window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            }
            LoadingDialogContent()
        }
    }
}

@Composable
private fun LoadingDialogContent() {
    // Transparent background
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { /* Click on background. */ },
        contentAlignment = Alignment.Center
    ) {
        // AlertDialog content
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.5f)
                // .size(112.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { /* Prevent event popup. */ },
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors().copy(
                containerColor = Color.White
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Icon(
                //     Icons.Default.Info,
                //     contentDescription = null,
                //     tint = Color.Blue,
                //     modifier = Modifier.size(48.dp)
                // )
                CircularProgressIndicator(
                    modifier = Modifier.size(64.dp),
                    color = MaterialTheme.colorScheme.primary,
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.bas_dlg_loading_title),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.bas_dlg_loading_text),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                )

                // Spacer(modifier = Modifier.height(24.dp))
                // Row(
                //     modifier = Modifier.fillMaxWidth(),
                //     horizontalArrangement = Arrangement.spacedBy(12.dp)
                // ) {
                //     OutlinedButton(
                //         onClick = { },
                //         modifier = Modifier.weight(1f)
                //     ) {
                //         Text("Cancel")
                //     }
                //
                //     Button(
                //         onClick = { },
                //         modifier = Modifier.weight(1f)
                //     ) {
                //         Text("OK")
                //     }
                // }
            }
        }
    }
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

// @Composable
// fun DialogContent() {
//
// }

@Preview(
    uiMode = UI_MODE_NIGHT_NO,
    showBackground = true,
    backgroundColor = 0xFFF0EAE2,
)
@Composable
fun PreviewEventLoadingDialogContent() {
    PreviewWrapper {
        LoadingDialogContent()
    }
}

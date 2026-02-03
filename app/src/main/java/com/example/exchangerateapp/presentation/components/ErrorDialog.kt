package com.example.exchangerateapp.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.exchangerateapp.R

@Composable
fun ErrorDialog(
    icon: Int,
    message: String,
    onRetry: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {},
        icon = {
            Icon(painterResource(icon), null)
        },
        title = { Text(stringResource(R.string.error_title)) },
        text = { Text(message) },
        confirmButton = {
            TextButton(onClick = onRetry) {
                Text(stringResource(R.string.retry))
            }
        }
    )
}

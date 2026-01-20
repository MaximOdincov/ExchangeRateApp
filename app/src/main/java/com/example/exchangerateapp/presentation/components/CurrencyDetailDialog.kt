package com.example.exchangerateapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.exchangerateapp.R
import com.example.exchangerateapp.presentation.models.CurrencyUI

@Composable
fun CurrencyDetailsDialog(
    currencyUi: CurrencyUI,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(16.dp),
        title = {
            Text(
                text = currencyUi.currency.name,
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = stringResource(R.string.label_code), style = MaterialTheme.typography.labelLarge)
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(text = currencyUi.currency.charCode, style = MaterialTheme.typography.bodyLarge)
                }

                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = stringResource(R.string.label_current), style = MaterialTheme.typography.labelLarge)
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(text = currencyUi.currency.value.toString(), style = MaterialTheme.typography.bodyLarge)
                }

                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = stringResource(R.string.label_change), style = MaterialTheme.typography.labelLarge)
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(text = currencyUi.changePercent, style = MaterialTheme.typography.bodyLarge, color = when (currencyUi.changeColor) {
                        com.example.exchangerateapp.presentation.models.ChangeColor.POSITIVE -> androidx.compose.ui.graphics.Color(0xFF2E7D32)
                        com.example.exchangerateapp.presentation.models.ChangeColor.NEGATIVE -> androidx.compose.ui.graphics.Color(0xFFD32F2F)
                        else -> androidx.compose.ui.graphics.Color.Gray
                    })
                }

                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = stringResource(R.string.label_previous), style = MaterialTheme.typography.labelLarge)
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(text = currencyUi.currency.previous.toString(), style = MaterialTheme.typography.bodyLarge)
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.close))
            }
        }
    )
}
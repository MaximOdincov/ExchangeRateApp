package com.example.exchangerateapp.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.exchangerateapp.R

@Composable
fun LastUpdateBanner(
    date: String,
    isDataNew: Boolean
) {
    if (!isDataNew) {
        androidx.compose.material3.Text(
            text = stringResource(
                R.string.data_may_be_outdated,
                date
            ),
            style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
            color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )
    }
}
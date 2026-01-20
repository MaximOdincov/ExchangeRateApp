package com.example.exchangerateapp.presentation.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.exchangerateapp.R
import com.example.exchangerateapp.domain.entities.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExchangeToolbar(
    currentTheme: AppTheme,
    onThemeClick: (AppTheme) -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.toolbar_title),
                style = MaterialTheme.typography.titleLarge
            )
        },
        actions = {
            IconButton(
                onClick = {
                    val next = when (currentTheme) {
                        AppTheme.LIGHT -> AppTheme.DARK
                        AppTheme.DARK -> AppTheme.LIGHT
                        AppTheme.SYSTEM -> AppTheme.DARK
                    }
                    onThemeClick(next)
                }
            ) {
                Icon(
                    painter = painterResource(
                        if (currentTheme == AppTheme.LIGHT)
                            R.drawable.day_theme_icon
                        else
                            R.drawable.dark_theme_icon
                    ),
                    contentDescription = null
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.shadow(8.dp)
    )
}

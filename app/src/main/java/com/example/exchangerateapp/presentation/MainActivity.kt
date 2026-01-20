package com.example.exchangerateapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel
import androidx.activity.enableEdgeToEdge
import com.example.exchangerateapp.presentation.components.MainScreen
import com.example.exchangerateapp.presentation.ui.theme.ExchangeRateAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val vm: MainViewModel = koinViewModel()
            val theme by vm.themeFlow.collectAsState()

            ExchangeRateAppTheme(darkTheme = when (theme) {
                com.example.exchangerateapp.domain.entities.AppTheme.DARK -> true
                com.example.exchangerateapp.domain.entities.AppTheme.LIGHT -> false
                com.example.exchangerateapp.domain.entities.AppTheme.SYSTEM -> androidx.compose.foundation.isSystemInDarkTheme()
            }) {
                MainScreen()
            }
        }
    }
}
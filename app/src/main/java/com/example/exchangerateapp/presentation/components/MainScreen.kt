package com.example.exchangerateapp.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Modifier
import com.example.exchangerateapp.R
import com.example.exchangerateapp.presentation.ErrorType
import com.example.exchangerateapp.presentation.MainViewModel
import com.example.exchangerateapp.presentation.State
import com.example.exchangerateapp.presentation.UiEvent
import com.example.exchangerateapp.presentation.models.CurrencyUI
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val theme by viewModel.themeFlow.collectAsState()
    val query by viewModel.query.collectAsState()

    var selectedCurrency by remember { mutableStateOf<CurrencyUI?>(null) }

    val snackbarHostState = remember { SnackbarHostState() }
    var errorDialog by remember { mutableStateOf<ErrorType?>(null) }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is UiEvent.ShowError -> {
                    when (event.type) {
                        ErrorType.NoInternet,
                        ErrorType.Server -> {
                            errorDialog = event.type
                        }

                        else -> {
                                    snackbarHostState.showSnackbar(
                                        message = "Произошла ошибка"
                                    )
                                }
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            ExchangeToolbar(
                currentTheme = theme,
                onThemeClick = viewModel::setTheme
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        when (state) {
            State.Loading -> LoadingContent()

            is State.Data -> {
                val data = state as State.Data

                Column(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                ) {
                    SearchField(
                        query = query,
                        onQueryChange = viewModel::onSearchQueryChange
                    )

                    val isRefreshing by viewModel.isRefreshing.collectAsState()

                    CurrencyList(
                        favourites = data.favouriteCurrencies,
                        currencies = data.currencies,
                        onFavouriteClick = viewModel::onFavouriteClick,
                        onRefresh = viewModel::refresh,
                        onItemClick = { selectedCurrency = it },
                        isRefreshing = isRefreshing
                    )
                }
            }
        }
    }

    errorDialog?.let { error ->
        when (error) {
            ErrorType.NoInternet -> {
                ErrorDialog(
                    icon = R.drawable.no_internet_icon,
                    message = "Проверьте подключение к интернету",
                    onRetry = {
                        errorDialog = null
                        viewModel.refresh()
                    },
                    onDismiss = {
                        errorDialog = null
                    }
                )
            }

            ErrorType.Server -> {
                ErrorDialog(
                    icon = R.drawable.server_error_icon,
                    message = "Сервер недоступен. Попробуйте позже.",
                    onRetry = {
                        errorDialog = null
                        viewModel.refresh()
                    },
                    onDismiss = {
                        errorDialog = null
                    }
                )
            }
            else -> Unit
        }
    }

    selectedCurrency?.let { currency ->
        CurrencyDetailsDialog(currencyUi = currency, onDismiss = { selectedCurrency = null })
    }
}
@Composable
private fun LoadingContent(){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}
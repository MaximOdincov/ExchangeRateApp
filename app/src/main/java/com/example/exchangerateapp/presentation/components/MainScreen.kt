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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.exchangerateapp.R
import com.example.exchangerateapp.presentation.ErrorType
import com.example.exchangerateapp.presentation.MainViewModel
import com.example.exchangerateapp.presentation.State
import com.example.exchangerateapp.presentation.models.CurrencyUI
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val theme by viewModel.themeFlow.collectAsState()
    val query by viewModel.query.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    var selectedCurrency by remember { mutableStateOf<CurrencyUI?>(null) }
    var errorDialog by remember { mutableStateOf<ErrorType?>(null) }

    val snackbarHostState = remember { SnackbarHostState() }
    val outdatedSnackbarMessage = stringResource(R.string.check_internet_connection)


    Scaffold(
        topBar = {
            ExchangeToolbar(
                currentTheme = theme,
                onThemeClick = viewModel::setTheme
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        when (val currentState = state) {
            State.Loading -> LoadingContent()

            is State.Data -> {
                if (!currentState.isDataNew) {
                    LaunchedEffect(currentState.date) {
                        snackbarHostState.showSnackbar(
                            message = outdatedSnackbarMessage
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                ) {
                    LastUpdateBanner(
                        date = currentState.date,
                        isDataNew = currentState.isDataNew
                    )

                    SearchField(
                        query = query,
                        onQueryChange = viewModel::onSearchQueryChange,
                        onTrailingButtonClick = viewModel::clearQuery
                    )

                    CurrencyList(
                        favourites = currentState.favouriteCurrencies,
                        currencies = currentState.currencies,
                        onFavouriteClick = viewModel::onFavouriteClick,
                        onRefresh = viewModel::refresh,
                        onItemClick = { selectedCurrency = it },
                        isRefreshing = isRefreshing
                    )
                }
            }

            is State.Error -> {
                if (errorDialog == null) {
                    errorDialog = currentState.type
                }
            }
        }
    }

    errorDialog?.let { error ->
        when (error) {
            ErrorType.Empty -> {
                ErrorDialog(
                    icon = R.drawable.no_internet_icon,
                    message = stringResource(R.string.no_data),
                    onRetry = {
                        viewModel.refresh()
                        errorDialog = null
                    }
                )
            }

            else -> {
                ErrorDialog(
                    icon = R.drawable.server_error_icon,
                    message = stringResource(R.string.service_unavailable),
                    onRetry = {
                        viewModel.refresh()
                        errorDialog = null
                    }
                )
            }
        }
    }

    selectedCurrency?.let { currency ->
        CurrencyDetailsDialog(
            currencyUi = currency,
            onDismiss = { selectedCurrency = null }
        )
    }
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
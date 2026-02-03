package com.example.exchangerateapp.presentation

import com.example.exchangerateapp.presentation.models.CurrencyUI

sealed class State {
    object Loading: State()
    data class Data(
        val favouriteCurrencies: List<CurrencyUI>,
        val currencies:List<CurrencyUI>,
        val date: String,
        val isDataNew: Boolean
    ): State()
    data class Error(
        val type: ErrorType
    ) : State()
}
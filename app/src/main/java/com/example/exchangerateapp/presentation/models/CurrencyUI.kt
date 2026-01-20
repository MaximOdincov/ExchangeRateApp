package com.example.exchangerateapp.presentation.models

import com.example.exchangerateapp.domain.entities.Currency

data class CurrencyUI(
    val currency: Currency,
    val isFavourite: Boolean,
    val changePercent: String,
    val changeColor: ChangeColor
)

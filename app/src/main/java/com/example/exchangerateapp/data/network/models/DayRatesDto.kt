package com.example.exchangerateapp.data.network.models

import com.google.gson.annotations.SerializedName

data class DayRatesDto(
    @SerializedName("Date") val date: String,
    @SerializedName("PreviousDate") val previousDate: String,
    @SerializedName("Valute") val value: Map<String, CurrencyDto>
)
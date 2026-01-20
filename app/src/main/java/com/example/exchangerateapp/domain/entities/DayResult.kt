package com.example.exchangerateapp.domain.entities


data class DayResult(
    val date: Long,
    val currencies: List<Currency>
)

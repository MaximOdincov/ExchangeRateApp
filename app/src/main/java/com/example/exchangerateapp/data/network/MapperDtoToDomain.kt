package com.example.exchangerateapp.data.network

import com.example.exchangerateapp.data.network.models.CurrencyDto
import com.example.exchangerateapp.data.network.models.DayRatesDto
import com.example.exchangerateapp.domain.entities.Currency
import com.example.exchangerateapp.domain.entities.DayResult
import java.time.ZonedDateTime

fun DayRatesDto.toDomain(): DayResult =
    DayResult(
        date = ZonedDateTime.parse(date).toInstant().toEpochMilli(),
        currencies = value.values
            .map{it.toDomain()}
            .sortedBy { it.name }
    )

fun CurrencyDto.toDomain() =
    Currency(
        id = id,
        numCode = numCode,
        charCode = charCode,
        nominal = nominal,
        name = name,
        value = value,
        previous = previous
    )
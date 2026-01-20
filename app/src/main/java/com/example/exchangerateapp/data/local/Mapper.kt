package com.example.exchangerateapp.data.local

import com.example.exchangerateapp.data.local.entities.CurrencyDb
import com.example.exchangerateapp.data.local.entities.DayInfoDb
import com.example.exchangerateapp.domain.entities.Currency
import com.example.exchangerateapp.domain.entities.DayResult

fun DayInfoDb.toDomain(currencies: List<CurrencyDb>) = DayResult(
        date = date,
        currencies = currencies.map{
            it.toDomain()
        }
    )

fun CurrencyDb.toDomain() = Currency(
        id = id,
        numCode = numCode,
        charCode = charCode,
        nominal = nominal,
        name = name,
        value = value,
        previous = previous
    )

fun DayResult.toDb() = DayInfoDb(
    date = date
)

fun List<Currency>.toDb() = map{it.toDb()}

fun Currency.toDb() = CurrencyDb(
    id = id,
    numCode = numCode,
    charCode = charCode,
    nominal = nominal,
    name = name,
    value = value,
    previous = previous
)

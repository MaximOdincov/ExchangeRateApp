package com.example.exchangerateapp.presentation

import android.database.sqlite.SQLiteException
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.exchangerateapp.domain.entities.Currency
import com.example.exchangerateapp.presentation.models.ChangeColor
import com.example.exchangerateapp.presentation.models.CurrencyUI
import kotlinx.serialization.SerializationException
import java.io.IOException
import java.net.ConnectException
import retrofit2.HttpException
import java.math.BigDecimal
import java.math.RoundingMode
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Throwable.toErrorType(): ErrorType =
    when (this) {
        is UnknownHostException, is SocketTimeoutException, is IOException, is ConnectException -> ErrorType.NoInternet

        is HttpException -> ErrorType.Server

        is SerializationException -> ErrorType.Parsing

        is SQLiteException -> ErrorType.Database

        else -> ErrorType.Unknown
    }

fun Long.toDateString(): String {
    val instant = Instant.ofEpochMilli(this)
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        .withZone(ZoneId.systemDefault())
    return formatter.format(instant)
}

fun Currency.toUi(isFavourite: Boolean): CurrencyUI {
    val percent = if (previous == BigDecimal.ZERO) BigDecimal.ZERO
    else {
        value.subtract(previous)
            .divide(previous, 4, RoundingMode.HALF_DOWN)
            .multiply(BigDecimal(100))
    }

    val color = when {
        percent > BigDecimal.ZERO -> ChangeColor.POSITIVE
        percent < BigDecimal.ZERO -> ChangeColor.NEGATIVE
        else -> ChangeColor.NEUTRAL
    }

    return CurrencyUI(
        currency = this,
        isFavourite = isFavourite,
        changePercent = "${percent.setScale(2)}%",
        changeColor = color
    )
}
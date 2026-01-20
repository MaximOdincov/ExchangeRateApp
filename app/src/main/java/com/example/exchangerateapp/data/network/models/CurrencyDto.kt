package com.example.exchangerateapp.data.network.models
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class CurrencyDto(
    @SerializedName("ID") val id: String,
    @SerializedName("NumCode") val numCode: String,
    @SerializedName("CharCode") val charCode: String,
    @SerializedName("Nominal") val nominal: Int,
    @SerializedName("Name") val name: String,
    @SerializedName("Value") val value: BigDecimal,
    @SerializedName("Previous") val previous: BigDecimal
)

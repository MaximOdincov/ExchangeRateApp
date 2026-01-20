package com.example.exchangerateapp.data.network

import com.example.exchangerateapp.data.network.models.DayRatesDto
import retrofit2.http.GET

interface ApiService {

    @GET(DAILY_JSON_LINK)
    suspend fun getDailyRates(): DayRatesDto

    companion object{
        private const val DAILY_JSON_LINK = "daily_json.js"
    }
}
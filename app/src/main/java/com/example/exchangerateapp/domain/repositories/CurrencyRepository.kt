package com.example.exchangerateapp.domain.repositories

import com.example.exchangerateapp.domain.entities.DayResult
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    suspend fun getCurrentData(): Result<DayResult?>
    suspend fun updateCurrentData(): Result<Unit>
    fun observeCurrentData(): Flow<DayResult>
}
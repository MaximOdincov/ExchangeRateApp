package com.example.exchangerateapp.domain.repositories

import com.example.exchangerateapp.domain.entities.DayResult
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    suspend fun getLocalData(): Result<DayResult?>
    suspend fun getRemoteData(): Result<DayResult>
    suspend fun saveData(data: DayResult): Result<Unit>
    fun observeLocalData(): Flow<DayResult>
}
package com.example.exchangerateapp.domain.usecases

import com.example.exchangerateapp.domain.entities.DayResult
import com.example.exchangerateapp.domain.repositories.CurrencyRepository
import kotlinx.coroutines.flow.Flow

class ObserveCurrentDataUseCase(
    private val repository: CurrencyRepository){
    operator fun invoke(): Flow<DayResult> = repository.observeLocalData()
}
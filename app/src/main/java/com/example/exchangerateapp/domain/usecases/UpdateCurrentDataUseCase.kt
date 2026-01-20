package com.example.exchangerateapp.domain.usecases

import com.example.exchangerateapp.domain.repositories.CurrencyRepository

class UpdateCurrentDataUseCase(
    private val repository: CurrencyRepository
){
    suspend operator fun invoke(): Result<Unit> = repository.updateCurrentData()
}
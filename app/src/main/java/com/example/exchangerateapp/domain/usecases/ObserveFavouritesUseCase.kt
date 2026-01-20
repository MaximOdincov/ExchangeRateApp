package com.example.exchangerateapp.domain.usecases

import com.example.exchangerateapp.domain.repositories.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow

class ObserveFavouritesUseCase(
    private val repository: UserPreferencesRepository
    ) {
        operator fun invoke(): Flow<Set<String>> = repository.observeFavoriteCurrencies()
    }

package com.example.exchangerateapp.domain.usecases

import com.example.exchangerateapp.domain.entities.AppTheme
import com.example.exchangerateapp.domain.repositories.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow

class ObserveThemeUseCase(
    private val repository: UserPreferencesRepository
) {
    operator fun invoke(): Flow<AppTheme> = repository.observeTheme()
}
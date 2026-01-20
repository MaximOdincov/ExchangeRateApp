package com.example.exchangerateapp.domain.usecases

import com.example.exchangerateapp.domain.entities.AppTheme
import com.example.exchangerateapp.domain.repositories.UserPreferencesRepository

class SetThemeUseCase(
    private val repository: UserPreferencesRepository
) {
    suspend operator fun invoke(theme: AppTheme) = repository.setTheme(theme)
}
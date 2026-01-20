package com.example.exchangerateapp.domain.usecases

import com.example.exchangerateapp.domain.repositories.UserPreferencesRepository

class AddCurrencyToFavouritesUseCase(
    private val repository: UserPreferencesRepository
) {
    suspend operator fun invoke(currencyId: String) = repository.addToFavorites(currencyId)
}
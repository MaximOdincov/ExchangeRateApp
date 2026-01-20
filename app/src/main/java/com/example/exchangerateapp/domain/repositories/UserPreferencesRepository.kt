package com.example.exchangerateapp.domain.repositories

import com.example.exchangerateapp.domain.entities.AppTheme
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    fun observeFavoriteCurrencies(): Flow<Set<String>>

    suspend fun addToFavorites(currencyId: String)

    suspend fun removeFromFavorites(currencyId: String)

    fun observeTheme(): Flow<AppTheme>

    suspend fun setTheme(theme: AppTheme)
}
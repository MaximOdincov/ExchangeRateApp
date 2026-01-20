package com.example.exchangerateapp.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.example.exchangerateapp.domain.entities.AppTheme
import com.example.exchangerateapp.domain.repositories.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesRepositoryImpl(
    private val dataStore: DataStore<Preferences>
): UserPreferencesRepository {

    companion object {
        private val FAVORITES =
            stringSetPreferencesKey("favorite_currencies")

        private val THEME =
            stringPreferencesKey("app_theme")
    }


    override fun observeFavoriteCurrencies(): Flow<Set<String>> =
        dataStore.data.map{ prefs ->
            prefs[FAVORITES] ?: emptySet()
        }

    override suspend fun addToFavorites(currencyId: String) {
        dataStore.edit { prefs ->
            val current = prefs[FAVORITES] ?: emptySet()
            prefs[FAVORITES] = current + currencyId
        }
    }

    override suspend fun removeFromFavorites(currencyId: String) {
        dataStore.edit { prefs ->
            val current = prefs[FAVORITES] ?: emptySet()
            prefs[FAVORITES] = current - currencyId
        }
    }

    override fun observeTheme(): Flow<AppTheme> =
        dataStore.data.map { prefs ->
            prefs[THEME]?.let {
                runCatching { AppTheme.valueOf(it) }.getOrNull()
            } ?: AppTheme.SYSTEM
        }

    override suspend fun setTheme(theme: AppTheme) {
        dataStore.edit { prefs ->
            prefs[THEME] = theme.name
        }
    }
}
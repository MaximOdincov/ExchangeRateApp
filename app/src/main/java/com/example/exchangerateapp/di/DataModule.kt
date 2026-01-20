package com.example.exchangerateapp.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.example.exchangerateapp.data.local.CurrencyDao
import com.example.exchangerateapp.data.local.AppDatabase
import com.example.exchangerateapp.data.network.ApiFactory
import com.example.exchangerateapp.data.network.ApiService
import com.example.exchangerateapp.data.repositories.CurrencyRepositoryImpl
import com.example.exchangerateapp.data.repositories.UserPreferencesRepositoryImpl
import com.example.exchangerateapp.domain.repositories.CurrencyRepository
import com.example.exchangerateapp.domain.repositories.UserPreferencesRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single<ApiService> {
        ApiFactory.create()
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "exchange_db"
        )
            .build()
    }

    single<CurrencyDao> {
        get<AppDatabase>().currencyDao()
    }

    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create(
            produceFile = {
                androidContext().preferencesDataStoreFile("settings")
            }
        )
    }


    single<CurrencyRepository> {
        CurrencyRepositoryImpl(
            dao = get(),
            api = get()
        )
    }
    
    single<UserPreferencesRepository>{
        UserPreferencesRepositoryImpl(
            dataStore = get()
        )
    }
}
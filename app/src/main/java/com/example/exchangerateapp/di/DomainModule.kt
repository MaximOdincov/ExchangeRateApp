package com.example.exchangerateapp.di

import com.example.exchangerateapp.domain.usecases.AddCurrencyToFavouritesUseCase
import com.example.exchangerateapp.domain.usecases.ObserveCurrentDataUseCase
import com.example.exchangerateapp.domain.usecases.ObserveFavouritesUseCase
import com.example.exchangerateapp.domain.usecases.ObserveThemeUseCase
import com.example.exchangerateapp.domain.usecases.RemoveCurrencyFromFavouritesUseCase
import com.example.exchangerateapp.domain.usecases.SetThemeUseCase
import com.example.exchangerateapp.domain.usecases.UpdateCurrentDataUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { AddCurrencyToFavouritesUseCase(get()) }
    factory { ObserveCurrentDataUseCase(get()) }
    factory { ObserveFavouritesUseCase(get()) }
    factory { ObserveThemeUseCase(get()) }
    factory { RemoveCurrencyFromFavouritesUseCase(get()) }
    factory { SetThemeUseCase(get()) }
    factory { UpdateCurrentDataUseCase(get()) }
}
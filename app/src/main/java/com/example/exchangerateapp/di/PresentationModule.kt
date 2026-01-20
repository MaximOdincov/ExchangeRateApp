package com.example.exchangerateapp.di

import com.example.exchangerateapp.presentation.MainViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel{
        MainViewModel(
            observeCurrentDataUseCase = get(),
            observeFavouritesUseCase = get(),
            observeThemeUseCase = get(),
            addCurrencyToFavouritesUseCase = get(),
            removeCurrencyFromFavouritesUseCase = get(),
            setThemeUseCase = get(),
            updateCurrentDataUseCase = get()
        )
    }
}
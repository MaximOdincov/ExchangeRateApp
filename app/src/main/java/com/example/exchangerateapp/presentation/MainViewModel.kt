package com.example.exchangerateapp.presentation

import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangerateapp.domain.entities.AppTheme
import com.example.exchangerateapp.domain.entities.Currency
import com.example.exchangerateapp.domain.usecases.AddCurrencyToFavouritesUseCase
import com.example.exchangerateapp.domain.usecases.ObserveCurrentDataUseCase
import com.example.exchangerateapp.domain.usecases.ObserveFavouritesUseCase
import com.example.exchangerateapp.domain.usecases.ObserveThemeUseCase
import com.example.exchangerateapp.domain.usecases.RemoveCurrencyFromFavouritesUseCase
import com.example.exchangerateapp.domain.usecases.SetThemeUseCase
import com.example.exchangerateapp.domain.usecases.UpdateCurrentDataUseCase
import com.example.exchangerateapp.presentation.models.ChangeColor
import com.example.exchangerateapp.presentation.models.CurrencyUI
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    observeCurrentDataUseCase: ObserveCurrentDataUseCase,
    observeFavouritesUseCase: ObserveFavouritesUseCase,
    observeThemeUseCase: ObserveThemeUseCase,
    private val addCurrencyToFavouritesUseCase: AddCurrencyToFavouritesUseCase,
    private val removeCurrencyFromFavouritesUseCase: RemoveCurrencyFromFavouritesUseCase,
    private val setThemeUseCase: SetThemeUseCase,
    private val updateCurrentDataUseCase: UpdateCurrentDataUseCase
): ViewModel() {

    private val _events = MutableSharedFlow<UiEvent>(extraBufferCapacity = 1)
    val events = _events.asSharedFlow()

    private val searchQuery = MutableStateFlow("")
    val query = searchQuery.asStateFlow()
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    fun onSearchQueryChange(query: String){
        searchQuery.value = query.trim()
    }

    private val dayResultFlow = observeCurrentDataUseCase()
    private val favouritesIdsFlow = observeFavouritesUseCase()
    private val debouncedQuery = searchQuery
        .debounce(300)
        .distinctUntilChanged()

    val state: StateFlow<State> =
        combine(
            dayResultFlow,
            favouritesIdsFlow,
            debouncedQuery
        ) { dayResult, favouritesIds, query ->

            val filtered = dayResult.currencies.filter {
                query.isBlank() ||
                        it.name.contains(query, ignoreCase = true) ||
                        it.charCode.contains(query, ignoreCase = true) ||
                        it.numCode.contains(query)
            }

            val uiCurrencies = filtered.map { currency ->
                currency.toUi(
                    isFavourite = currency.id in favouritesIds
                )
            }

            val (favourites, others) =
                uiCurrencies.partition { it.isFavourite }

            State.Data(
                date = dayResult.date.toDateString(),
                favouriteCurrencies = favourites,
                currencies = others
            )
        }
            .map{it as State}
            .onStart { emit(State.Loading) }
            .catch { _events.tryEmit(UiEvent.ShowError(it.toErrorType())) }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                State.Loading
            )



    fun onFavouriteClick(id: String, isFavourite: Boolean) {
        viewModelScope.launch {
            if (isFavourite) {
                removeCurrencyFromFavouritesUseCase(id)
            } else {
                addCurrencyToFavouritesUseCase(id)
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                val res = updateCurrentDataUseCase()
                res.onFailure {
                    _events.tryEmit(UiEvent.ShowError(it.toErrorType()))
                }
            } finally {
                _isRefreshing.value = false
            }
        }
    }


    val themeFlow = observeThemeUseCase()
        .stateIn(viewModelScope, SharingStarted.Eagerly, AppTheme.SYSTEM)

    fun setTheme(theme: AppTheme) {
        viewModelScope.launch {
            setThemeUseCase(theme)
        }
    }

    init {
        refresh()
    }
}




















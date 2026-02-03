package com.example.exchangerateapp.presentation

import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangerateapp.domain.entities.AppTheme
import com.example.exchangerateapp.domain.exeptions.ExceptionWithLoadedLocalData
import com.example.exchangerateapp.domain.usecases.AddCurrencyToFavouritesUseCase
import com.example.exchangerateapp.domain.usecases.ObserveCurrentDataUseCase
import com.example.exchangerateapp.domain.usecases.ObserveFavouritesUseCase
import com.example.exchangerateapp.domain.usecases.ObserveThemeUseCase
import com.example.exchangerateapp.domain.usecases.RemoveCurrencyFromFavouritesUseCase
import com.example.exchangerateapp.domain.usecases.SetThemeUseCase
import com.example.exchangerateapp.domain.usecases.UpdateCurrentDataUseCase
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
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
    private val _state = MutableStateFlow<State>(State.Loading)
    val state: StateFlow<State> = _state

    private val searchQuery = MutableStateFlow("")
    val query = searchQuery.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private val _isDataNew = MutableStateFlow(true)
    val isDataNew = _isDataNew.asStateFlow()

    fun onSearchQueryChange(query: String){
        searchQuery.value = query.trim()
    }

    private val dayResultFlow = observeCurrentDataUseCase()
    private val favouritesIdsFlow = observeFavouritesUseCase()
    private val debouncedQuery = searchQuery
        .debounce(300)
        .distinctUntilChanged()


    fun onFavouriteClick(id: String, isFavourite: Boolean) {
        viewModelScope.launch {
            if (isFavourite) {
                removeCurrencyFromFavouritesUseCase(id)
            } else {
                addCurrencyToFavouritesUseCase(id)
            }
        }
    }

    fun clearQuery(){
        searchQuery.value = ""
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            val result = updateCurrentDataUseCase()
            result
                .onSuccess {
                    _isDataNew.value = true
                }
                .onFailure { throwable ->
                    if (throwable is ExceptionWithLoadedLocalData) {
                        _isDataNew.value = false
                    }
                    else{
                        _state.value = State.Error(throwable.toErrorType())
                    }
                }
            _isRefreshing.value = false
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
        viewModelScope.launch {
            combine(
                dayResultFlow,
                favouritesIdsFlow,
                debouncedQuery,
                isDataNew
            ) { dayResult, favouritesIds, query, isDataNew->

                val filtered = dayResult.currencies.filter { currency ->
                    query.isBlank() ||
                            currency.name.contains(query, ignoreCase = true) ||
                            currency.charCode.contains(query, ignoreCase = true) ||
                            currency.numCode.contains(query)
                }

                val uiCurrencies = filtered.map { currency ->
                    currency.toUi(
                        isFavourite = currency.id in favouritesIds
                    )
                }

                val favourites = uiCurrencies.filter { it.isFavourite }
                val others = uiCurrencies
                State.Data(
                    date = dayResult.date.toDateString(),
                    favouriteCurrencies = favourites,
                    currencies = others,
                    isDataNew = isDataNew
                )
            }
                .onStart { _state.value = State.Loading }
                .catch { throwable ->
                    val errorType = throwable.toErrorType()
                    _state.value = State.Error(
                        type = errorType
                    )
                }
                .collect { newState ->
                    _state.value = newState
                }
        }
        refresh()
    }
}




















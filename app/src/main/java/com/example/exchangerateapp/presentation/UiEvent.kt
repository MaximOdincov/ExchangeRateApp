package com.example.exchangerateapp.presentation

sealed interface UiEvent {
    data class ShowError(val type: ErrorType) : UiEvent
}
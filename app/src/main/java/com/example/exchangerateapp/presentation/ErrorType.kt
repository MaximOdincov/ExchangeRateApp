package com.example.exchangerateapp.presentation

sealed interface ErrorType {

    object NoInternet : ErrorType
    object Server : ErrorType
    object Parsing : ErrorType
    object Database : ErrorType
    object Unknown : ErrorType
}
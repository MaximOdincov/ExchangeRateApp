package com.example.exchangerateapp.domain.exeptions

class EmptyException(cause: Throwable) : Exception(cause)
class ExceptionWithLoadedLocalData(cause: Throwable) : Exception(cause)
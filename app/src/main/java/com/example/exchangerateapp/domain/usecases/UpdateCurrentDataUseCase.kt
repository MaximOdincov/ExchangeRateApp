package com.example.exchangerateapp.domain.usecases

import com.example.exchangerateapp.domain.exeptions.EmptyException
import com.example.exchangerateapp.domain.exeptions.ExceptionWithLoadedLocalData
import com.example.exchangerateapp.domain.repositories.CurrencyRepository

class UpdateCurrentDataUseCase(
    private val repository: CurrencyRepository
){
    suspend operator fun invoke(): Result<Unit> {
        val localResult = repository.getLocalData()
        val local = localResult.getOrElse{throwable ->
            return Result.failure(throwable)
        }

        val remoteResult = repository.getRemoteData()
        val remote = remoteResult.getOrElse { throwable ->
            return if(local == null) Result.failure(EmptyException(throwable))
            else{
                Result.failure(ExceptionWithLoadedLocalData(throwable))
            }
        }

        if (!isNewDay(remote.date, local?.date)) {
            return Result.success(Unit)
        }

        return repository.saveData(remote)
    }
    private fun isNewDay(
        remote: Long,
        local: Long?
    ): Boolean = local == null || remote > local
}
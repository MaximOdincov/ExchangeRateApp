package com.example.exchangerateapp.data.repositories

import com.example.exchangerateapp.data.local.CurrencyDao
import com.example.exchangerateapp.data.local.toDb
import com.example.exchangerateapp.data.local.toDomain
import com.example.exchangerateapp.data.network.ApiService
import com.example.exchangerateapp.data.network.toDomain
import com.example.exchangerateapp.domain.entities.DayResult
import com.example.exchangerateapp.domain.repositories.CurrencyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext

class CurrencyRepositoryImpl(
    private val dao: CurrencyDao,
    private val api: ApiService
): CurrencyRepository {
    override suspend fun getLocalData(): Result<DayResult?> {
        return withContext(Dispatchers.IO) {
            runCatching {
                val currencies = dao.getCurrencies()
                val dayInfo = dao.getDayInfo()
                dayInfo?.toDomain(currencies)
            }
        }
    }

    override suspend fun getRemoteData(): Result<DayResult> =
        withContext(Dispatchers.IO){
            runCatching {
                api.getDailyRates().toDomain()
                }
            }

    override suspend fun saveData(data: DayResult): Result<Unit> =
        withContext(Dispatchers.IO){
            runCatching {
                dao.clearCurrencies()
                dao.insertCurrencies(data.currencies.toDb())
                dao.insertDayInfo(data.toDb())
            }
        }

    override fun observeLocalData(): Flow<DayResult> =
        combine(
            dao.observeCurrencies(),
            dao.observeDayInfo()
        ) { currenciesDb, dayInfoDb ->
            DayResult(
                date = dayInfoDb?.date ?: 0L,
                currencies = currenciesDb.map { it.toDomain() }
            )
        }
}
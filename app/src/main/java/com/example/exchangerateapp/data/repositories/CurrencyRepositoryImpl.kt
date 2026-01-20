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
    override suspend fun getCurrentData(): Result<DayResult?> {
        return withContext(Dispatchers.IO) {
            runCatching {
                val currencies = dao.getCurrencies()
                val dayInfo = dao.getDayInfo()
                dayInfo?.toDomain(currencies)
            }
        }
    }

    override suspend fun updateCurrentData(): Result<Unit> =
        withContext(Dispatchers.IO){
            runCatching {
                val remote = api.getDailyRates().toDomain()
                val localDate = dao.getDayInfo()?.date

                if(isNewDay(remote.date, localDate)){
                    dao.clearCurrencies()
                    dao.insertCurrencies(remote.currencies.toDb())
                    dao.insertDayInfo(remote.toDb())
                }
            }
        }

    override fun observeCurrentData(): Flow<DayResult> =
        combine(
            dao.observeCurrencies(),
            dao.observeDayInfo()
        ) { currenciesDb, dayInfoDb ->
            DayResult(
                date = dayInfoDb?.date ?: 0L,
                currencies = currenciesDb.map { it.toDomain() }
            )
        }


    private fun isNewDay(
        remote: Long,
        local: Long?
    ): Boolean = local == null || remote > local
}
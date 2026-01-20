package com.example.exchangerateapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.exchangerateapp.data.local.entities.CurrencyDb
import com.example.exchangerateapp.data.local.entities.DayInfoDb
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM currencies ORDER BY name ASC")
    fun observeCurrencies(): Flow<List<CurrencyDb>>

    @Query("SELECT * FROM currencies ORDER BY name ASC")
    fun getCurrencies(): List<CurrencyDb>

    @Query("SELECT * FROM day_info LIMIT 1")
    fun observeDayInfo(): Flow<DayInfoDb?>

    @Query("SELECT * FROM day_info LIMIT 1")
    fun getDayInfo(): DayInfoDb?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencies(currencies: List<CurrencyDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDayInfo(info: DayInfoDb)

    @Query("DELETE FROM currencies")
    suspend fun clearCurrencies()
}
package com.example.exchangerateapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.exchangerateapp.data.local.converters.BigDecimalConverter
import com.example.exchangerateapp.data.local.entities.CurrencyDb
import com.example.exchangerateapp.data.local.entities.DayInfoDb

@Database(
    entities = [CurrencyDb::class, DayInfoDb::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(BigDecimalConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao
}
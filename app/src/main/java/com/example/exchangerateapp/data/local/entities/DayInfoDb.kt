package com.example.exchangerateapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "day_info")
data class DayInfoDb(
    @PrimaryKey val id: Int = 0,
    val date: Long
)
package com.example.tp2b.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "capital_cities")
data class CapitalCity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val countryName: String,
    val cityName: String,
    var population: Long
)
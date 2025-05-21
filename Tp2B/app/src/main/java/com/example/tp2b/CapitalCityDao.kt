package com.example.tp2b

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.example.tp2b.model.CapitalCity
import kotlinx.coroutines.flow.Flow

@Dao
interface CapitalCityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: CapitalCity)

    @Query("SELECT * FROM capital_cities WHERE cityName LIKE :cityName COLLATE NOCASE LIMIT 1")
    suspend fun findCityByName(cityName: String): CapitalCity?

    @Query("DELETE FROM capital_cities WHERE cityName LIKE :cityName COLLATE NOCASE")
    suspend fun deleteCityByName(cityName: String): Int

    @Query("DELETE FROM capital_cities WHERE countryName LIKE :countryName COLLATE NOCASE")
    suspend fun deleteAllCitiesFromCountry(countryName: String): Int

    @Query("UPDATE capital_cities SET population = :newPopulation WHERE cityName LIKE :cityName COLLATE NOCASE")
    suspend fun updateCityPopulation(cityName: String, newPopulation: Long): Int

    @Query("SELECT * FROM capital_cities ORDER BY countryName ASC")
    fun getAllCities(): Flow<List<CapitalCity>>
}

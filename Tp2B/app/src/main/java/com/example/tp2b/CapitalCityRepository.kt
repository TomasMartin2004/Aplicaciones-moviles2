package com.example.tp2b

import com.example.tp2b.model.CapitalCity
import kotlinx.coroutines.flow.Flow

class CapitalCityRepository(private val capitalCityDao: CapitalCityDao) {

    //1. cargar una ciudad capital
    suspend fun addCity(city: CapitalCity) {
        capitalCityDao.insertCity(city)
    }

    //2. consultar una ciudad por su nombre
    suspend fun findCityByName(cityName: String): CapitalCity? {
        return capitalCityDao.findCityByName(cityName)
    }

    //3. borrar una ciudad ingresando su nombre
    suspend fun deleteCityByName(cityName: String): Boolean {
        return capitalCityDao.deleteCityByName(cityName) > 0
    }

    //4. borrar todas las ciudades de un país
    suspend fun deleteAllCitiesFromCountry(countryName: String): Int {
        return capitalCityDao.deleteAllCitiesFromCountry(countryName)
    }

    //5. modificar la poblacion de una ciudad
    suspend fun updateCityPopulation(cityName: String, newPopulation: Long): Boolean {
        return capitalCityDao.updateCityPopulation(cityName, newPopulation) > 0
    }

    // metodo adicional para obtener todas las ciudades.
    fun getAllCities(): Flow<List<CapitalCity>> {
        return capitalCityDao.getAllCities()
    }
}

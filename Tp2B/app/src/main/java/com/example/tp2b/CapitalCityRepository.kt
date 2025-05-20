package com.example.tp2b

import com.example.tp2b.model.CapitalCity

class CapitalCityRepository {
    private val cities = mutableListOf<CapitalCity>()

    //1. cargar una ciudad capital
    fun addCity(city: CapitalCity) {
        cities.add(city)
    }

    //2. consultar una ciudad por su nombre
    fun findCityByName(cityName: String): CapitalCity? {
        return cities.find { it.cityName.equals(cityName, ignoreCase = true) }
    }

    //3. borrar una ciudad ingresando su nombre
    fun deleteCityByName(cityName: String): Boolean {
        val city = findCityByName(cityName)
        return if (city != null) {
            cities.remove(city)
            true
        } else {
            false
        }
    }

    //4. borrar todas las ciudades de un pais
    fun deleteAllCitiesFromCountry(countryName: String): Int {
        val citiesToRemove = cities.filter { it.countryName.equals(countryName, ignoreCase = true) }
        cities.removeAll(citiesToRemove)
        return citiesToRemove.size
    }

    //5. modificar la poblacion de una ciudad
    fun updateCityPopulation(cityName: String, newPopulation: Long): Boolean {
        val city = findCityByName(cityName)
        return if (city != null) {
            city.population = newPopulation
            true
        } else {
            false
        }
    }

    //metodo adicional para obtener todas las ciudades
    fun getAllCities(): List<CapitalCity> {
        return cities.toList()
    }
}

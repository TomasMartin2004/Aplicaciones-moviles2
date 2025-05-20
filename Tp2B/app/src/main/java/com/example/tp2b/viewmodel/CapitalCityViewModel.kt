package com.example.tp2b.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tp2b.model.CapitalCity
import com.example.tp2b.CapitalCityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CapitalCityViewModel : ViewModel() {
    private val repository = CapitalCityRepository()

    private val _uiState = MutableStateFlow(CapitalCityUiState())
    val uiState: StateFlow<CapitalCityUiState> = _uiState.asStateFlow()

    init {
        //algunas ciudades de ejemplo
        addCity(CapitalCity("Argentina", "Buenos Aires", 3_075_646))
        addCity(CapitalCity("España", "Madrid", 3_223_334))
        addCity(CapitalCity("Francia", "París", 2_161_000))
        updateCitiesList()
    }

    fun addCity(city: CapitalCity) {
        repository.addCity(city)
        updateCitiesList()
        _uiState.update { it.copy(
            message = "Ciudad ${city.cityName} agregada correctamente"
        ) }
    }

    fun findCityByName(cityName: String) {
        val city = repository.findCityByName(cityName)
        _uiState.update {
            if (city != null) {
                it.copy(
                    foundCity = city,
                    message = "Ciudad encontrada"
                )
            } else {
                it.copy(
                    foundCity = null,
                    message = "Ciudad no encontrada"
                )
            }
        }
    }

    fun deleteCityByName(cityName: String) {
        val result = repository.deleteCityByName(cityName)
        updateCitiesList()
        _uiState.update { it.copy(
            message = if (result) "Ciudad eliminada correctamente" else "Ciudad no encontrada"
        ) }
    }

    fun deleteAllCitiesFromCountry(countryName: String) {
        val count = repository.deleteAllCitiesFromCountry(countryName)
        updateCitiesList()
        _uiState.update { it.copy(
            message = "$count ciudades eliminadas del país $countryName"
        ) }
    }

    fun updateCityPopulation(cityName: String, newPopulation: Long) {
        val result = repository.updateCityPopulation(cityName, newPopulation)
        updateCitiesList()
        _uiState.update { it.copy(
            message = if (result) "Población actualizada correctamente" else "Ciudad no encontrada"
        ) }
    }

    private fun updateCitiesList() {
        _uiState.update { it.copy(cities = repository.getAllCities()) }
    }

    fun clearMessage() {
        _uiState.update { it.copy(message = "") }
    }

    //nuevo metodo para establecer un mensaje arbitrario, util para validaciones
    fun setMessage(msg: String) {
        _uiState.update { it.copy(message = msg) }
    }

    fun clearFoundCity() {
        _uiState.update { it.copy(foundCity = null) }
    }
}

data class CapitalCityUiState(
    val cities: List<CapitalCity> = emptyList(),
    val foundCity: CapitalCity? = null,
    val message: String = ""
)
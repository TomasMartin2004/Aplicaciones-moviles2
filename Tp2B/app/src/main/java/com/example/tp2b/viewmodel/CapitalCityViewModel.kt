package com.example.tp2b.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tp2b.model.CapitalCity
import com.example.tp2b.CapitalCityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CapitalCityViewModel(private val repository: CapitalCityRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(CapitalCityUiState())
    val uiState: StateFlow<CapitalCityUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllCities().collect { cities ->
                _uiState.update { it.copy(cities = cities) }
            }
        }
    }

    fun addCity(city: CapitalCity) {
        viewModelScope.launch {
            repository.addCity(city)
            _uiState.update { it.copy(
                message = "Ciudad ${city.cityName} agregada correctamente"
            ) }
        }
    }

    fun findCityByName(cityName: String) {
        viewModelScope.launch {
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
    }

    fun deleteCityByName(cityName: String) {
        viewModelScope.launch {
            val result = repository.deleteCityByName(cityName)
            _uiState.update { it.copy(
                message = if (result) "Ciudad eliminada correctamente" else "Ciudad no encontrada"
            ) }
        }
    }

    fun deleteAllCitiesFromCountry(countryName: String) {
        viewModelScope.launch {
            val count = repository.deleteAllCitiesFromCountry(countryName)
            _uiState.update { it.copy(
                message = "$count ciudades eliminadas del país $countryName"
            ) }
        }
    }

    fun updateCityPopulation(cityName: String, newPopulation: Long) {
        viewModelScope.launch {
            val result = repository.updateCityPopulation(cityName, newPopulation)
            _uiState.update { it.copy(
                message = if (result) "Población actualizada correctamente" else "Ciudad no encontrada"
            ) }
        }
    }

    fun clearMessage() {
        _uiState.update { it.copy(message = "") }
    }

    fun setMessage(msg: String) {
        _uiState.update { it.copy(message = msg) }
    }

    fun clearFoundCity() {
        _uiState.update { it.copy(foundCity = null) }
    }
}

class CapitalCityViewModelFactory(private val repository: CapitalCityRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CapitalCityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CapitalCityViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

data class CapitalCityUiState(
    val cities: List<CapitalCity> = emptyList(),
    val foundCity: CapitalCity? = null,
    val message: String = ""
)

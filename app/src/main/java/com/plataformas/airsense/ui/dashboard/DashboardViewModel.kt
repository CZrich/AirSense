package com.plataformas.airsense.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plataformas.airsense.data.repository.AirQualityRepository
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {
    private val repository = AirQualityRepository()

    private val _aqi = MutableLiveData<Int>()
    val aqi: LiveData<Int> = _aqi

    private val _city = MutableLiveData<String>()
    val city: LiveData<String> = _city

    private val _location = MutableLiveData<List<Double>>() // Para el mapa
    val location: LiveData<List<Double>> = _location

    private val _aiPrediction = MutableLiveData<String>()
    val aiPrediction: LiveData<String> = _aiPrediction

    private val _iaqi = MutableLiveData<IAqiUiModel>()
    val iaqi: LiveData<IAqiUiModel> = _iaqi

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadAirQuality() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = repository.getAirQuality("here")

                if (response.status == "ok") {
                    val data = response.data
                    _aqi.value = data.aqi
                    _city.value = data.city.name
                    _location.value = data.city.geo

                    _aiPrediction.value = when {
                        data.aqi > 150 -> "Niveles críticos. Basado en tu perfil de asma, quédate en casa."
                        data.aqi > 100 -> "Calidad insalubre. Evita ejercicio intenso fuera."
                        else -> "Día ideal para salir. Calidad óptima para tu condición."
                    }

                    _iaqi.value = IAqiUiModel(
                        pm25 = data.iaqi.pm25?.value,
                        pm10 = data.iaqi.pm10?.value,
                        o3 = data.iaqi.o3?.value,
                        no2 = data.iaqi.no2?.value
                    )
                } else {
                    _error.value = "No se pudo obtener datos"
                }
            } catch (e: Exception) {
                _error.value = "Error de conexión: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}

/* 🧠 Modelo SOLO para UI */
data class IAqiUiModel(
    val pm25: Double?,
    val pm10: Double?,
    val o3: Double?,
    val no2: Double?
)

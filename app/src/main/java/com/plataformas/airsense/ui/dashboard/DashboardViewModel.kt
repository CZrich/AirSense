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

    private val _pm25 = MutableLiveData<Double?>()
    val pm25: LiveData<Double?> = _pm25

    private val _city = MutableLiveData<String>()
    val city: LiveData<String> = _city

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadAirQuality() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null

                val response = repository.getAirQuality("here")

                if (response.status == "ok") {
                    _aqi.value = response.data.aqi
                    _pm25.value = response.data.iaqi.pm25?.value
                    _city.value = response.data.city.name

                } else {
                    _error.value = "No se pudo obtener datos"
                }

            } catch (e: Exception) {
                _error.value = "Error de conexión"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
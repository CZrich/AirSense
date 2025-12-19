package com.plataformas.airsense.data.repository
import  com.plataformas.airsense.data.model.AirQualityResponse
import com.plataformas.airsense.data.remote.RetrofitClient
import com.plataformas.airsense.utils.Constants
class AirQualityRepository {
    suspend fun getAirQuality(location: String): AirQualityResponse {
        return RetrofitClient.api.getAirQualityByLocation(
            location = location,
            token = Constants.API_TOKEN
        )
    }
}
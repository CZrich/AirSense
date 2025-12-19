package com.plataformas.airsense.data.remote
import com.plataformas.airsense.data.model.AirQualityResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
interface AirQualityApi {
    /**
     * Obtiene datos de calidad del aire desde WAQI
     *
     * @param location ciudad, estación o "here"
     * @param token token de autenticación WAQI
     */
    @GET("{location}/")
    suspend fun getAirQualityByLocation(
        @Path("location") location: String,
        @Query("token") token: String
    ): AirQualityResponse

}

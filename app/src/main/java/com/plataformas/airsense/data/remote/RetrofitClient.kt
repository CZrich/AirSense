package com.plataformas.airsense.data.remote
import com.plataformas.airsense.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object RetrofitClient {
    val api: AirQualityApi by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AirQualityApi::class.java)
    }
}
package com.plataformas.airsense.data.model

import com.google.gson.annotations.SerializedName

data class AirQualityResponse(
    @SerializedName("status")
    val status: String,

    @SerializedName("data")
    val data: AirData
)


data class AirData(
    @SerializedName("aqi")
    val aqi: Int,

    @SerializedName("dominentpol")
    val dominantPollutant: String,

    @SerializedName("city")
    val city: City,

    @SerializedName("iaqi")
    val iaqi: IAQI,

    @SerializedName("time")
    val time: Time,

    @SerializedName("forecast")
    val forecast: Forecast?
)
data class City(
    @SerializedName("name")
    val name: String,

    @SerializedName("geo")
    val geo: List<Double>
)
data class IAQI(
    @SerializedName("pm25")
    val pm25: ValueWrapper?,

    @SerializedName("pm10")
    val pm10: ValueWrapper?,

    @SerializedName("o3")
    val o3: ValueWrapper?,     // ✅

    @SerializedName("no2")
    val no2: ValueWrapper?,    // ✅

    @SerializedName("t")
    val temperature: ValueWrapper?,

    @SerializedName("h")
    val humidity: ValueWrapper?
)

data class ValueWrapper(
    @SerializedName("v")
    val value: Double
)
data class Time(
    @SerializedName("s")
    val s: String,

    @SerializedName("tz")
    val tz: String,

    @SerializedName("iso")
    val iso: String
)
data class Forecast(
    @SerializedName("daily")
    val daily: DailyForecast
)

data class DailyForecast(
    @SerializedName("pm25")
    val pm25: List<ForecastItem>?
)

data class ForecastItem(
    @SerializedName("avg")
    val avg: Int,

    @SerializedName("day")
    val day: String,

    @SerializedName("max")
    val max: Int,

    @SerializedName("min")
    val min: Int
)


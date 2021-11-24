package com.example.dell.kotlinweather.logic.network

import com.example.dell.kotlinweather.KotlinWeatherApplication
import com.example.dell.kotlinweather.logic.model.DailyResponse
import com.example.dell.kotlinweather.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("v7/weather/now?key=${KotlinWeatherApplication.key}")
    fun getRealtimeWeather(@Query("location") id: String): Call<RealtimeResponse>

    @GET("v7/weather/3d?key=${KotlinWeatherApplication.key}")
    fun getDailyWeather(@Query("location") id: String): Call<DailyResponse>
}
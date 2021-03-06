package com.example.dell.kotlinweather.logic.network


import com.example.dell.kotlinweather.KotlinWeatherApplication
import com.example.dell.kotlinweather.logic.model.DailyResponse
import com.example.dell.kotlinweather.logic.model.Place
import com.example.dell.kotlinweather.logic.model.PlaceResponse
import com.example.dell.kotlinweather.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {
    @GET("v2/city/lookup?key=${KotlinWeatherApplication.key}")
    fun searchPlaces(@Query("location") query: String): Call<PlaceResponse>




}
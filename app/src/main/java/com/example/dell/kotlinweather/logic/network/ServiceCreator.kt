package com.example.dell.kotlinweather.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {
    private const val PLACE_URL = "https://geoapi.qweather.com/"
    private const val WEATHER_URL = "https://devapi.qweather.com/"


    private val place_retrofit = Retrofit.Builder()
        .baseUrl(PLACE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val weather_retrofit = Retrofit.Builder()
        .baseUrl(WEATHER_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = place_retrofit.create(serviceClass)

    fun <T> create2(serviceClass: Class<T>): T = weather_retrofit.create(serviceClass);

    inline fun <reified T> create(): T = create(T::class.java)


}
package com.example.dell.kotlinweather.logic.network


import com.example.dell.kotlinweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object WeatherNetwork{
    private val placeService = ServiceCreator.create(PlaceService::class.java)
    private val weatherService = ServiceCreator.create2(WeatherService::class.java)

    suspend fun searchPlaces(location: String) = placeService.searchPlaces(location).await()

    private suspend fun <T> Call<T>.await(): T{
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {

                override fun onResponse(call: Call<T>, response: Response<T>){
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(
                        RuntimeException("response body is null")
                    )
                }
                override fun onFailure(call: Call<T>, t:Throwable){
                    continuation.resumeWithException(t)
                }

            })
        }
    }

    suspend fun getDailyWeather(location: String) = weatherService.getDailyWeather(location).await()

    suspend fun getRealtimeWeather(location: String) = weatherService.getRealtimeWeather(location).await()
}
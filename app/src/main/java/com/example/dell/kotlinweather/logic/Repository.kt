package com.example.dell.kotlinweather.logic

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.liveData
import com.example.dell.kotlinweather.logic.Repository.savePlace
import com.example.dell.kotlinweather.logic.dao.PlaceDao
import com.example.dell.kotlinweather.logic.model.Place
import com.example.dell.kotlinweather.logic.model.Weather
import com.example.dell.kotlinweather.logic.network.WeatherNetwork
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

object Repository{
    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    fun getSavedPlace() = PlaceDao.getSavedPlace()

    fun isPlaceSaved() = PlaceDao.isPlaceSaved()

    fun searchPlaces(location: String) = fire(Dispatchers.IO) {
        val placeResponse = WeatherNetwork.searchPlaces(location)
        if (placeResponse.code == "200"){
            val places = placeResponse.location
            Result.success(places)
        } else {
            Log.e(TAG, "searchPlaces: ${placeResponse.code}", )
            Result.failure(RuntimeException("response status is ${placeResponse.code}"))

        }

    }

    fun refreshWeather(locationId: String) = fire(Dispatchers.IO) {

        coroutineScope {
            val deferredRealtime = async {
                WeatherNetwork.getRealtimeWeather(locationId)
            }
            val deferredDaily = async {
                WeatherNetwork.getDailyWeather(locationId)
            }
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()
            Log.e(TAG, "refreshWeather: ${realtimeResponse.code} + ${dailyResponse.code}", )
            if(realtimeResponse.code == "200" && dailyResponse.code == "200"){
                val weather = Weather(realtimeResponse.now, dailyResponse.daily)
                Result.success(weather)
            } else {
                Result.failure(
                    RuntimeException(
                        "realtime response status is ${realtimeResponse.code}" + "daily response status is ${dailyResponse.code}"
                    )

                )
            }
        }
    }

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception){
                Result.failure<T>(e)
            }
            emit(result)
        }
}
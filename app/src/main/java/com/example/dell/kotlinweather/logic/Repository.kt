package com.example.dell.kotlinweather.logic

import android.app.DownloadManager
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.liveData
import com.example.dell.kotlinweather.logic.model.Place
import com.example.dell.kotlinweather.logic.network.WeatherNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.lang.RuntimeException

object Repository{
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = WeatherNetwork.searchPlaces(query)
            if (placeResponse.code == "200"){
                val places = placeResponse.location

                Result.success(places)
            } else {
                Log.e(TAG, "searchPlaces: ${placeResponse.code}", )
                Result.failure(RuntimeException("response status is ${placeResponse.code}"))

            }
        } catch (e: Exception){
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }
}
package com.example.dell.kotlinweather.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.example.dell.kotlinweather.KotlinWeatherApplication
import com.example.dell.kotlinweather.logic.model.Place
import com.google.gson.Gson

object PlaceDao{
    fun savePlace(place: Place){
        sharedPreference().edit{
            putString("place", Gson().toJson(place))
        }
    }

    fun getSavedPlace(): Place{
        val placeJson = sharedPreference().getString("place", "")
        return Gson().fromJson(placeJson, Place::class.java)
    }

    fun isPlaceSaved() = sharedPreference().contains("place")

    private fun sharedPreference() = KotlinWeatherApplication.context.getSharedPreferences("weather", Context.MODE_PRIVATE)
}
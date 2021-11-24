package com.example.dell.kotlinweather.ui.place

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.dell.kotlinweather.logic.Repository

class WeatherViewModel: ViewModel() {
    private val locationLiveData = MutableLiveData<String>()

    var placeName = ""
    var id = ""
    val weatherLiveData = Transformations.switchMap(locationLiveData){ locationId ->
        Repository.refreshWeather(locationId)
        
    }

    fun refreshWeather(locationId: String){
        locationLiveData.value = locationId
    }

}
package com.example.dell.kotlinweather.logic.model

data class RealtimeResponse(val code: String, val now:Now){
    data class Now(val temp: String, val text:String, val feelsLike:String)
}
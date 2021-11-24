package com.example.dell.kotlinweather.logic.model

data class DailyResponse(val code:String, val daily: List<Daily>){
    data class Daily(val fxDate:String, val tempMax: String, val tempMin: String, val textDay: String, val textNight: String)
}
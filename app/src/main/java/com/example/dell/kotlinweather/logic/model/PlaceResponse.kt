package com.example.dell.kotlinweather.logic.model

import com.google.gson.annotations.SerializedName

data class PlaceResponse(val code: String, val location: List<Place>)
data class Place(val name: String, val id:String, @SerializedName("adm1") val province:String, @SerializedName("adm2")val city: String)

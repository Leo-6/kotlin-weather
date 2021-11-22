package com.example.dell.kotlinweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class KotlinWeatherApplication :Application() {
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context:Context
        const val key = "b04c214d45cc41b192eed622ebcaf313"
    }
    override fun onCreate() {
        super.onCreate()
        context = applicationContext

    }
}
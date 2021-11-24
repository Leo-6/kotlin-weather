package com.example.dell.kotlinweather.ui.weather

import android.nfc.Tag
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.dell.kotlinweather.R
import com.example.dell.kotlinweather.logic.model.Weather
import com.example.dell.kotlinweather.ui.place.WeatherViewModel
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.forecast.*
import kotlinx.android.synthetic.main.forecast_item.*
import kotlinx.android.synthetic.main.now.*
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity: AppCompatActivity() {
    private val TAG = "WeatherActivity"
    val viewModel by lazy { ViewModelProviders.of(this).get(WeatherViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        if(viewModel.placeName.isEmpty()){
            viewModel.placeName = intent.getStringExtra("place_name") ?: ""
            viewModel.id = intent.getStringExtra("locationId")?:""
        }
        viewModel.weatherLiveData.observe(this, Observer { result ->
            val weather = result.getOrNull()
            if (weather != null) {
                showWeatherInfo(weather)
            } else {
                Toast.makeText(this, "无法成功获取天气信息", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
        viewModel.refreshWeather(viewModel.id)
    }

    private fun showWeatherInfo(weather: Weather) {
        placeName.text = viewModel.placeName
        val realtime = weather.realtime
        val daily = weather.daily
        //now.xml中的数据
        val currentTempText = "${realtime.temp.toInt()}℃"
        currentTemp.text = currentTempText
        currentSky.text = realtime.text
        feelsLikeTemp.text = "体感温度：${realtime.feelsLike.toInt()}℃"

        //forecast.xml
        forecastLayout.removeAllViews()
        val days = daily.size
        for(i in 0 until days){
            val view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false)
            val dataInfo = view.findViewById(R.id.dateInfo) as TextView
            val skyInfo = view.findViewById(R.id.skyInfo) as TextView
            val temperatureInfo = view.findViewById(R.id.temperatureInfo) as TextView
            dataInfo.text = daily[i].fxDate
            skyInfo.text = "${daily[i].textDay} / ${daily[i].textNight}"
            temperatureInfo.text = "${daily[i].tempMax}℃ / ${daily[i].tempMin}℃"
            forecastLayout.addView(view)
        }
        weatherLayout.visibility = View.VISIBLE
    }
}
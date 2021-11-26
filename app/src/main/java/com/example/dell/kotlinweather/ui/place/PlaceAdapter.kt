package com.example.dell.kotlinweather.ui.place

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.dell.kotlinweather.R
import com.example.dell.kotlinweather.logic.model.Place
import com.example.dell.kotlinweather.ui.weather.WeatherActivity
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.place_item.view.*

class PlaceAdapter(private  val fragment: PlaceFragment, private val placeList: List<Place>): RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val placeName: TextView = view.findViewById(R.id.placeName)
        val cityName: TextView = view.findViewById(R.id.placeAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false);
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = placeList[position]
        holder.placeName.text = place.name
        holder.cityName.text = place.province + place.city + place.name
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val place = placeList[position]
            val activity = fragment.activity
            if(activity is WeatherActivity){
                activity.drawerLayout.closeDrawers()
                activity.viewModel.placeName = place.name
                activity.viewModel.id = place.id
                activity.refreshWeather()
            } else {
                val intent = Intent(holder.itemView.context, WeatherActivity::class.java).apply {
                    putExtra("place_name", place.name)
                    putExtra("locationId", place.id)
                }
                fragment.startActivity(intent)
                activity?.finish()
            }
            fragment.viewModel.savePlace(place)

        }

    }

    override fun getItemCount(): Int {
        return placeList.size
    }
}
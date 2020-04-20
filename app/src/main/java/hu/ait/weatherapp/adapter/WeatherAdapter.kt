package hu.ait.weatherapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.ait.weatherapp.R
import hu.ait.weatherapp.ScrollingActivity
import hu.ait.weatherapp.data.City
import hu.ait.weatherapp.touch.WeatherTouchHelperCallback
import kotlinx.android.synthetic.main.city_row.view.*
import java.util.*

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.ViewHolder>,
    WeatherTouchHelperCallback {
    override fun onDismissed(position: Int) {
        deleteCity(position)
    }

    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
        Collections.swap(cities, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    var cities = mutableListOf<City>()
    var context : Context
    constructor(context: Context, listCities: List<City>){
        this.context = context
        cities.addAll(listCities)
    }

    // how each item looks like
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var cityView = LayoutInflater.from(context).inflate(
            R.layout.city_row, parent, false
        )
        return ViewHolder(cityView)
    }

    //called for every iem
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCity = cities[position]

        holder.tvName.text = currentCity.cityName
        holder.btnDelete.setOnClickListener{
            deleteCity(holder.adapterPosition)
        }
        //TODO: Update to weather pics
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    fun addCity(city: City){
        cities.add(city)
        //notify data set changed // redraw list
        notifyItemInserted(cities.lastIndex)
    }

    fun deleteCity(position: Int){
        Thread {
            (context as ScrollingActivity).runOnUiThread {
                cities.removeAt(position)
                notifyItemRemoved(position)
            }
        }.start()
    }

    fun deleteAll(){
        cities.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvName = itemView.tvName
        var btnDelete = itemView.btnDelete
        var imgType = itemView.imgvWeather
    }
}
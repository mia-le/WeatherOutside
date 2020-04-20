package hu.ait.weatherapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import hu.ait.weatherapp.DetailsActivity
import hu.ait.weatherapp.R
import hu.ait.weatherapp.ScrollingActivity
import hu.ait.weatherapp.SplashActivity
import hu.ait.weatherapp.data.City
import hu.ait.weatherapp.touch.WeatherTouchHelperCallback
import kotlinx.android.synthetic.main.city_row.view.*
import java.util.*

class WeatherAdapter: RecyclerView.Adapter<WeatherAdapter.ViewHolder>,
    WeatherTouchHelperCallback {

    companion object {
        const val KEY_DATA = "KEY_DATA"
    }


    override fun onDismissed(position: Int) {
        deleteCity(position)
    }

    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
        Collections.swap(cities, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    var cities = mutableListOf<City>()
    var context : Context
    var activity : ScrollingActivity
    constructor(context: Context, listCities: List<City>, activity: ScrollingActivity){
        this.activity = activity
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

    //called for every item
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCity = cities[position]

        holder.tvName.text = currentCity.cityName
        holder.btnDelete.setOnClickListener{
            deleteCity(holder.adapterPosition)
        }
        //TODO: Update to weather pics
        //holder.imgType.setImageResource()

        holder.cardCity.setOnClickListener {
            var intentDetails = Intent()

            intentDetails.setClass(this.context, DetailsActivity::class.java)

            intentDetails.putExtra(KEY_DATA, holder.tvName.text.toString())

            activity.startActivity(intentDetails)
        }
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
        cities.removeAt(position)
        notifyItemRemoved(position)
    }

    fun deleteAll(){
        cities.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvName = itemView.tvName
        var btnDelete = itemView.btnDelete
        var cardCity = itemView.cardCity
    }
}
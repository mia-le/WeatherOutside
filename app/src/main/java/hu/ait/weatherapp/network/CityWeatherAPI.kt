package hu.ait.weatherapp.network

import hu.ait.weatherapp.data.CityWeatherResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//https://api.openweathermap.org/data/2.5/weather?q=Budapest,hu&units=metric&appid=f3d694bc3e1d44c1ed5a97bd1120e8fe

interface CityWeatherAPI {
    @GET("/data/2.5/weather")
    fun getWeather(@Query("q") city: String,
    @Query("units") units: String,
    @Query("appid") appid: String) : Call<CityWeatherResult>
}
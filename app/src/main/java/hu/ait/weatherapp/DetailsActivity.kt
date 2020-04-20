package hu.ait.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.ait.weatherapp.data.CityWeatherResult
import hu.ait.weatherapp.network.CityWeatherAPI
import kotlinx.android.synthetic.main.activity_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailsActivity : AppCompatActivity() {

    val cityName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val weatherAPI = retrofit.create(CityWeatherAPI::class.java)

        val weatherCall = weatherAPI.getWeather("Budapest")

        weatherCall.enqueue(object : Callback<CityWeatherResult> {
            override fun onFailure(call: Call<CityWeatherResult>, t: Throwable) {
                tvCityName.text = t.message
            }

            override fun onResponse(
                call: Call<CityWeatherResult>,
                response: Response<CityWeatherResult>
            ) {
                var weatherResult = response.body()
                tvCityTemp.text = "HUF: ${weatherResult?.main?.temp}"
            }
        })
    }

}

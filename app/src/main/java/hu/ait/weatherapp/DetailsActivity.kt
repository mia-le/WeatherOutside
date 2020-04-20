package hu.ait.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import hu.ait.weatherapp.data.CityWeatherResult
import hu.ait.weatherapp.network.CityWeatherAPI
import kotlinx.android.synthetic.main.activity_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailsActivity : AppCompatActivity() {

    companion object {
        const val KEY_DATA = "KEY_DATA"
    }

    //val cityName = "Budapest"
    val cityName = intent.getStringExtra(KEY_DATA)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val weatherAPI = retrofit.create(CityWeatherAPI::class.java)

        val weatherCall = weatherAPI.getWeather(cityName,
            "metric",
            "b8fb4c940db983b12db035d0dac282b1")

        weatherCall.enqueue(object : Callback<CityWeatherResult> {
            override fun onFailure(call: Call<CityWeatherResult>, t: Throwable) {
                tvCityName.text = t.message
            }

            override fun onResponse(
                call: Call<CityWeatherResult>,
                response: Response<CityWeatherResult>
            ) {
                var weatherResult = response.body()
                tvCityName.text = "${cityName}, ${weatherResult?.sys?.country}"
                tvCityMain.text = "${weatherResult?.weather?.get(0)?.main}"
                tvCityDescription.text = "${weatherResult?.weather?.get(0)?.description}"
                tvCityTemp.text = "${weatherResult?.main?.temp}°C"
                tvCityTempMin.text = "min: ${weatherResult?.main?.temp_min}"
                tvCityTempMax.text = "max:${weatherResult?.main?.temp_max}"
                tvCityFeelsLike.text = "Feels like: ${weatherResult?.main?.feels_like}°C"
                tvCityPressure.text = "Pressure: ${weatherResult?.main?.pressure}  hPa"
                tvCityHumidity.text = "Humidity: ${weatherResult?.main?.humidity} %"
                tvCityWindSpeed.text = "Wind speed: ${weatherResult?.wind?.speed} m/s"
                tvCityCloud.text = "Cloudiness: ${weatherResult?.clouds?.all} %"
//                Glide.with(this).load(
//                    ("https://openweathermap.org/img/w/" +response.body()?.weather?.get(0)?.icon+ ".png")).into(imgvWeather)
            }
        })
    }

    override fun onBackPressed() {

    }
}

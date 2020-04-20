package hu.ait.weatherapp.data

data class CityWeatherResult(val coord: Coord?, val weather: List<Weather1581206104>?, val base: String?, val main: Main?, val visibility: Number?, val wind: Wind?, val clouds: Clouds?, val dt: Number?, val sys: Sys?, val timezone: Number?, val id: Number?, val name: String?, val cod: Number?)

data class Clouds(val all: Number?)

data class Coord(val lon: Number?, val lat: Number?)

data class Main(val temp: Number?, val feels_like: Number?, val temp_min: Number?, val temp_max: Number?, val pressure: Number?, val humidity: Number?)

data class Sys(val type: Number?, val id: Number?, val country: String?, val sunrise: Number?, val sunset: Number?)

data class Weather1581206104(val id: Number?, val main: String?, val description: String?, val icon: String?)

data class Wind(val speed: Number?, val deg: Number?)
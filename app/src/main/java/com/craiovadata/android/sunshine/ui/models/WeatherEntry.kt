package com.craiovadata.android.sunshine.ui.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "weather", indices = [Index(value = ["date"], unique = true)])
class WeatherEntry {

//    @PrimaryKey(autoGenerate = true)
    @PrimaryKey var date: Date = Date()
//    var id: Int = 0
    var weatherId: Int = 0

    var temperature: Double = 0.toDouble()
    var humidity: Double = 0.toDouble()
    var pressure: Double = 0.toDouble()
    var wind: Double = 0.toDouble()
    var degrees: Double = 0.toDouble()
    var lat: Double = 0.toDouble()
    var lon: Double = 0.toDouble()
    var iconCodeOWM: String = "01d"
    var isCurrentWeather: Int = NON_CURRENT_WEATHER
    var cityName: String = ""
    var description: String = ""
    var sunrise: Long = 0
    var sunset: Long = 0
    var dt: Long = 0

    /**
     * This constructor is used by OpenWeatherJsonParser. When the network fetch has JSON data, it
     * converts this data to WeatherEntry objects using this constructor.
     *
     * @param weatherId Image _id for weather
     * @param date          Date of weather
     * @param temperature          Max temperature
     * @param humidity      Humidity for the day
     * @param pressure      Barometric pressure
     * @param wind          Wind speed
     * @param degrees       Wind direction
     * @param iconCodeOWM          Icon code OWM
     */

    // - for DEBUG - constructor for currentWeather. Has isCurrentWeather = 1 and cityName - for debug
    @Ignore
    constructor(
            weatherId: Int, date: Date, temperature: Double, humidity: Double, pressure: Double,
            wind: Double, degrees: Double, iconCodeOWM: String, isCurrentWeather: Int, cityName: String, lat: Double, lon: Double,
            sunrise: Long, sunset: Long, dt: Long) {
        this.weatherId = weatherId
        this.date = date
        this.temperature = temperature
        this.humidity = humidity
        this.pressure = pressure
        this.wind = wind
        this.degrees = degrees
        this.iconCodeOWM = iconCodeOWM
        this.isCurrentWeather = isCurrentWeather
        this.cityName = cityName
        this.lat = lat
        this.lon = lon
        this.sunrise = sunrise
        this.sunset = sunset
        this.dt = dt

    }

 // constructor for currentWeather. Has isCurrentWeather = 1
    @Ignore
    constructor(
            weatherId: Int, date: Date, temperature: Double, humidity: Double, pressure: Double,
            wind: Double, degrees: Double, iconCodeOWM: String, isCurrentWeather: Int, lat: Double, lon: Double,
            sunrise: Long, sunset: Long, dt: Long) {
        this.weatherId = weatherId
        this.date = date
        this.temperature = temperature
        this.humidity = humidity
        this.pressure = pressure
        this.wind = wind
        this.degrees = degrees
        this.iconCodeOWM = iconCodeOWM
        this.isCurrentWeather = isCurrentWeather
        this.lat = lat
        this.lon = lon
     this.sunrise = sunrise
     this.sunset = sunset
     this.dt = dt
    }

// constructor for forecast 5days 3 hours - isCurrentWeather missing but defaults to 0
    @Ignore
    constructor(
            weatherId: Int, date: Date, temperature: Double, humidity: Double, pressure: Double,
            wind: Double, degrees: Double, iconCodeOWM: String, lat: Double, lon: Double) {
        this.weatherId = weatherId
        this.date = date
        this.temperature = temperature
        this.humidity = humidity
        this.pressure = pressure
        this.wind = wind
        this.degrees = degrees
        this.iconCodeOWM = iconCodeOWM
        this.lat = lat
        this.lon = lon
    }

    // main constructor. Complet
    constructor(
//        id: Int,
                weatherId: Int, date: Date, temperature: Double, humidity: Double, pressure: Double,
                wind: Double, degrees: Double, iconCodeOWM: String, isCurrentWeather: Int, cityName: String) {
//        this.id = id
        this.weatherId = weatherId
        this.date = date
        this.temperature = temperature
        this.humidity = humidity
        this.pressure = pressure
        this.wind = wind
        this.degrees = degrees
        this.iconCodeOWM = iconCodeOWM
        this.isCurrentWeather = isCurrentWeather
        this.cityName = cityName
    }

    @Ignore
    constructor(
//        id: Int,
        weatherId: Int,
        description: String){
        this.description = description
//        this.id = id
        this.weatherId = weatherId
    }

    companion object{
        const val CURRENT_WEATHER = 1
        const val NON_CURRENT_WEATHER = 0
    }
}

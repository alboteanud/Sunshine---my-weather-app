package com.example.android.sunshine.data.database

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "weather", indices = [Index(value = ["date"], unique = true)])
class WeatherEntry {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var weatherIconId: Int = 0
    var date: Date = Date()
    var temp: Double = 0.toDouble()
    var humidity: Double = 0.toDouble()
    var pressure: Double = 0.toDouble()
    var wind: Double = 0.toDouble()
    var degrees: Double = 0.toDouble()
    var lat: Double = 0.toDouble()
    var lon: Double = 0.toDouble()
    var icon: String = ""
    var isCurrentWeather: Int = 0

    /**
     * This constructor is used by OpenWeatherJsonParser. When the network fetch has JSON data, it
     * converts this data to WeatherEntry objects using this constructor.
     *
     * @param weatherIconId Image _id for weather
     * @param date          Date of weather
     * @param temp          Max temp
     * @param humidity      Humidity for the day
     * @param pressure      Barometric pressure
     * @param wind          Wind speed
     * @param degrees       Wind direction
     * @param icon          Icon code OWM
     */
    @Ignore
    constructor(
            weatherIconId: Int, date: Date, temp: Double, humidity: Double, pressure: Double,
            wind: Double, degrees: Double, icon: String, isCurrentWeather: Int) {
        this.weatherIconId = weatherIconId
        this.date = date
        this.temp = temp
        this.humidity = humidity
        this.pressure = pressure
        this.wind = wind
        this.degrees = degrees
        this.icon = icon
        this.isCurrentWeather = isCurrentWeather
//        this.lat = lat
//        this.lon = lon
    }

  @Ignore
    constructor(
            weatherIconId: Int, date: Date, temp: Double, humidity: Double, pressure: Double,
            wind: Double, degrees: Double, icon: String, isCurrentWeather: Int, lat: Double, lon: Double) {
        this.weatherIconId = weatherIconId
        this.date = date
        this.temp = temp
        this.humidity = humidity
        this.pressure = pressure
        this.wind = wind
        this.degrees = degrees
        this.icon = icon
        this.isCurrentWeather = isCurrentWeather
        this.lat = lat
        this.lon = lon
    }

    constructor(id: Int,
                weatherIconId: Int, date: Date, temp: Double, humidity: Double, pressure: Double,
                wind: Double, degrees: Double, icon: String, isCurrentWeather: Int) {
        this.id = id
        this.weatherIconId = weatherIconId
        this.date = date
        this.temp = temp
        this.humidity = humidity
        this.pressure = pressure
        this.wind = wind
        this.degrees = degrees
        this.icon = icon
        this.isCurrentWeather = isCurrentWeather
    }
}

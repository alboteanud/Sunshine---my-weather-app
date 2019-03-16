

package com.example.android.sunshine.data.database;

import java.util.Date;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "weather", indices = {@Index(value = {"_date"}, unique = true)})
public class WeatherEntry_java {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int weatherIconId;
    private Date date;
    private double temp;
    private double humidity;
    private double pressure;
    private double wind;
    private double degrees;
    private String icon;
    private int isCurrentWeather;

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
    public WeatherEntry_java(int weatherIconId, Date date, double temp, double humidity, double pressure, double wind, double degrees, String icon, int isCurrentWeather) {
        this.weatherIconId = weatherIconId;
        this.date = date;
        this.temp = temp;
        this.humidity = humidity;
        this.pressure = pressure;
        this.wind = wind;
        this.degrees = degrees;
        this.icon = icon;
        this.isCurrentWeather = isCurrentWeather;
    }

    @Ignore
    public WeatherEntry_java(int weatherIconId, Date date, double temp, double humidity, double pressure, double wind, double degrees, String icon) {
        this.weatherIconId = weatherIconId;
        this.date = date;
        this.temp = temp;
        this.humidity = humidity;
        this.pressure = pressure;
        this.wind = wind;
        this.degrees = degrees;
        this.icon = icon;
    }

    public WeatherEntry_java(int id, int weatherIconId, Date date, double temp, double humidity, double pressure, double wind, double degrees, String icon, int isCurrentWeather) {
        this.id = id;
        this.weatherIconId = weatherIconId;
        this.date = date;
        this.temp = temp;
        this.humidity = humidity;
        this.pressure = pressure;
        this.wind = wind;
        this.degrees = degrees;
        this.icon = icon;
        this.isCurrentWeather = isCurrentWeather;
    }


    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public int getWeatherIconId() {
        return weatherIconId;
    }

    public double getTemp() {
        return temp;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public double getWind() {
        return wind;
    }

    public double getDegrees() {
        return degrees;
    }

    public String getIcon() {
        return icon;
    }

    public int isCurrentWeather() {
        return isCurrentWeather;
    }
}

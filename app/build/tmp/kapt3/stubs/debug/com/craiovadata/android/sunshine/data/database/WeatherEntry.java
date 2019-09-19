package com.craiovadata.android.sunshine.data.database;

import java.lang.System;

@androidx.room.Entity(tableName = "weather", indices = {@androidx.room.Index(unique = true, value = {"date"})})
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b+\b\u0007\u0018\u00002\u00020\u0001Bg\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0007\u0012\u0006\u0010\n\u001a\u00020\u0007\u0012\u0006\u0010\u000b\u001a\u00020\u0007\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u0003\u0012\u0006\u0010\u000f\u001a\u00020\r\u0012\u0006\u0010\u0010\u001a\u00020\u0007\u0012\u0006\u0010\u0011\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\u0012B_\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0007\u0012\u0006\u0010\n\u001a\u00020\u0007\u0012\u0006\u0010\u000b\u001a\u00020\u0007\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u0003\u0012\u0006\u0010\u0010\u001a\u00020\u0007\u0012\u0006\u0010\u0011\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\u0013BW\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0007\u0012\u0006\u0010\n\u001a\u00020\u0007\u0012\u0006\u0010\u000b\u001a\u00020\u0007\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u0010\u001a\u00020\u0007\u0012\u0006\u0010\u0011\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\u0014B_\b\u0016\u0012\u0006\u0010\u0015\u001a\u00020\u0003\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0007\u0012\u0006\u0010\n\u001a\u00020\u0007\u0012\u0006\u0010\u000b\u001a\u00020\u0007\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u0003\u0012\u0006\u0010\u000f\u001a\u00020\r\u00a2\u0006\u0002\u0010\u0016R\u001a\u0010\u000f\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u001a\u0010\u000b\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u001a\u0010\b\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b#\u0010 \"\u0004\b$\u0010\"R\u001a\u0010\f\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u0018\"\u0004\b&\u0010\u001aR\u001e\u0010\u0015\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\'\u0010(\"\u0004\b)\u0010*R\u001a\u0010\u000e\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010(\"\u0004\b+\u0010*R\u001a\u0010\u0010\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b,\u0010 \"\u0004\b-\u0010\"R\u001a\u0010\u0011\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b.\u0010 \"\u0004\b/\u0010\"R\u001a\u0010\t\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b0\u0010 \"\u0004\b1\u0010\"R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b2\u0010 \"\u0004\b3\u0010\"R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b4\u0010(\"\u0004\b5\u0010*R\u001a\u0010\n\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b6\u0010 \"\u0004\b7\u0010\"\u00a8\u00068"}, d2 = {"Lcom/craiovadata/android/sunshine/data/database/WeatherEntry;", "", "weatherId", "", "date", "Ljava/util/Date;", "temperature", "", "humidity", "pressure", "wind", "degrees", "iconCodeOWM", "", "isCurrentWeather", "cityName", "lat", "lon", "(ILjava/util/Date;DDDDDLjava/lang/String;ILjava/lang/String;DD)V", "(ILjava/util/Date;DDDDDLjava/lang/String;IDD)V", "(ILjava/util/Date;DDDDDLjava/lang/String;DD)V", "id", "(IILjava/util/Date;DDDDDLjava/lang/String;ILjava/lang/String;)V", "getCityName", "()Ljava/lang/String;", "setCityName", "(Ljava/lang/String;)V", "getDate", "()Ljava/util/Date;", "setDate", "(Ljava/util/Date;)V", "getDegrees", "()D", "setDegrees", "(D)V", "getHumidity", "setHumidity", "getIconCodeOWM", "setIconCodeOWM", "getId", "()I", "setId", "(I)V", "setCurrentWeather", "getLat", "setLat", "getLon", "setLon", "getPressure", "setPressure", "getTemperature", "setTemperature", "getWeatherId", "setWeatherId", "getWind", "setWind", "app_debug"})
public final class WeatherEntry {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private int id;
    private int weatherId;
    @org.jetbrains.annotations.NotNull()
    private java.util.Date date;
    private double temperature;
    private double humidity;
    private double pressure;
    private double wind;
    private double degrees;
    private double lat;
    private double lon;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String iconCodeOWM;
    private int isCurrentWeather;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String cityName;
    
    public final int getId() {
        return 0;
    }
    
    public final void setId(int p0) {
    }
    
    public final int getWeatherId() {
        return 0;
    }
    
    public final void setWeatherId(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Date getDate() {
        return null;
    }
    
    public final void setDate(@org.jetbrains.annotations.NotNull()
    java.util.Date p0) {
    }
    
    public final double getTemperature() {
        return 0.0;
    }
    
    public final void setTemperature(double p0) {
    }
    
    public final double getHumidity() {
        return 0.0;
    }
    
    public final void setHumidity(double p0) {
    }
    
    public final double getPressure() {
        return 0.0;
    }
    
    public final void setPressure(double p0) {
    }
    
    public final double getWind() {
        return 0.0;
    }
    
    public final void setWind(double p0) {
    }
    
    public final double getDegrees() {
        return 0.0;
    }
    
    public final void setDegrees(double p0) {
    }
    
    public final double getLat() {
        return 0.0;
    }
    
    public final void setLat(double p0) {
    }
    
    public final double getLon() {
        return 0.0;
    }
    
    public final void setLon(double p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getIconCodeOWM() {
        return null;
    }
    
    public final void setIconCodeOWM(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    public final int isCurrentWeather() {
        return 0;
    }
    
    public final void setCurrentWeather(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCityName() {
        return null;
    }
    
    public final void setCityName(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
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
    @androidx.room.Ignore()
    public WeatherEntry(int weatherId, @org.jetbrains.annotations.NotNull()
    java.util.Date date, double temperature, double humidity, double pressure, double wind, double degrees, @org.jetbrains.annotations.NotNull()
    java.lang.String iconCodeOWM, int isCurrentWeather, @org.jetbrains.annotations.NotNull()
    java.lang.String cityName, double lat, double lon) {
        super();
    }
    
    @androidx.room.Ignore()
    public WeatherEntry(int weatherId, @org.jetbrains.annotations.NotNull()
    java.util.Date date, double temperature, double humidity, double pressure, double wind, double degrees, @org.jetbrains.annotations.NotNull()
    java.lang.String iconCodeOWM, int isCurrentWeather, double lat, double lon) {
        super();
    }
    
    @androidx.room.Ignore()
    public WeatherEntry(int weatherId, @org.jetbrains.annotations.NotNull()
    java.util.Date date, double temperature, double humidity, double pressure, double wind, double degrees, @org.jetbrains.annotations.NotNull()
    java.lang.String iconCodeOWM, double lat, double lon) {
        super();
    }
    
    public WeatherEntry(int id, int weatherId, @org.jetbrains.annotations.NotNull()
    java.util.Date date, double temperature, double humidity, double pressure, double wind, double degrees, @org.jetbrains.annotations.NotNull()
    java.lang.String iconCodeOWM, int isCurrentWeather, @org.jetbrains.annotations.NotNull()
    java.lang.String cityName) {
        super();
    }
}
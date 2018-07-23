package com.example.android.sunshine.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

@Database(entities = {WeatherEntry.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class SunshineDatabase extends RoomDatabase {

    private static final String LOG_TAG = SunshineDatabase.class.getSimpleName();
    private static final String DATABASE_NAME = "weather";

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static volatile SunshineDatabase sInstance;

    public static SunshineDatabase getInstance(Context context) {
        Log.d(LOG_TAG, "Getting the database");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        SunshineDatabase.class, SunshineDatabase.DATABASE_NAME).build();
                Log.d(LOG_TAG, "Made new database");
            }
        }
        return sInstance;
    }
    public abstract WeatherDao weatherDao();

}

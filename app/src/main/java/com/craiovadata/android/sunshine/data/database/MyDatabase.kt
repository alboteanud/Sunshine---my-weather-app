package com.craiovadata.android.sunshine.data.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [WeatherEntry::class], version = 1, exportSchema = false)
@TypeConverters(
    DateConverter::class
)
abstract class MyDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao?

    companion object {
        private val LOG_TAG = MyDatabase::class.java.simpleName
        private const val DATABASE_NAME = "weather"
        // For Singleton instantiation
        private val LOCK = Any()
        @Volatile
        private var sInstance: MyDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): MyDatabase? {
            Log.d(LOG_TAG, "Getting the database")
            if (sInstance == null) {
                synchronized(LOCK) {
                    sInstance = databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java,
                        DATABASE_NAME
                    ).build()
                    Log.d(LOG_TAG, "Made new database")
                }
            }
            return sInstance
        }
    }
}
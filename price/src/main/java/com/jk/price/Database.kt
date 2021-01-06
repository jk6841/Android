package com.jk.price

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@androidx.room.Database(
        entities = [Purchase::class],
        version = 1,
        exportSchema = false
)

@TypeConverters(DateString::class)
abstract class Database: RoomDatabase() {

    abstract fun dbDao(): DBDao

    companion object{
        @Volatile
        private var database: Database? = null

        @Synchronized
        fun getInstance(application: Application): Database{
            if (database == null){
                val context: Context = application.applicationContext
                database = Room
                        .databaseBuilder(context, Database::class.java, "app.db").build()
            }
            return database as Database
        }
    }

}
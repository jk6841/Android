package com.jk.price

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.runBlocking

class Repository {

    companion object{
        @Volatile
        private var instance: Repository? = null
        private var database: Database? = null
        private var dbDao: DBDao? = null

        @Synchronized
        fun getInstance(application: Application): Repository {
            if (instance == null){
                instance = Repository()
                database = Database.getInstance(application)
                dbDao = database!!.dbDao()
                return instance!!
            }
            return instance!!
        }
    }

    fun insert(purchase: Purchase){
        runBlocking{
            dbDao!!.insert(purchase)
        }
    }

    fun search(name: String): LiveData<List<Purchase>> = dbDao!!.search("%$name%")

}
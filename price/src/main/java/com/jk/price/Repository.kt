package com.jk.price

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.runBlocking

object Repository{
    private var database: Database? = null
    private var dbDao: DBDao? = null

    fun setupRepository(application: Application){
        database = Database.getInstance(application)
        dbDao = database!!.dbDao()
    }

    fun insert(purchase: Purchase){
        runBlocking{
            dbDao!!.insert(purchase)
        }
    }

    fun search(market: String, type: String, name: String): LiveData<List<Purchase>>{
        return dbDao!!.search("%$market%", "%$type%","%$name%")
    }

    fun delete(ID: Int){
        runBlocking {
            dbDao!!.delete(ID)
        }
    }

}
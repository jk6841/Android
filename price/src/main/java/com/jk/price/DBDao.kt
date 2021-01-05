package com.jk.price

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DBDao {

    @Insert
    suspend fun insert(purchase: Purchase)

    @Query("SELECT * FROM purchase WHERE Name LIKE :name")
    fun search(name: String): LiveData<List<Purchase>>

}
package com.jk.price

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DBDao {
    @Insert
    suspend fun insert(purchase: Purchase)

    @Query("SELECT * FROM purchase " +
            "WHERE Market LIKE :market AND Type LIKE :type AND Name LIKE :name")
    fun search(market: String, type: String, name: String): LiveData<List<Purchase>>

    @Query("DELETE FROM purchase WHERE ID = :ID")
    suspend fun delete(ID: Int)
}
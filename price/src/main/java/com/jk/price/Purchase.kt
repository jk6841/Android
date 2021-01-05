package com.jk.price

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName="purchase")
class Purchase {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="ID") var ID: Int = 0

    @ColumnInfo(name="Date") var date: String = ""

    @ColumnInfo(name="Name") var name: String = ""

    @ColumnInfo(name="Market") var market: String = ""

    @ColumnInfo(name="Count") var count: Int = 0

    @ColumnInfo(name="Cost") var cost: Int = 0

}
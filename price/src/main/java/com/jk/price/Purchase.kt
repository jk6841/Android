package com.jk.price

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName="purchase")
class Purchase {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="ID") var ID = 0

    @ColumnInfo(name="Date") var date = ""

    @ColumnInfo(name="Market") var market = ""

    @ColumnInfo(name="Type") var type = ""

    @ColumnInfo(name="Name") var name = ""

    @ColumnInfo(name="Count") var count = 0

    @ColumnInfo(name="Cost") var cost = 0

    @ColumnInfo(name="UnitCost") var unitCost = 0.toDouble()

    @ColumnInfo(name="Unit") var unit = ""

}
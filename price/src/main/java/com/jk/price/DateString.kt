package com.jk.price

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class DateString {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd E요일", Locale.KOREA)

    @TypeConverter
    fun dateToString(date: Date?): String? {
        return if (date == null) dateFormat.format(Date()) else dateFormat.format(date)
    }

    @TypeConverter
    fun stringToDate(string: String?): Date? {
        try {
            return dateFormat.parse(string!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

}
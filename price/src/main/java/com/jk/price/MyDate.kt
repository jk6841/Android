package com.jk.price

import android.util.Log
import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

object MyDate {
    val dateFormat0 = SimpleDateFormat("yyyy년 M월 d일 E요일", Locale.KOREA)
    val dateFormat1 = SimpleDateFormat("yyyyMd", Locale.KOREA)

    private val cal: Calendar? = Calendar.getInstance()

    fun getTodayYear() = cal!!.get(Calendar.YEAR).toString()

    fun getTodayMonth() = (cal!!.get(Calendar.MONTH) + 1).toString()

    fun getTodayDay() = cal!!.get(Calendar.DATE).toString()

    fun getDayList(year: String, month: String): ArrayList<String>{
        val y = year.toInt()
        when (month.toInt()){
            1, 3, 5, 7, 8, 10, 12 -> return MyResource.day31
            4, 6, 9, 11 -> return MyResource.day30
            else -> {
                if (y.rem(400) == 0)
                    return MyResource.day29
                if (y.rem(100) == 0)
                    return MyResource.day28
                if (y.rem(4) == 0)
                    return MyResource.day29
                return MyResource.day28
            }
        }
    }

    @TypeConverter
    fun dateToString(date: Date?): String? {
        return if (date == null) dateFormat0.format(Date()) else dateFormat0.format(date)
    }

    @TypeConverter
    fun stringToDate(string: String?): Date? {
        try {
            return dateFormat0.parse(string!!)
        } catch (e: Exception) {
            Log.e("", e.message!!)
        }
        return null
    }

    fun convertDateFormat(input: String,
                          inputFormat: SimpleDateFormat,
                          outputFormat: SimpleDateFormat): String =
            outputFormat.format(inputFormat.parse(input)!!)
}
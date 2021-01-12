package com.jk.price

import android.util.Log
import androidx.lifecycle.MutableLiveData
import java.lang.NumberFormatException

class Number(var number: Double = 0.toDouble(),
             val string: MutableLiveData<String> = MutableLiveData(),
             private val percentString: String){

    fun update(number: Double){
        this.number = number
        if (number.toInt().toDouble() == number)
            string.value = number.toInt().toString()
        else
            string.value = number.toString()
    }

    fun update(string: String){
        this.string.value = string
        if (string.isEmpty())
            number = 0.toDouble()
        else {
            try {
                number = if (string.contains(percentString)){
                    string.substring(0, string.length-1).toDouble() / 100
                } else
                    string.toDouble()
            } catch (e: NumberFormatException) {
                Log.e("", e.message!!)
            }
        }
    }

}
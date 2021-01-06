package com.jk.price

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val resources: Resources = application.resources

    private val inputDateFormat: SimpleDateFormat =
            SimpleDateFormat("yyyyMd", Locale.KOREAN)
    private val outputDateFormat: SimpleDateFormat =
            SimpleDateFormat("yyyy년 M월 d일 E요일", Locale.KOREAN)

    private val emptyString: String = getResourceString(R.string.emptyString)

    private var repository: Repository? = null

    val yearList = MutableLiveData(generateList(2000, 2050))
    val monthList = MutableLiveData(generateList(1, 12))
    val dayList: LiveData<ArrayList<String>>

    val unitList: ArrayList<String> =
            arrayListOf(getResourceString(R.string.unitGeneral),
                    getResourceString(R.string.unitG),
                    getResourceString(R.string.unitML))

    val todayYear: String
    val todayMonth: String
    val todayDay: String

    val year = MutableLiveData(emptyString)
    val month = MutableLiveData(emptyString)
    val day = MutableLiveData(emptyString)
    val date: LiveData<String>
    val market = MutableLiveData(emptyString)
    val name = MutableLiveData(emptyString)
    val cost = MutableLiveData(emptyString)
    val unitCost: LiveData<String>
    val type = MutableLiveData(emptyString)
    val unit = MutableLiveData(emptyString)
    val count = MutableLiveData(emptyString)

    val saveEnable: LiveData<Boolean>

    var searchResult: LiveData<List<Purchase>>? = null

    private val day28 = generateList(1, 28)
    private val day29 = generateList(1, 29)
    private val day30 = generateList(1, 30)
    private val day31 = generateList(1, 31)

    init{
        val cal: Calendar = Calendar.getInstance()
        todayYear = cal.get(Calendar.YEAR).toString()
        year.value = todayYear
        todayMonth = (cal.get(Calendar.MONTH) + 1).toString()
        month.value = todayMonth
        todayDay = cal.get(Calendar.DATE).toString()
        day.value = todayDay

        repository = Repository.getInstance(application)

        searchResult = Transformations.switchMap(name) { input ->
            repository!!.search(input)
        }

        date = Transformations.switchMap(year) { yearString ->
            Transformations.switchMap(month) { monthString ->
                Transformations.map(day) { dayString ->
                    convertDateFormat(yearString + monthString + dayString,
                            inputDateFormat, outputDateFormat)
                }
            }
        }

        dayList = Transformations.switchMap(year){
            yearString -> Transformations.map(month){
                monthString -> getDayList(yearString, monthString)
            }
        }

        unitCost = Transformations.switchMap(cost) {
            costString -> Transformations.switchMap(count) {
                countString -> Transformations.map(unit) {
                    unitCost(costString, countString)
                }
            }
        }

        saveEnable = Transformations.switchMap(date) { dateString ->
            Transformations.switchMap(market) { marketString ->
                Transformations.switchMap(type) { typeString ->
                    Transformations.switchMap(name) { nameString ->
                        Transformations.switchMap(cost) { costString ->
                            Transformations.switchMap(count) { countString ->
                                Transformations.switchMap(unitCost) { unitCostString ->
                                    Transformations.map(unit) { unitString ->
                                        isNonEmpty(dateString,
                                                marketString,
                                                typeString,
                                                nameString,
                                                costString,
                                                countString,
                                                unitCostString,
                                                unitString)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun changeUnit(unit: String){
        this.unit.value = unit
    }

    private fun unitCost(costString: String, countString: String): String{
        if (countString.isEmpty())
            return emptyString
        if (costString.isEmpty())
            return emptyString
        val ret = costString.toDouble() / countString.toDouble()
        return String.format("%.1f", ret)
    }

    private fun convertDateFormat(input: String,
                                  inputFormat: SimpleDateFormat,
                                  outputFormat: SimpleDateFormat): String =
            outputFormat.format(inputFormat.parse(input)!!)

    private fun getDayList(year: String, month: String): ArrayList<String>{
        val y = year.toInt()
        when (month.toInt()){
            1, 3, 5, 7, 8, 10, 12 -> return day31
            4, 6, 9, 11 -> return day30
            else -> {
                if (y.rem(400) == 0)
                    return day29
                if (y.rem(100) == 0)
                    return day28
                if (y.rem(4) == 0)
                    return day29
                return day28
            }
        }
    }

    fun changeYear(year: String){
        this.year.value = year
    }

    fun changeMonth(month: String){
        this.month.value = month
    }

    fun changeDay(day: String){
        this.day.value = day
    }

    private fun isNonEmpty(vararg strings: String): Boolean{
        for (string in strings){
            if (string.isEmpty())
                return false
        }
        return true
    }

    fun savePurchase(){
        val purchase = Purchase()

        purchase.date = date.value!!
        purchase.market = market.value!!
        purchase.type = type.value!!
        purchase.name = name.value!!
        purchase.cost = cost.value!!.toInt()
        purchase.count = count.value!!.toInt()
        purchase.unitCost = unitCost.value!!.toDouble()
        purchase.unit = unit.value!!

        repository!!.insert(purchase)

        type.value = emptyString
        name.value = emptyString
        cost.value = emptyString
        count.value = emptyString
    }

    fun search(name: String): LiveData<List<Purchase>> = searchResult!!

    private fun getResourceString(ID: Int) = resources.getString(ID)

    private fun generateList(a: Int, b: Int): ArrayList<String>{
        val arrayList = ArrayList<String>()
        for (i in a..b){
            arrayList.add(i.toString())
        }
        return arrayList
    }

}
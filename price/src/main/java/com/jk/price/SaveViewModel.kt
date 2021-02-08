package com.jk.price

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.jk.price.MyResource.emptyString
import kotlin.collections.ArrayList

class SaveViewModel: ViewModel() {

    val dayList: LiveData<ArrayList<String>>
    val date: LiveData<String>
    val market = MutableLiveData(emptyString)
    val name = MutableLiveData(emptyString)
    val cost = MutableLiveData(emptyString)
    val unitCost: LiveData<String>
    val type = MutableLiveData(emptyString)
    val unit = MutableLiveData(emptyString)
    val count = MutableLiveData(emptyString)
    val memo = MutableLiveData(emptyString)
    val saveEnable: LiveData<Boolean>
    private val year = MutableLiveData(MyDate.getTodayYear())
    private val month = MutableLiveData(MyDate.getTodayMonth())
    private val day = MutableLiveData(MyDate.getTodayDay())

    init{
        date = Transformations.switchMap(year) { yearString ->
            Transformations.switchMap(month) { monthString ->
                Transformations.map(day) { dayString ->
                    MyDate.convertDateFormat(yearString + monthString + dayString,
                            MyDate.dateFormat1, MyDate.dateFormat0)
                }
            }
        }

        dayList = Transformations.switchMap(year){
            yearString -> Transformations.map(month){
                monthString -> MyDate.getDayList(yearString, monthString)
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

    fun changeYear(year: String){
        this.year.value = year
    }

    fun changeMonth(month: String){
        this.month.value = month
    }

    fun changeDay(day: String){
        this.day.value = day
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
        purchase.memo = memo.value!!

        Repository.insert(purchase)

        type.value = emptyString
        name.value = emptyString
        cost.value = emptyString
        count.value = emptyString
        memo.value = emptyString
    }

    private fun unitCost(costString: String, countString: String): String{
        if (countString.isEmpty())
            return emptyString
        if (costString.isEmpty())
            return emptyString
        val ret = costString.toDouble() / countString.toDouble()
        return String.format("%.1f", ret)
    }

    private fun isNonEmpty(vararg strings: String): Boolean{
        for (string in strings){
            if (string.isEmpty())
                return false
        }
        return true
    }
}
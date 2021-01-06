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

    private val emptyString: String = resources.getString(R.string.emptyString)
    private val button0: String = getResourceString(R.string.button0)
    private val buttonPercent: String = getResourceString(R.string.buttonPercent)
    private val buttonPoint: String = getResourceString(R.string.buttonPoint)
    private val buttonAdd: String = getResourceString(R.string.buttonAdd)
    private val buttonSub: String = getResourceString(R.string.buttonSub)
    private val buttonMul: String = getResourceString(R.string.buttonMul)
    private val buttonDiv: String = getResourceString(R.string.buttonDiv)

    private var repository: Repository? = null

    val yearList = MutableLiveData(generateList(2000, 2050))
    val monthList = MutableLiveData(generateList(1, 12))
    val dayList: LiveData<ArrayList<String>>

    val unitList: ArrayList<String> =
            arrayListOf(getResourceString(R.string.unitGeneral),
                    getResourceString(R.string.unitG),
                    getResourceString(R.string.unitML))

    private val operatorString = MutableLiveData<String>()
    private val operand0 = Number(0.toDouble(), MutableLiveData(), buttonPercent)
    private val operand1 = Number(0.toDouble(), MutableLiveData(), buttonPercent)
    private val result = Number(0.toDouble(), MutableLiveData(), buttonPercent)

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

    private val history: ArrayList<Calculation> = ArrayList()

    private var target: Target = Target.Operand0 // Either Target.Operand0 or Target.Operand1

    init{
        val cal: Calendar = Calendar.getInstance()
        todayYear = cal.get(Calendar.YEAR).toString()
        year.value = todayYear
        todayMonth = (cal.get(Calendar.MONTH) + 1).toString()
        month.value = todayMonth
        todayDay = cal.get(Calendar.DATE).toString()
        day.value = todayDay

        initialize()

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

    fun isNonEmpty(vararg strings: String): Boolean{
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

    fun press(buttonType: ButtonType, buttonString: String) {
        when (buttonType) {
            ButtonType.Number -> pressNumber(buttonString)
            ButtonType.Point -> pressPoint()
            ButtonType.Percent -> pressPercent()
            ButtonType.Negative -> pressNegative()
            ButtonType.Back -> pressBack()
            ButtonType.Operator -> pressOperator(buttonString)
            ButtonType.Result -> pressResult()
            ButtonType.AllClear -> pressAllClear()
            ButtonType.Clear -> pressClear()
        }
    }

    private fun initialize(){
        pressAllClear()
    }

    private fun pressNumber(buttonString: String){
        var newString = getOperandString()
        if ((newString == "0") || (newString == "00")){
            newString = buttonString
        } else
            newString += buttonString
        updateOperand(newString)
        if (target == Target.Operand1){
            calculate()
        }
    }

    private fun pressPoint(){
        var newString = getOperandString()
        if (!newString.contains(buttonPoint)){
            if (newString.isEmpty())
                newString = button0 + buttonPoint
            else
                newString += buttonPoint
            updateOperand(newString)
        }
    }

    private fun pressPercent(){
        val newString = getOperandString()
        if (!newString.contains(buttonPercent) && newString.isNotEmpty()){
            updateOperand(newString + buttonPercent)
            if (target == Target.Operand1){
                calculate()
            }
        }
    }

    private fun pressNegative(){
        updateOperand(-getOperandNumber())
        if (target == Target.Operand1){
            calculate()
        }
    }

    private fun pressBack(){
        val newString = getOperandString()
        val length: Int = newString.length
        if (length > 0) {
            updateOperand(newString.substring(0, length - 1))
        }
        else {
            operatorString.value = emptyString
            updateResult(emptyString)
            target = Target.Operand0
        }
        calculate()
        if (length == 1)
            updateResult(emptyString)
    }

    private fun pressOperator(buttonString: String){
        operatorString.value = buttonString
        if (target == Target.Operand0)
            target = Target.Operand1
        else{
            save()
            operand0.update(getResultString())
            operand1.update(emptyString)
            updateResult(emptyString)
        }
    }

    private fun pressResult(){
        save()
        pressClear()
    }

    private fun pressAllClear(){
        pressClear()
        history.clear()
    }

    private fun pressClear(){
        target = Target.Operand0
        operand0.update(emptyString)
        operand1.update(emptyString)
        updateResult(emptyString)
        operatorString.value = emptyString
    }

    private fun getOperandString(): String{
        return if (target == Target.Operand0)
            operand0.string.value!!
        else
            operand1.string.value!!
    }

    private fun getOperandNumber(): Double{
        return if (target == Target.Operand0)
            operand0.number
        else
            operand1.number
    }

    private fun updateOperand(newString: String){
        if (target == Target.Operand0)
            operand0.update(newString)
        else
            operand1.update(newString)
    }

    private fun updateOperand(newDouble: Double){
        if (target == Target.Operand0)
            operand0.update(newDouble)
        else
            operand1.update(newDouble)
    }

    private fun getResultString(): String{
        return result.string.value!!
    }

    private fun getResultNumber(): Double{
        return result.number
    }

    private fun updateResult(string: String){
        result.update(string)
    }

    private fun updateResult(newDouble: Double){
        result.update(newDouble)
    }

    private fun calculate() {
        when (operatorString.value) {
            buttonAdd -> {
                updateResult(operand0.number + operand1.number)
            }
            buttonSub -> {
                updateResult(operand0.number - operand1.number)
            }
            buttonMul -> {
                updateResult(operand0.number * operand1.number)
            }
            buttonDiv -> {
                updateResult(operand0.number / operand1.number)
            }
        }
    }

    private fun save(){
        val historyItem = Calculation(
                operand0.string.value!!,
                operand1.string.value!!,
                result.string.value!!,
                operatorString.value!!
        )
        history.add(historyItem)
    }

    private fun getResourceString(ID: Int) = resources.getString(ID)

    private fun generateList(a: Int, b: Int): ArrayList<String>{
        val arrayList = ArrayList<String>()
        for (i in a..b){
            arrayList.add(i.toString())
        }
        return arrayList
    }

    private enum class Target{
        Operand0, Operand1
    }

    private inner class Calculation(var operand0: String,
                                    var operand1: String,
                                    var result: String,
                                    var operator: String)
}
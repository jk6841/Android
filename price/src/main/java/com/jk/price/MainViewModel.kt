package com.jk.price

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val resources: Resources = application.resources

    private val emptyString: String = resources.getString(R.string.emptyString)
    private val button0: String = resources.getString(R.string.button0)
    private val buttonPercent: String = resources.getString(R.string.buttonPercent)
    private val buttonPoint: String = resources.getString(R.string.buttonPoint)
    private val buttonAdd: String = resources.getString(R.string.buttonAdd)
    private val buttonSub: String = resources.getString(R.string.buttonSub)
    private val buttonMul: String = resources.getString(R.string.buttonMul)
    private val buttonDiv: String = resources.getString(R.string.buttonDiv)
    val operatorString: MutableLiveData<String> = MutableLiveData<String>()
    val operand0: Number = Number(0.toDouble(), MutableLiveData(), buttonPercent)
    val operand1: Number = Number(0.toDouble(), MutableLiveData(), buttonPercent)
    val result: Number = Number(0.toDouble(), MutableLiveData(), buttonPercent)

    private val history: ArrayList<Calculation> = ArrayList()

    private var target: Target = Target.Operand0 // Either Target.Operand0 or Target.Operand1

    init{
        initialize()
    }

    fun press(buttonType: ButtonType, buttonString: String){
        when (buttonType){
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

    private enum class Target{
        Operand0, Operand1
    }

    private inner class Calculation(var operand0: String,
                                    var operand1: String,
                                    var result: String,
                                    var operator: String)
}
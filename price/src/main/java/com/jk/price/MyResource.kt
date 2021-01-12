package com.jk.price

import android.app.Application
import androidx.core.content.ContextCompat

object MyResource{

    fun getString(application: Application, stringID: Int) =
            application.resources.getString(stringID)

    fun getColor(application: Application, colorID: Int) =
            ContextCompat.getColor(application, colorID)

    //// List ////
    val yearList = MyList.generateList(2000, 2050)
    val monthList = MyList.generateList(1, 12)
    val day28 = MyList.generateList(1, 28)
    val day29 = MyList.generateList(1, 29)
    val day30 = MyList.generateList(1, 30)
    val day31 = MyList.generateList(1, 31)
    val unitList = arrayListOf("원/개", "원/g", "원/ml")
    val buttonNum = MyList.generateList(0, 9)

    //// String ////
    const val emptyString = ""
    const val button00 = "00"
    const val buttonNegative = "-"
    const val buttonResult = "＝" 
    const val buttonPoint = "." 
    const val buttonBack = "←" 
    const val buttonAdd = "＋" 
    const val buttonSub = "－" 
    const val buttonMul = "×" 
    const val buttonDiv = "÷" 
    const val buttonPercent = "％" 
    const val buttonAllClear = "AC" 
    const val buttonClear = "C"
    
    const val type = "제품 종류"
    const val name = "제품 이름"
    const val market = "구매처"
    const val count = "수량"
    const val cost = "가격"
    const val unitCost = "단위 가격"
    const val date = "날짜"
    const val condition = "검색 조건"
    const val result = "검색 결과"
    const val year = "년"
    const val month = "월"
    const val day = "일"
    const val memo = "메모"
    const val unit = "단위"
    const val save = "저장"
    const val saveSuccess = "저장하였습니다"

}
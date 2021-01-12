package com.jk.price

object MyList {
    fun generateList(start: Int, end: Int): ArrayList<String>{
        val arrayList = ArrayList<String>()
        for (i in start..end){
            arrayList.add(i.toString())
        }
        return arrayList
    }
}
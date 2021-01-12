package com.jk.price

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class SearchViewModel: ViewModel() {

    var searchResult: LiveData<List<Purchase>>? = null

    val market = MutableLiveData(MyResource.emptyString)
    val type = MutableLiveData(MyResource.emptyString)
    val name = MutableLiveData(MyResource.emptyString)


    init{
        searchResult = Transformations.switchMap(market) {
            marketString -> Transformations.switchMap(type) {
                typeString -> Transformations.switchMap(name) {
                    nameString -> Repository.search(marketString, typeString, nameString)
                }
            }
        }
    }

    fun delete(ID: Int){
        Repository.delete(ID)
    }



}
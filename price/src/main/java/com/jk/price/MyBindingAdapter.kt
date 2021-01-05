package com.jk.price

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.widget.AppCompatSpinner
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jk.price.fragment.SearchFragment

object MyBindingAdapter {
    @BindingAdapter(value = ["list", "holder"])
    @JvmStatic
    fun recyclerViewAdapter(view: RecyclerView, list: List<Purchase>?, holder: Int){
        var adapter: MyRecyclerViewAdapter? = view.adapter as MyRecyclerViewAdapter?
        if (view.adapter == null){
            adapter = MyRecyclerViewAdapter(holder)
            view.adapter = adapter
        }
        adapter!!.list = list
        adapter.notifyDataSetChanged()
    }

//    @BindingAdapter(value = ["spinnerList", "context", "spinnerTag"])
//    @JvmStatic
//    fun spinnerAdapter(spinner: Spinner,
//                       list: ArrayList<String>,
//                       context: Context,
//                       onItemSelectedListener: SearchFragment.OnItemSelectedListener
//                       tag: String){
//        spinner.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, list)
//        spinner.onItemSelectedListener = onItemSelectedListener
//
//    }

}
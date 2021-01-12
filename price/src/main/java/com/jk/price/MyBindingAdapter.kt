package com.jk.price

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jk.price.fragment.SearchFragment

object MyBindingAdapter {
    @BindingAdapter(value = ["list", "holder", "onDeleteClick"])
    @JvmStatic
    fun recyclerViewAdapter(view: RecyclerView,
                            list: List<Purchase>?,
                            holder: Int,
                            onDeleteClick: SearchFragment.OnDeleteClick){
        var adapter: MyRecyclerViewAdapter? = view.adapter as MyRecyclerViewAdapter?
        if (view.adapter == null){
            adapter = MyRecyclerViewAdapter(holder)
            view.adapter = adapter
        }
        adapter!!.list = list
        adapter.notifyDataSetChanged()
        adapter.onDeleteClick = onDeleteClick
    }
}
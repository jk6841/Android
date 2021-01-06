package com.jk.price

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jk.price.databinding.ViewholderBinding

class MyRecyclerViewAdapter(var layout: Int):
        RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>() {

    var list: List<Purchase>?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        return MyViewHolder(inflater.inflate(layout, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list!![position])
    }

    override fun getItemCount(): Int {
        if (list == null)
            return 0
        return list!!.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ViewholderBinding?= null

        init{
            binding = DataBindingUtil.bind(itemView)
        }

        fun bind(purchase: Purchase){
            binding!!.purchase = purchase
            binding!!.executePendingBindings()
        }
    }
}
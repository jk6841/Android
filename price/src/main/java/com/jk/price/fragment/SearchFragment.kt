package com.jk.price.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.jk.price.MainActivity
import com.jk.price.R
import com.jk.price.SearchViewModel
import com.jk.price.databinding.FragmentSearchBinding

class SearchFragment: MyFragment<FragmentSearchBinding, SearchViewModel>(R.layout.fragment_search){

    override fun setViewModel() {
        viewModel = (activity as MainActivity).searchViewModel
    }

    override fun setupDataBinding(inflater: LayoutInflater, layoutID: Int, container: ViewGroup?) {
        super.setupDataBinding(inflater, layoutID, container)
        binding!!.onDeleteClick = OnDeleteClick()

    }

    inner class OnDeleteClick{
        fun onClick(ID: Int){
            viewModel!!.delete(ID)
        }
    }

}
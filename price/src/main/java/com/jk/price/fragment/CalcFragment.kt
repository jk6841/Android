package com.jk.price.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jk.price.*
import com.jk.price.databinding.FragmentCalcBinding

class CalcFragment: MyFragment<FragmentCalcBinding, CalcViewModel>(R.layout.fragment_calc) {

    override fun setViewModel() {
        viewModel = (activity as MainActivity).calcViewModel
    }

    override fun setupDataBinding(inflater: LayoutInflater, layoutID: Int, container: ViewGroup?) {
        super.setupDataBinding(inflater, layoutID, container)
        binding!!.onButtonPress = OnButtonPress()
    }

    inner class OnButtonPress{
        fun onPress(buttonType: ButtonType, buttonString: String){
            viewModel!!.press(buttonType, buttonString)
        }
    }

}
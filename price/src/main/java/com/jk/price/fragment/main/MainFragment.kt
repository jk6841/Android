package com.jk.price.fragment.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.jk.price.*
import com.jk.price.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    lateinit var binding: MainFragmentBinding

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.main_fragment, container, false)
        val root: View = binding.root
        binding.lifecycleOwner = this
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        binding.viewModel = viewModel
        binding.onButtonPress = OnButtonPress()
    }

    inner class OnButtonPress{
        fun onPress(buttonType: ButtonType, buttonString: String){
            viewModel.press(buttonType, buttonString)
        }
    }

}
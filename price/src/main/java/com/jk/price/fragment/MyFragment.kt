package com.jk.price.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.jk.price.MyResource

abstract class MyFragment<BINDING: ViewDataBinding, VM: ViewModel>(private var layoutID: Int) :
        Fragment()
{

    protected var binding: BINDING? = null
    protected var viewModel: VM? = null

    final override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setViewModel()
        setupDataBinding(inflater, layoutID, container)
        return binding?.root
    }

    final override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    open fun setupDataBinding(inflater: LayoutInflater, layoutID: Int, container: ViewGroup?){
        binding = DataBindingUtil.inflate(inflater, layoutID, container, false)
        binding!!.lifecycleOwner = this
        binding!!.setVariable(BR.viewModel, viewModel)
        binding!!.setVariable(BR.myResource, MyResource)
    }

    abstract fun setViewModel()

}
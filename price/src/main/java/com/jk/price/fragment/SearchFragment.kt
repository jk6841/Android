package com.jk.price.fragment

import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.jk.price.*
import com.jk.price.databinding.MainFragmentBinding

class SearchFragment : Fragment() {

    lateinit var binding: MainFragmentBinding
    var spinner: Spinner? = null

    companion object {
        fun newInstance() = SearchFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val resources: Resources = activity?.applicationContext!!.resources
        binding = DataBindingUtil.inflate(
                inflater, R.layout.main_fragment, container, false)
        val root: View = binding.root
        viewModel = (activity as MainActivity).viewModel
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.data.dateSpinner.year.onItemSelectedListener =
                OnItemSelectedListener(resources.getString(R.string.yearTag))
        binding.data.dateSpinner.month.onItemSelectedListener =
                OnItemSelectedListener(resources.getString(R.string.monthTag))
        binding.data.dateSpinner.day.onItemSelectedListener =
                OnItemSelectedListener(resources.getString(R.string.dayTag))
        binding.data.unitSpinner.onItemSelectedListener =
                OnItemSelectedListener(resources.getString(R.string.unitTag))
        return root
    }

    inner class OnButtonPress {
        fun onPress(buttonType: ButtonType, buttonString: String) {
            viewModel.press(buttonType, buttonString)
        }
    }

    inner class OnItemSelectedListener(val tag: String): AdapterView.OnItemSelectedListener{

        var isFirst: Boolean = true

        override fun onItemSelected(parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long) {
            if (isFirst){
                when (tag){
                    getApplicationString(R.string.yearTag) ->
                        parent?.setSelection(
                                viewModel.todayYear.toInt() - viewModel.yearList.value!![0].toInt())
                    getApplicationString(R.string.monthTag) ->
                        parent?.setSelection(
                                viewModel.todayMonth.toInt() - viewModel.monthList.value!![0].toInt())
                    getApplicationString(R.string.dayTag) ->
                        parent?.setSelection(
                                viewModel.todayDay.toInt() - viewModel.dayList.value!![0].toInt())
                }
                isFirst = false
            }
            val item: String = parent?.getItemAtPosition(position) as String
            when (tag){
                getApplicationString(R.string.yearTag) ->
                    viewModel.changeYear(item)
                getApplicationString(R.string.monthTag) ->
                    viewModel.changeMonth(item)
                getApplicationString(R.string.dayTag) ->
                    viewModel.changeDay(item)
                getApplicationString(R.string.unitTag) ->
                    viewModel.changeUnit(item)
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }

    private fun getApplicationString(ID: Int): String =
            activity?.applicationContext!!.getString(ID)

}
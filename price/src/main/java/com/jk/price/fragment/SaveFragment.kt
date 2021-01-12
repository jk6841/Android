package com.jk.price.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import com.jk.price.*
import com.jk.price.databinding.FragmentSaveBinding

class SaveFragment : MyFragment<FragmentSaveBinding, SaveViewModel>(R.layout.fragment_save) {

    override fun setViewModel() {
        viewModel = (activity as MainActivity).saveViewModel
    }

    override fun setupDataBinding(inflater: LayoutInflater,
                                  layoutID: Int,
                                  container: ViewGroup?) {
        super.setupDataBinding(inflater, layoutID, container)
        setupDateSpinnerBinding()
        binding!!.unitSpinner.onItemSelectedListener = OnItemSelectedListener(Tag.UnitTag)
        binding!!.saveOnClickListener = OnClickSave()
    }

    private fun setupDateSpinnerBinding(){
        val spinner = binding!!.dateSpinner
        spinner.myResource = MyResource
        spinner.year.onItemSelectedListener = OnItemSelectedListener(Tag.YearTag)
        spinner.month.onItemSelectedListener = OnItemSelectedListener(Tag.MonthTag)
        spinner.day.onItemSelectedListener = OnItemSelectedListener(Tag.DayTag)
    }

    enum class Tag{
        YearTag,
        MonthTag,
        DayTag,
        UnitTag
    }

    inner class OnClickSave: View.OnClickListener{
        override fun onClick(v: View?) {
            viewModel!!.savePurchase()
            Toast.makeText(activity?.applicationContext,
                    MyResource.saveSuccess,
                    Toast.LENGTH_SHORT)
                    .show()
        }
    }

    inner class OnItemSelectedListener(private val tag: Tag): AdapterView.OnItemSelectedListener{

        var isFirst: Boolean = true

        override fun onItemSelected(parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long) {
            if (isFirst){
                when (tag){
                    Tag.YearTag ->
                        parent?.setSelection(
                                MyDate.getTodayYear().toInt() - MyResource.yearList[0].toInt())
                    Tag.MonthTag ->
                        parent?.setSelection(
                                MyDate.getTodayMonth().toInt() -1)
                    Tag.DayTag ->
                        parent?.setSelection(
                                MyDate.getTodayDay().toInt() - 1)
                }
                isFirst = false
            }
            val item: String = parent?.getItemAtPosition(position) as String
            when (tag){
                Tag.YearTag ->
                    viewModel!!.changeYear(item)
                Tag.MonthTag ->
                    viewModel!!.changeMonth(item)
                Tag.DayTag ->
                    viewModel!!.changeDay(item)
                Tag.UnitTag ->
                    viewModel!!.changeUnit(item)
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }

}
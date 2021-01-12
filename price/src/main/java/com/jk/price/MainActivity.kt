package com.jk.price

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.jk.price.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    var binding: MainActivityBinding?= null
    var mainViewModel: MainViewModel? = null
    var saveViewModel: SaveViewModel? = null
    var calcViewModel: CalcViewModel? = null
    var searchViewModel: SearchViewModel? = null
    var androidViewModelFactory: ViewModelProvider.Factory? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        binding?.lifecycleOwner = this
        NavigationUI.setupWithNavController(binding!!.navView,
                Navigation.findNavController(this, R.id.fragment))
        androidViewModelFactory = ViewModelProvider.AndroidViewModelFactory(application)
        createViewModel()
    }

    private fun createViewModel(){
        mainViewModel = ViewModelProvider(this, androidViewModelFactory!!)
                .get(MainViewModel::class.java)
        saveViewModel = ViewModelProvider(this).get(SaveViewModel::class.java)
        calcViewModel = ViewModelProvider(this).get(CalcViewModel::class.java)
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
    }

}
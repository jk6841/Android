package com.jk.price

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class MainViewModel(application: Application) : AndroidViewModel(application) {

    init{
        Repository.setupRepository(application)
    }


}
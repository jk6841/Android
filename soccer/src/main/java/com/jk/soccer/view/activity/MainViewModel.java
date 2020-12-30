package com.jk.soccer.view.activity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.jk.soccer.model.Repository;

public class MainViewModel extends AndroidViewModel {

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
    }

    @Override
    protected void onCleared() {
        repository.close();
        super.onCleared();
    }

    final private Repository repository;
}

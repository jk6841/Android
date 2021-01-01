package com.jk.soccer.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jk.soccer.model.Repository;

public class LeagueInfoViewModel extends ViewModel {

    public LeagueInfoViewModel() {
        repository = Repository.getInstance();
        name = new MutableLiveData<>();
    }

    public void getLeagueInfoAsync(Integer ID){
        repository.getLeagueInfoAsync(ID, league -> {
            name.postValue(league.getName());
        });
    }

    public MutableLiveData<String> getName() {
        return name;
    }

    final private Repository repository;
    final private MutableLiveData<String> name;

}

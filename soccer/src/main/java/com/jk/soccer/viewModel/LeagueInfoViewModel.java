package com.jk.soccer.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jk.soccer.model.LeagueTableEntry;
import com.jk.soccer.model.Repository;

import java.util.List;

public class LeagueInfoViewModel extends ViewModel {

    public LeagueInfoViewModel() {
        repository = Repository.getInstance();
        name = new MutableLiveData<>();
        table = new MutableLiveData<>();
    }

    public void getLeagueInfoAsync(Integer ID){
        repository.getLeagueInfoAsync(ID, league -> {
            name.postValue(league.getName());
            table.postValue(league.getTable());
        });
    }

    public MutableLiveData<String> getName() {
        return name;
    }

    public MutableLiveData<List<LeagueTableEntry>> getTable() {
        return table;
    }

    final private Repository repository;
    final private MutableLiveData<String> name;
    final private MutableLiveData<List<LeagueTableEntry>> table;

}

package com.jk.soccer.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jk.soccer.model.Fixture;
import com.jk.soccer.model.LeagueTableEntry;
import com.jk.soccer.model.Repository;
import com.jk.soccer.model.TopPlayer;

import java.util.List;

public class LeagueInfoViewModel extends ViewModel {

    public LeagueInfoViewModel() {
        repository = Repository.getInstance();
        name = new MutableLiveData<>();
        table = new MutableLiveData<>();
        topGoal = new MutableLiveData<>();
        topAssist = new MutableLiveData<>();
        fixture = new MutableLiveData<>();
    }

    public void getLeagueInfoAsync(Integer ID){
        repository.getLeagueInfoAsync(ID, league -> {
            name.postValue(league.getName());
            table.postValue(league.getTable());
            topGoal.postValue(league.getTopGoal());
            topAssist.postValue(league.getTopAssist());
            fixture.postValue(league.getFixtures());
        });
    }

    public MutableLiveData<String> getName() {
        return name;
    }

    public MutableLiveData<List<LeagueTableEntry>> getTable() {
        return table;
    }

    public MutableLiveData<List<TopPlayer>> getTopGoal() {
        return topGoal;
    }

    public MutableLiveData<List<TopPlayer>> getTopAssist() {
        return topAssist;
    }

    public MutableLiveData<List<Fixture>> getFixture() {
        return fixture;
    }

    final private Repository repository;
    final private MutableLiveData<String> name;
    final private MutableLiveData<List<LeagueTableEntry>> table;
    final private MutableLiveData<List<TopPlayer>> topGoal;
    final private MutableLiveData<List<TopPlayer>> topAssist;
    final private MutableLiveData<List<Fixture>> fixture;

}

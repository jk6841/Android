package com.jk.soccer.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jk.soccer.etc.Fixture;
import com.jk.soccer.etc.TopPlayer;
import com.jk.soccer.model.Repository;

import java.util.List;

public class TeamInfoViewModel extends ViewModel {

    public TeamInfoViewModel() {
        repository = Repository.getInstance();
        name = new MutableLiveData<>();
        stadium = new MutableLiveData<>();
        fixtures = new MutableLiveData<>();
        topGoal = new MutableLiveData<>();
        topAssist = new MutableLiveData<>();
    }

    public void init(){
        name.setValue("");
    }

    public void getTeamInfo(Integer ID){
        repository.getTeamInfoAsync(ID, team -> {
            name.postValue(team.getName());
            StringBuilder sb = new StringBuilder(team.getStadium());
            sb.append("(");
            sb.append(team.getCity());
            sb.append(")");
            stadium.postValue(sb.toString());
            fixtures.postValue(team.getFixtures());
            topGoal.postValue(team.getTopGoal());
            topAssist.postValue(team.getTopAssist());
        });
    }

    public MutableLiveData<String> getName() {
        return name;
    }

    public MutableLiveData<String> getStadium() {
        return stadium;
    }

    public MutableLiveData<List<Fixture>> getFixtures() {
        return fixtures;
    }

    public MutableLiveData<List<TopPlayer>> getTopGoal() {
        return topGoal;
    }

    public MutableLiveData<List<TopPlayer>> getTopAssist() {
        return topAssist;
    }

    final private Repository repository;
    final private MutableLiveData<String> name;
    final private MutableLiveData<String> stadium;
    final private MutableLiveData<List<Fixture>> fixtures;
    final private MutableLiveData<List<TopPlayer>> topGoal;
    final private MutableLiveData<List<TopPlayer>> topAssist;

}

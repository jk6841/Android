package com.jk.soccer.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jk.soccer.model.Repository;

public class PlayerInfoViewModel extends ViewModel {
    public PlayerInfoViewModel() {
        repository = Repository.getInstance();
        name = new MutableLiveData<>();
        teamID = new MutableLiveData<>();
        team = new MutableLiveData<>();
        shirt = new MutableLiveData<>();
        position = new MutableLiveData<>();
        foot = new MutableLiveData<>();
        height = new MutableLiveData<>();
        age = new MutableLiveData<>();
        country = new MutableLiveData<>();
        countryID = new MutableLiveData<>();
    }

    public void init(){
        String emptyString = "";
        name.setValue(emptyString);
        teamID.setValue(emptyString);
        team.setValue(emptyString);
        shirt.setValue(emptyString);
        position.setValue(emptyString);
        foot.setValue(emptyString);
        height.setValue(emptyString);
        age.setValue(emptyString);
        country.setValue(emptyString);
        countryID.setValue(emptyString);
    }

    public void getPlayerInfo(Integer ID){
        repository.getPlayerInfoAsync(ID, result -> {
            name.postValue(result.getName());
            teamID.postValue(result.getTeamID());
            team.postValue(result.getTeamName());
            position.postValue(result.getPosition());
            height.postValue(result.getHeight());
            foot.postValue(result.getFoot());
            age.postValue(result.getAge());
            country.postValue(result.getCountryName());
            countryID.postValue(result.getCountryID());
            shirt.postValue(result.getShirt());
        });
    }

    public MutableLiveData<String> getName() {
        return name;
    }

    public MutableLiveData<String> getTeamID() {
        return teamID;
    }

    public MutableLiveData<String> getTeam() {
        return team;
    }

    public MutableLiveData<String> getShirt() {
        return shirt;
    }

    public MutableLiveData<String> getPosition() {
        return position;
    }

    public MutableLiveData<String> getFoot() {
        return foot;
    }

    public MutableLiveData<String> getHeight() {
        return height;
    }

    public MutableLiveData<String> getAge() {
        return age;
    }

    public MutableLiveData<String> getCountry() {
        return country;
    }

    public MutableLiveData<String> getCountryID() {
        return countryID;
    }

    final private Repository repository;

    final private MutableLiveData<String> name;
    final private MutableLiveData<String> teamID;
    final private MutableLiveData<String> team ;
    final private MutableLiveData<String> shirt;
    final private MutableLiveData<String> position;
    final private MutableLiveData<String> foot;
    final private MutableLiveData<String> height;
    final private MutableLiveData<String> age;
    final private MutableLiveData<String> country;
    final private MutableLiveData<String> countryID;
}

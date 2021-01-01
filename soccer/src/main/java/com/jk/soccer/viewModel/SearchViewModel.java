package com.jk.soccer.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.jk.soccer.etc.enumeration.Type;
import com.jk.soccer.model.Repository;
import com.jk.soccer.model.UpdateCallback;
import com.jk.soccer.model.local.TableSearch;

import java.util.List;

public class SearchViewModel extends ViewModel {

    public SearchViewModel() {
        repository = Repository.getInstance();
        name = new MutableLiveData<>();
        name.setValue("");
        leagueList = Transformations.switchMap(name, input -> repository.search(input, Type.LEAGUE));
        teamList = Transformations.switchMap(name, input -> repository.search(input, Type.TEAM));
        playerList = Transformations.switchMap(name, input -> repository.search(input, Type.PERSON));
        data = new MutableLiveData<>();
        update();
    }

    public void update(){
        data.setValue("데이터를 받는 중");
//        repository.updateDB(result -> {
//            data.postValue(result? today : "데이터 다운로드 실패");
//        });
        repository.updateDB(new UpdateCallback(data));
    }

    public MutableLiveData<String> getName() {
        return name;
    }

    public MutableLiveData<String> getData() {
        return data;
    }

    public LiveData<List<TableSearch>> getList() {
        return playerList;
    }

    public LiveData<List<TableSearch>> getLeagueList(){
        return leagueList;
    }

    public LiveData<List<TableSearch>> getTeamList() {
        return teamList;
    }

    public LiveData<List<TableSearch>> getPlayerList() {
        return playerList;
    }

    final private  Repository repository;
    final private LiveData<List<TableSearch>> leagueList;
    final private LiveData<List<TableSearch>> teamList;
    final private LiveData<List<TableSearch>> playerList;
    final private MutableLiveData<String> name;
    final private MutableLiveData<String> data;

}

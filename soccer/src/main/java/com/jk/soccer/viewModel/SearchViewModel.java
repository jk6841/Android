package com.jk.soccer.viewModel;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.jk.soccer.etc.enumeration.Type;
import com.jk.soccer.model.Repository;
import com.jk.soccer.model.local.TableSearch;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SearchViewModel extends ViewModel {

    public SearchViewModel() {
        repository = Repository.getInstance();
        name = new MutableLiveData<>();
        name.setValue("");
        leagueList = Transformations.switchMap(name, input -> repository.search(input, Type.LEAGUE));
        teamList = Transformations.switchMap(name, input -> repository.search(input, Type.TEAM));
        playerList = Transformations.switchMap(name, input -> repository.search(input, Type.PERSON));
        data = new MutableLiveData<>();
        today = "최근 업데이트: "
                + new SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA)
                .format(new Date());
        update();
    }

    public void update(){
        data.setValue("데이터를 받는 중");
        repository.updateDB(result -> {
            data.postValue(result? today : "데이터 다운로드 실패");
        });
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

    private final Repository repository;
    final private LiveData<List<TableSearch>> leagueList;
    final private LiveData<List<TableSearch>> teamList;
    final private LiveData<List<TableSearch>> playerList;
    final private MutableLiveData<String> name;
    final private MutableLiveData<String> data;
    final private String today;

}

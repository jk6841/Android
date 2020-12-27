package com.jk.soccer.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.jk.soccer.etc.Pair;
import com.jk.soccer.model.Repository;

import java.util.ArrayList;
import java.util.List;

public class SearchViewModel extends AndroidViewModel {

    public SearchViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
        emptyList = new ArrayList<>();
        leagueList = repository.getLeagueList();
        teamList = new ArrayList<>();
        playerList = new ArrayList<>();
        leagueIndex = new MutableLiveData<>();
        leagueIndex.setValue(-1);
        teamIndex = new MutableLiveData<>();
        teamIndex.setValue(-1);
        leagueHandler = new LeagueHandler(leagueIndex);
        teamHandler = new TeamHandler(teamIndex);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public void close() {
        onCleared();
    }

    private final Repository repository;
    final private List<Pair> emptyList;
    private List<Pair> leagueList;
    private List<Pair> teamList;
    private List<Pair> playerList;
    final private MutableLiveData<Integer> leagueIndex;
    final private MutableLiveData<Integer> teamIndex;
    final private LeagueHandler leagueHandler;
    final private TeamHandler teamHandler;



    public Handler getLeagueHandler(){
        return leagueHandler;
    }

    public Handler getTeamHandler(){
        return teamHandler;
    }

    public List<Pair> getEmptyList() {
        return emptyList;
    }

    public List<Pair> getLeagueList(){
        return leagueList;
    }

    public void newTeamList(){
        Integer leagueID = leagueList.get(leagueIndex.getValue()).getID();
        teamList = repository.getTeamList(leagueID);
    }

    public List<Pair> getTeamList(){
        return teamList;
    }

    public void newPlayerList(){
        Integer teamID = teamList.get(teamIndex.getValue()).getID();
        playerList = repository.getPlayerList(teamID);
    }

    public List<Pair> getPlayerList(){
        return playerList;
    }

    public MutableLiveData<Integer> getLeagueIndex(){
        return leagueIndex;
    }

    public void setLeagueIndex(Integer val){
        leagueIndex.setValue(val);
    }

    public MutableLiveData<Integer> getTeamIndex() {
        return teamIndex;
    }

    public void setTeamIndex(Integer val){
        teamIndex.setValue(val);
    }

    public static abstract class Handler {
        final protected MutableLiveData<Integer> index;
        public Handler(MutableLiveData<Integer> index) {
            this.index = index;
        }

        public abstract void setIndex(Integer index);
    }

    public class LeagueHandler extends Handler {
        public LeagueHandler(MutableLiveData<Integer> index) {
            super(index);
        }

        @Override
        public void setIndex(Integer index) {
            setLeagueIndex(index);
            newTeamList();
        }
    }

    public class TeamHandler extends Handler {
        public TeamHandler(MutableLiveData<Integer> index) {
            super(index);
        }

        @Override
        public void setIndex(Integer index) {
            setTeamIndex(index);
            newPlayerList();
        }
    }

}

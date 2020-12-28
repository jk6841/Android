package com.jk.soccer.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.jk.soccer.model.Repository;
import com.jk.soccer.model.local.TableLeague;
import com.jk.soccer.model.local.TablePlayer;
import com.jk.soccer.model.local.TableTeam;

import java.util.List;

public class SearchViewModel extends AndroidViewModel {

    public SearchViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
        leagueIndex = new MutableLiveData<>();
        leagueIndex.setValue(-1);
        teamIndex = new MutableLiveData<>();
        teamIndex.setValue(-1);
        leagueIndexManager = new LeagueIndexManager(leagueIndex);
        teamIndexManager = new TeamIndexManager(teamIndex);
        leagueList = repository.getLeagueList();
        teamList = Transformations.switchMap(leagueIndex, repository::getTeamList);
        playerList = Transformations.switchMap(teamIndex, repository::getPlayerList);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository.close();
    }

    public void close() {
        onCleared();
    }

    public LiveData<List<TableLeague>> getLeagueList() {
        return leagueList;
    }

    public LiveData<List<TableTeam>> getTeamList(){
        return teamList;
    }

    public LiveData<List<TablePlayer>> getPlayerList(){
        return playerList;
    }

    public Handler getLeagueIndexManager(){
        return leagueIndexManager;
    }

    public Handler getTeamIndexManager(){
        return teamIndexManager;
    }

    public MutableLiveData<Integer> getLeagueIndex() {
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

    public class LeagueIndexManager extends Handler {
        public LeagueIndexManager(MutableLiveData<Integer> index) {
            super(index);
        }

        @Override
        public void setIndex(Integer index) {
            setLeagueIndex(index);
            setTeamIndex(-1);
        }
    }

    public class TeamIndexManager extends Handler {
        public TeamIndexManager(MutableLiveData<Integer> index) {
            super(index);
        }

        @Override
        public void setIndex(Integer index) {
            setTeamIndex(index);
        }
    }

    private final Repository repository;
    final private LiveData<List<TableLeague>> leagueList;
    final private LiveData<List<TableTeam>> teamList;
    final private LiveData<List<TablePlayer>> playerList;
    final private MutableLiveData<Integer> leagueIndex;
    final private MutableLiveData<Integer> teamIndex;
    final private LeagueIndexManager leagueIndexManager;
    final private TeamIndexManager teamIndexManager;

}

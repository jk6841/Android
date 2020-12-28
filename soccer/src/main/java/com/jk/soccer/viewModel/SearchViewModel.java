package com.jk.soccer.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.jk.soccer.etc.Pair;
import com.jk.soccer.model.Repository;

import java.util.List;

public class SearchViewModel extends AndroidViewModel {

    public SearchViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
        leagueIndex = new MutableLiveData<>();
        leagueIndex.setValue(-1);
        teamIndex = new MutableLiveData<>();
        teamIndex.setValue(-1);
        leagueHandler = new LeagueHandler(leagueIndex);
        teamHandler = new TeamHandler(teamIndex);
        leagueList = repository.getLeagueList();
        teamList = Transformations.switchMap(leagueIndex,
                repository::getTeamList);
        playerList = Transformations.switchMap(teamIndex,
                new Function<Integer, LiveData<List<Pair>>>() {
                    @Override
                    public LiveData<List<Pair>> apply(Integer input) {
                        return repository.getPlayerList(teamIndex.getValue());
                    }
                });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public void close() {
        onCleared();
    }

    private final Repository repository;
    final private LiveData<List<Pair>> leagueList;
    final private LiveData<List<Pair>> teamList;
    final private LiveData<List<Pair>> playerList;
    final private MutableLiveData<Integer> leagueIndex;
    final private MutableLiveData<Integer> teamIndex;
    final private LeagueHandler leagueHandler;
    final private TeamHandler teamHandler;

    public LiveData<List<Pair>> getLeagueList() {
        return leagueList;
    }

    public LiveData<List<Pair>> getTeamList(){
        return teamList;
    }

    public LiveData<List<Pair>> getPlayerList(){
        return playerList;
    }

    public Handler getLeagueHandler(){
        return leagueHandler;
    }

    public Handler getTeamHandler(){
        return teamHandler;
    }

    public void setLeagueIndex(Integer val){
        leagueIndex.setValue(val);
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
        }
    }

    public class TeamHandler extends Handler {
        public TeamHandler(MutableLiveData<Integer> index) {
            super(index);
        }

        @Override
        public void setIndex(Integer index) {
            setTeamIndex(index);
        }
    }

}

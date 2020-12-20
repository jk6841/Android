package com.jk.soccer.ui.match;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jk.soccer.data.Repository;
import com.jk.soccer.data.local.Match;

import java.util.List;

public class MatchViewModel extends AndroidViewModel {

    public List<Match> getMatches(){
        return repository.getMatch();
    }

    public LiveData<List<Match>> getLiveMatches(){
        return ldMatches;
    }

    public MatchViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
        ldMatches = repository.getMatchLiveData();
    }

    private Repository repository;
    private LiveData<List<Match>> ldMatches;
}

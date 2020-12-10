package com.jk.soccer.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.jk.soccer.R;
import com.jk.soccer.data.Repository;
import com.jk.soccer.data.local.Player;
import com.jk.soccer.data.local.Team;
import com.jk.soccer.ui.home.HomeAdapter;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private Repository repository;

    private HomeAdapter adapter;

    private LiveData<List<Player>> livedataPlayer;
    private LiveData<List<Team>> livedataTeam;

    final private LiveData<String> livedataPlayerName;
    final private LiveData<Boolean> livedataBookmark;
    //final private LiveData<String> livedataTeamName;
    final private LiveData<String> livedataPosition;
    final private LiveData<String> livedataHeight;
    final private LiveData<String> livedataFoot;
    final private LiveData<String> livedataAge;
    final private LiveData<String> livedataShirt;
    final private LiveData<String> livedataPlayerUrl;


    public HomeAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(HomeAdapter adapter) {
        this.adapter = adapter;
    }

    public LiveData<String> getLivePlayerName() {
        return livedataPlayerName;
    }

    public LiveData<String> getLivePosition() {
        return livedataPosition;
    }

    public LiveData<String> getLiveHeight() {
        return livedataHeight;
    }

    public LiveData<String> getLiveFoot() {
        return livedataFoot;
    }

    public LiveData<String> getLiveAge() {
        return livedataAge;
    }

    public LiveData<String> getLiveShirt() {
        return livedataShirt;
    }

    public LiveData<String> getLivePlayerUrl() {
        return livedataPlayerUrl;
    }

    public LiveData<List<Player>> getLivePlayer(){
        return livedataPlayer;
    }

    public HomeViewModel(@NonNull Application application) {
        super(application);

        repository = Repository.getInstance(application);
        livedataPlayer = repository.getPlayer();
        livedataTeam = repository.getTeam();

        livedataPlayerName = Transformations.map(
                livedataPlayer, input -> "이름: " + input.get(0).getName() );

        livedataBookmark = Transformations.map(
                livedataPlayer, input -> input.get(0).isBookmark() );

        livedataPosition = Transformations.map(
                livedataPlayer, input -> "포지션: " + input.get(0).getPosition() );

        livedataHeight = Transformations.map(
                livedataPlayer, input -> "신장: " + input.get(0).getHeight() );

        livedataFoot = Transformations.map(
                livedataPlayer, input -> "주발: " + input.get(0).getFoot() );

        livedataAge = Transformations.map(
                livedataPlayer, input -> "나이: " + input.get(0).getAge() + "세" );

        livedataShirt = Transformations.map(
                livedataPlayer, input -> "등번호: " + input.get(0).getShirt() );

        livedataPlayerUrl = Transformations.map(
                livedataPlayer,
                input -> application.getString(R.string.baseUrl2)
                        + input.get(0).getId()
                        + application.getString(R.string.png) );
    }

}
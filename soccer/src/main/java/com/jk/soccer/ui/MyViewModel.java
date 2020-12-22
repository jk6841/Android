package com.jk.soccer.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.jk.soccer.data.Repository;
import com.jk.soccer.data.local.Match;
import com.jk.soccer.data.local.Player;

import java.util.ArrayList;
import java.util.List;

public class MyViewModel extends AndroidViewModel {

    public MyViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
        playerLiveDataList = new ArrayList<>();
        idLiveDataList = new ArrayList<>();
        nameLiveDataList = new ArrayList<>();
        positionLiveDataList = new ArrayList<>();
        heightLiveDataList = new ArrayList<>();
        footLiveDataList = new ArrayList<>();
        ageLiveDataList = new ArrayList<>();
        shirtLiveDataList = new ArrayList<>();
        bookmarkLiveDataList = new ArrayList<>();

        matchLiveDataList = new ArrayList<>();
    }

    public void initPlayer(){
        Integer num = repository.getPlayer().size();
        for (int i = 0; i < num; i++){
            LiveData<Player> playerLiveData = repository.getPlayerLiveData(i);
            LiveData<Integer> idLiveData = Transformations.map(playerLiveData, Player::getId);
            LiveData<String> nameLiveData = (Transformations.map(playerLiveData, Player::getName));
            LiveData<String> positionLiveData = (Transformations.map(playerLiveData, Player::printPosition));
            LiveData<String> heightLiveData = (Transformations.map(playerLiveData, Player::printHeight));
            LiveData<String> footLiveData = (Transformations.map(playerLiveData, Player::printFoot));
            LiveData<String> ageLiveData = (Transformations.map(playerLiveData, Player::printAge));
            LiveData<String> shirtLiveData = (Transformations.map(playerLiveData, Player::printShirt));
            LiveData<Boolean> bookmarkLiveData = (Transformations.map(playerLiveData, Player::isBookmark));
            if (sortPlayer){
                playerLiveDataList.set(i, playerLiveData);
                idLiveDataList.set(i, idLiveData);
                nameLiveDataList.set(i, nameLiveData);
                positionLiveDataList.set(i, positionLiveData);
                heightLiveDataList.set(i, heightLiveData);
                footLiveDataList.set(i, footLiveData);
                ageLiveDataList.set(i, ageLiveData);
                shirtLiveDataList.set(i, shirtLiveData);
                bookmarkLiveDataList.set(i, bookmarkLiveData);
            } else {
                playerLiveDataList.add(playerLiveData);
                idLiveDataList.add(idLiveData);
                nameLiveDataList.add(nameLiveData);
                positionLiveDataList.add(positionLiveData);
                heightLiveDataList.add(heightLiveData);
                footLiveDataList.add(footLiveData);
                ageLiveDataList.add(ageLiveData);
                shirtLiveDataList.add(shirtLiveData);
                bookmarkLiveDataList.add(bookmarkLiveData);
            }
        }
        sortPlayer = true;
    }

    public void initMatch(){
        List<Match> matches = repository.getMatch();
        Integer num = matches.size();
        for (int i = 0; i < num; i++){
            LiveData<Match> matchLiveData = repository.getMatchLiveData(matches.get(i).getId());
            if (sortMatch)
                matchLiveDataList.set(i, matchLiveData);
            else
                matchLiveDataList.add(matchLiveData);
        }
    }

    public List<Player> getPlayers() { return repository.getPlayer(); }

    public List<Match> getMatches() { return repository.getMatch(); }

    public LiveData<Integer> getIdLiveData(Integer index){
        return idLiveDataList.get(index);
    }

    public LiveData<String> getNameLiveData(Integer index) {
        return nameLiveDataList.get(index);
    }

    public LiveData<String> getPositionLiveData(Integer index) {
        return positionLiveDataList.get(index);
    }

    public LiveData<String> getHeightLiveData(Integer index) {
        return heightLiveDataList.get(index);
    }

    public LiveData<String> getFootLiveData(Integer index) {
        return footLiveDataList.get(index);
    }

    public LiveData<String> getAgeLiveData(Integer index) {
        return ageLiveDataList.get(index);
    }

    public LiveData<String> getShirtLiveData(Integer index) {
        return shirtLiveDataList.get(index);
    }

    public LiveData<Boolean> getBookmarkLiveData(Integer index) {
        return bookmarkLiveDataList.get(index);
    }

    public void setBookmark(boolean bookmark, Integer index){
        repository.bookmark(bookmark, idLiveDataList.get(index).getValue());
    }

    final private Repository repository;

    // For Player //
    final private ArrayList<LiveData<Player>> playerLiveDataList;
    final private ArrayList<LiveData<Integer>> idLiveDataList;
    final private ArrayList<LiveData<String>> nameLiveDataList;
    final private ArrayList<LiveData<String>> positionLiveDataList;
    final private ArrayList<LiveData<String>> heightLiveDataList;
    final private ArrayList<LiveData<String>> footLiveDataList;
    final private ArrayList<LiveData<String>> ageLiveDataList;
    final private ArrayList<LiveData<String>> shirtLiveDataList;
    final private ArrayList<LiveData<Boolean>> bookmarkLiveDataList;
    private Boolean sortPlayer = false;

    // For Match //
    final private ArrayList<LiveData<Match>> matchLiveDataList;
    private Boolean sortMatch = false;

}
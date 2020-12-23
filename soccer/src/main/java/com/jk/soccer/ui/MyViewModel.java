package com.jk.soccer.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.jk.soccer.data.Repository;
import com.jk.soccer.data.local.TableMatch;
import com.jk.soccer.data.local.TablePlayer;
import com.jk.soccer.data.response.Player;

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
        birthLiveDataList = new ArrayList<>();
        shirtLiveDataList = new ArrayList<>();
        bookmarkLiveDataList = new ArrayList<>();
        teamLiveDataList = new ArrayList<>();
        matchLiveDataList = new ArrayList<>();
    }

    public void initPlayer(){
        List<TablePlayer> players = repository.getPlayer();
        for (int i = 0; i < players.size(); i++){
            LiveData<Player> playerLiveData = repository.getPlayerLiveData(players.get(i).getId());
            LiveData<Integer> idLiveData = Transformations.map(playerLiveData, Player::getId);
            LiveData<String> nameLiveData = (Transformations.map(playerLiveData, Player::getName));
            LiveData<String> positionLiveData = (Transformations.map(playerLiveData, Player::getPosition));
            LiveData<String> heightLiveData = (Transformations.map(playerLiveData, Player::printHeight));
            LiveData<String> footLiveData = (Transformations.map(playerLiveData, Player::getFoot));
            LiveData<String> birthLiveData = (Transformations.map(playerLiveData, Player::getBirth));
            LiveData<String> shirtLiveData = (Transformations.map(playerLiveData, Player::printShirt));
            LiveData<Boolean> bookmarkLiveData = (Transformations.map(playerLiveData, Player::getBookmark));
            LiveData<String> teamLiveData = Transformations.map(playerLiveData, Player::getTeam);
            if (sortPlayer){
                playerLiveDataList.set(i, playerLiveData);
                idLiveDataList.set(i, idLiveData);
                nameLiveDataList.set(i, nameLiveData);
                positionLiveDataList.set(i, positionLiveData);
                heightLiveDataList.set(i, heightLiveData);
                footLiveDataList.set(i, footLiveData);
                birthLiveDataList.set(i, birthLiveData);
                shirtLiveDataList.set(i, shirtLiveData);
                bookmarkLiveDataList.set(i, bookmarkLiveData);
                teamLiveDataList.set(i, teamLiveData);
            } else {
                playerLiveDataList.add(playerLiveData);
                idLiveDataList.add(idLiveData);
                nameLiveDataList.add(nameLiveData);
                positionLiveDataList.add(positionLiveData);
                heightLiveDataList.add(heightLiveData);
                footLiveDataList.add(footLiveData);
                birthLiveDataList.add(birthLiveData);
                shirtLiveDataList.add(shirtLiveData);
                bookmarkLiveDataList.add(bookmarkLiveData);
                teamLiveDataList.add(teamLiveData);
            }
        }
        sortPlayer = true;
    }

    public void initMatch(){
        List<TableMatch> matches = repository.getMatch();
        for (int i = 0; i < matches.size(); i++){
            LiveData<TableMatch> matchLiveData = repository.getMatchLiveData(matches.get(i).getId());
            if (sortMatch)
                matchLiveDataList.set(i, matchLiveData);
            else
                matchLiveDataList.add(matchLiveData);
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository.close();
    }

    public List<TablePlayer> getPlayers() { return repository.getPlayer(); }

    public List<TableMatch> getMatches() { return repository.getMatch(); }

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

    public LiveData<String> getBirthLiveData(Integer index) {
        return birthLiveDataList.get(index);
    }

    public LiveData<String> getShirtLiveData(Integer index) {
        return shirtLiveDataList.get(index);
    }

    public LiveData<Boolean> getBookmarkLiveData(Integer index) {
        return bookmarkLiveDataList.get(index);
    }

    public void setBookmark(Integer index){
        repository.bookmark(
                !bookmarkLiveDataList.get(index).getValue(),
                idLiveDataList.get(index).getValue());
    }

    public LiveData<String> getTeamLiveData(Integer index){
        return teamLiveDataList.get(index);
    }

    final private Repository repository;

    // For Player //
    final private ArrayList<LiveData<Player>> playerLiveDataList;
    final private ArrayList<LiveData<Integer>> idLiveDataList;
    final private ArrayList<LiveData<String>> nameLiveDataList;
    final private ArrayList<LiveData<String>> positionLiveDataList;
    final private ArrayList<LiveData<String>> heightLiveDataList;
    final private ArrayList<LiveData<String>> footLiveDataList;
    final private ArrayList<LiveData<String>> birthLiveDataList;
    final private ArrayList<LiveData<String>> shirtLiveDataList;
    final private ArrayList<LiveData<Boolean>> bookmarkLiveDataList;
    final private ArrayList<LiveData<String>> teamLiveDataList;
    private Boolean sortPlayer = false;

    // For Match //
    final private ArrayList<LiveData<TableMatch>> matchLiveDataList;
    private Boolean sortMatch = false;

}
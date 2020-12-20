package com.jk.soccer.ui.playerinfo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.jk.soccer.data.Repository;
import com.jk.soccer.data.local.Player;
import com.jk.soccer.data.local.Team;

public class PlayerInfoViewModel extends AndroidViewModel {

    public LiveData<Integer> getIdLiveData(){
        return idLiveData;
    }

    public LiveData<String> getNameLiveData() {
        return nameLiveData;
    }

    public LiveData<String> getTeamNameLiveData() { return teamNameLiveData; }

    public LiveData<String> getPositionLiveData() {
        return positionLiveData;
    }

    public LiveData<String> getHeightLiveData() {
        return heightLiveData;
    }

    public LiveData<String> getFootLiveData() {
        return footLiveData;
    }

    public LiveData<String> getAgeLiveData() {
        return ageLiveData;
    }

    public LiveData<String> getShirtLiveData() {
        return shirtLiveData;
    }

    public LiveData<Boolean> getBookmarkLiveData() {
        return bookmarkLiveData;
    }

    public void setBookmark(boolean bookmark, int playerId){
        repository.bookmark(bookmark, playerId);
    }

    public PlayerInfoViewModel(@NonNull Application application) {
        super(application);

        repository = Repository.getInstance(application);
    }

    public void init(int index){
        this.index = index;

        playerLiveData = repository.getPlayerLiveData(index);

        idLiveData = Transformations.map(playerLiveData, Player::getId);

        nameLiveData = (Transformations.map(playerLiveData, Player::getName));

        positionLiveData = (Transformations.map(playerLiveData, Player::printPosition));

        heightLiveData = (Transformations.map(playerLiveData, Player::printHeight));

        footLiveData = (Transformations.map(playerLiveData, Player::printFoot));

        ageLiveData = (Transformations.map(playerLiveData, Player::printAge));

        shirtLiveData = (Transformations.map(playerLiveData, Player::printShirt));

        bookmarkLiveData = (Transformations.map(playerLiveData, Player::isBookmark));
    }

    private Integer index;
    final private Repository repository;
    private LiveData<Player> playerLiveData;
    private LiveData<Integer> idLiveData;
    private LiveData<String> nameLiveData;
    private LiveData<String> teamNameLiveData;
    private LiveData<String> positionLiveData;
    private LiveData<String> heightLiveData;
    private LiveData<String> footLiveData;
    private LiveData<String> ageLiveData;
    private LiveData<String> shirtLiveData;
    private LiveData<Boolean> bookmarkLiveData;

}

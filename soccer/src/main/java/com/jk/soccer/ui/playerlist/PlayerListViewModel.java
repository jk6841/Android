package com.jk.soccer.ui.playerlist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.jk.soccer.data.Repository;
import com.jk.soccer.data.local.Player;
import java.util.List;

public class PlayerListViewModel extends AndroidViewModel {

    public List<Player> getPlayers() { return repository.getPlayerInit(); }

    public void setBookmark(int index){
        Player player = ldPlayers.getValue().get(index);
        repository.bookmark(
                Repository.Object.Player,
                !player.isBookmark(),
                player.getId());
    }

    public LiveData<List<Player>> getLivePlayers(){
        return ldPlayers;
    }

    public PlayerListViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
        ldPlayers = repository.getPlayer();
    }

    private Repository repository;

    final private LiveData<List<Player>> ldPlayers;
}
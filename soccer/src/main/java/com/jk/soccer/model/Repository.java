package com.jk.soccer.model;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jk.soccer.etc.MyCallback;
import com.jk.soccer.model.local.MyLocal;
import com.jk.soccer.etc.enumeration.Type;
import com.jk.soccer.model.local.TableSearch;
import com.jk.soccer.model.remote.MyRemote;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    public static Repository getInstance(Application application){
        if (repository == null){
            repository = new Repository(application);
        }
        return repository;
    }

    public static Repository getInstance(){
        return repository;
    }

    public void getLeagueInfoAsync(Integer ID, MyCallback<League> callback){
        myRemote.downloadLeague(ID, callback);
    }

    public void getTeamInfoAsync(Integer ID, MyCallback<Team> callback){
        myRemote.downloadTeam(ID, callback);
    }

    public void getPlayerInfoAsync(Integer ID, MyCallback<Player> callback){
        myRemote.downloadPlayer(ID, callback);
    }

    public void updateDB(UpdateCallback callback){
        Counter counter = new Counter(0);
        myLocal.clearSearch();
        myLocal.getLeagueList(leagueList -> {
            for (int i = 0; i < leagueList.size(); i++) {
                Integer leagueID = leagueList.get(i).getID();
                myRemote.downloadTeamList(leagueID, teamList -> {
                    if (teamList != null){
                        myLocal.insertSearch(teamList);
                        synchronized (counter){
                            counter.incValue(teamList.size());
                        }
                        for (int j = 0; j < teamList.size(); j++){
                            TableSearch team = teamList.get(j);
                            Integer teamID = team.getID();
                            myRemote.downloadPlayerList(teamID, playerList -> {
                                myLocal.insertSearch(playerList);
                                synchronized (counter){
                                    counter.incValue(-1);
                                }
                                if (counter.getValue() == 0){
                                    callback.onComplete(true);
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    public LiveData<List<TableSearch>> search(String searchWord, Type type){
        if (searchWord.length() <= 1)
            return emptySearch;
        return myLocal.getSearch("%" +searchWord + "%", type);
    }

    private static Repository repository = null;
    final private MyLocal myLocal;
    final private MyRemote myRemote;
    final private MutableLiveData<List<TableSearch>> emptySearch;

    private Repository(Application application){
        myLocal = MyLocal.getInstance(application);
        myRemote = MyRemote.getInstance(application);
        emptySearch = new MutableLiveData<>();
        emptySearch.setValue(new ArrayList<>());
    }

    private static class Counter{

        private Integer value;

        public Counter(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public void incValue(Integer inc){
            value += inc;
        }
    }

}

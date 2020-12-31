package com.jk.soccer.model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jk.soccer.etc.Player;
import com.jk.soccer.etc.MyCallback;
import com.jk.soccer.etc.Team;
import com.jk.soccer.model.local.MyLocal;
import com.jk.soccer.etc.enumeration.Type;
import com.jk.soccer.model.local.TableSearch;
import com.jk.soccer.model.remote.MyRemote;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    public Repository(Application application){
        myLocal = MyLocal.getInstance(application);
        myRemote = MyRemote.getInstance(application);
        emptySearch = new MutableLiveData<>();
        emptySearch.setValue(new ArrayList<>());
    }

    public static Repository getInstance(Application application){
        if (repository == null){
            repository = new Repository(application);
        }
        return repository;
    }

    public static Repository getInstance(){
        return repository;
    }

    public void close(){
        myLocal.close();
    }

    public void getLeagueInfoAsync(Integer ID, MyCallback<String> callback){
        getInfoAsync(Type.LEAGUE, ID, strings[9], callback);
    }

    public void getTeamInfoAsync(Integer ID, MyCallback<Team> callback){
        myRemote.downloadTeam(ID, callback);
    }

    public void getPlayerInfoAsync(Integer ID, MyCallback<Player> callback){
        myRemote.downloadPlayer(ID, callback);
    }

    public void getInfoAsync(Type type, Integer ID, String tab, MyCallback<String> callback){
        myRemote.downloadAsync(type, ID, tab, new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call,
                                   @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    String result;
                    try {
                        ResponseBody responseBody = response.body();
                        result = (responseBody != null)? responseBody.string() : strings[0];
                    } catch (Exception e){
                        result = strings[0];
                    }
                    callback.onComplete(result);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call,
                                  @NonNull Throwable t) {
                callback.onComplete(strings[0]);
            }
        });
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
                        String counterVal;
                        synchronized (counter){
                            counter.incValue(teamList.size());
                            counterVal = counter.getValue().toString();
                        }
                        callback.onProgress(counterVal);
                        Log.d("counter: ", counterVal);
                        for (int j = 0; j < teamList.size(); j++){
                            TableSearch team = teamList.get(j);
                            Integer teamID = team.getID();
                            myRemote.downloadPlayerList(teamID, playerList -> {
                                myLocal.insertSearch(playerList);
                                String counterVal2;
                                synchronized (counter){
                                    counter.incValue(-1);
                                    counterVal2 = counter.getValue().toString();
                                }
                                callback.onProgress(counterVal);
                                Log.d("counter: ", counterVal2);
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
    final private String[] strings
            = {"", "table", "squad", "id", "name", "leagueID", "tables", "tableData", "details",
    "overview"};

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

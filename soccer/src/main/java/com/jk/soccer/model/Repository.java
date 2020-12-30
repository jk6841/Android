package com.jk.soccer.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jk.soccer.etc.RepositoryCallback;
import com.jk.soccer.model.local.MyLocal;
import com.jk.soccer.etc.enumeration.Type;
import com.jk.soccer.model.local.TableSearch;
import com.jk.soccer.model.remote.MyRemote;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.jk.soccer.etc.MyJson.myJSONArray;
import static com.jk.soccer.etc.MyJson.myJSONInt;
import static com.jk.soccer.etc.MyJson.myJSONObject;
import static com.jk.soccer.etc.MyJson.myJSONString;

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

//    public String getLeagueInfo(Integer ID){
//        return myRemote.download(Type.LEAGUE, ID, "overview");
//    }
//
//    public String getTeamInfo(Integer ID){
//        return myRemote.download(Type.TEAM, ID, "overview");
//    }

    public void getTeamInfoAsync(Integer ID, RepositoryCallback<String> callback){
        getInfoAsync(Type.TEAM, ID, strings[9], callback);
    }


    public void getPlayerInfoAsync(Integer ID, RepositoryCallback<String> callback){
        getInfoAsync(Type.PERSON, ID, strings[0], callback);
    }

    public void getInfoAsync(Type type, Integer ID, String tab, RepositoryCallback<String> callback){
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

    public String getPlayerInfo(Integer ID){
        return myRemote.downloadSync(Type.PERSON, ID, strings[0]);
    }

    public void updateDB(RepositoryCallback<Boolean> result){
        Thread t = new Thread(() -> {
            myLocal.clearSearch();
            List<TableSearch> leagueList = myLocal.getLeagueList();
            List<Thread> threadLeagueList = new ArrayList<>();
            for (int i = 0; i < leagueList.size(); i++) {
                int k = i;
                Thread threadLeague = new Thread(() -> {
                    Integer leagueID = leagueList.get(k).getID();
                    String leagueString = myRemote.downloadSync(
                            Type.LEAGUE, leagueID, strings[1]);
                    List<TableSearch> teamList = myTeamList(leagueString);
                    if (teamList != null){
                        List<Thread> threadTeamList = new ArrayList<>();
                        for (int j = 0; j < teamList.size(); j++){
                            int w = j;
                            Thread threadTeam = new Thread(() -> {
                                TableSearch team = teamList.get(w);
                                Integer teamID = team.getID();
                                String teamString = myRemote.downloadSync(
                                        Type.TEAM, teamID, strings[2]);
                                List<TableSearch> personList = myPersonList(teamString);
                                myLocal.insertSearch(personList);
                            });
                            threadTeam.setName(teamList.get(w).getName());
                            threadTeam.start();
                            threadTeamList.add(threadTeam);
                        }
                        myLocal.insertSearch(teamList);
                        for (int j = 0; j < threadTeamList.size(); j++){
                            try{
                                threadTeamList.get(j).join();
                            } catch (Exception e){
                                result.onComplete(false);
                            }
                        }
                    }
                });
                threadLeague.setName(leagueList.get(k).getName());
                threadLeague.start();
                threadLeagueList.add(threadLeague);
            }
            for (int i = 0; i < threadLeagueList.size(); i++){
                try{
                    threadLeagueList.get(i).join();
                } catch (Exception e){
                    result.onComplete(false);
                }
            }
            result.onComplete(true);
        });
        t.start();
    }


    public LiveData<List<TableSearch>> search(String searchWord){
        if (searchWord.length() <= 1)
            return emptySearch;
        return myLocal.getSearch("%" +searchWord + "%");
    }

    private static Repository repository = null;
    final private MyLocal myLocal;
    final private MyRemote myRemote;
    final private MutableLiveData<List<TableSearch>> emptySearch;
    final private String[] strings
            = {"", "table", "squad", "id", "name", "leagueID", "tables", "tableData", "details",
    "overview"};


    private List<TableSearch> myTeamList(String jsonLeagueString){
        JSONObject jsonObject = myJSONObject(jsonLeagueString);
        JSONObject jsonTableData = myJSONObject(jsonObject, strings[7]);
        JSONArray jsonTables = myJSONArray(jsonTableData, strings[6]);
        JSONObject jsonTables2 = myJSONObject(jsonTables, 0);
        Integer leagueID = myJSONInt(jsonTables2, strings[5]);
        JSONArray jsonTable = myJSONArray(jsonTables2, strings[1]);
        if (jsonTable == null)
            return null;
        List<TableSearch> teamList = new ArrayList<>();
        for (int i = 0; i < jsonTable.length(); i++){
            JSONObject jsonTeam = myJSONObject(jsonTable, i);
            Integer teamID = myJSONInt(jsonTeam, strings[3]);
            String teamName = myJSONString(jsonTeam, strings[4]);
            teamList.add(new TableSearch(teamID, leagueID, Type.TEAM, teamName));
        }
        return teamList;
    }

    private List<TableSearch> myPersonList(String jsonTeamString){
        JSONObject jsonObject = myJSONObject(jsonTeamString);
        JSONObject jsonDetails = myJSONObject(jsonObject, strings[8]);
        Integer teamID = myJSONInt(jsonDetails, strings[3]);
        JSONArray jsonSquad = myJSONArray(jsonObject, strings[2]);
        if (jsonSquad == null)
            return null;
        List<TableSearch> playerList = new ArrayList<>();
        for (int i = 0; i < jsonSquad.length(); i++){
            JSONArray jsonSquadItem = myJSONArray(myJSONArray(jsonSquad, i), 1);
            if (jsonSquadItem == null)
                continue;
            for (int j = 0; j < jsonSquadItem.length(); j++){
                JSONObject jsonPlayer = myJSONObject(jsonSquadItem, j);
                Integer playerID = myJSONInt(jsonPlayer, strings[3]);
                String name = myJSONString(jsonPlayer, strings[4]);
                playerList.add(new TableSearch(playerID, teamID, Type.PERSON, name));
            }
        }
        return playerList;
    }


}

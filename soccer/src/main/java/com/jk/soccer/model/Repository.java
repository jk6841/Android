package com.jk.soccer.model;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jk.soccer.etc.RepositoryCallback;
import com.jk.soccer.model.local.MyLocal;
import com.jk.soccer.etc.Type;
import com.jk.soccer.model.local.TableSearch;
import com.jk.soccer.model.remote.MyRemote;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.jk.soccer.etc.MyParser.myJSONArray;
import static com.jk.soccer.etc.MyParser.myJSONInt;
import static com.jk.soccer.etc.MyParser.myJSONObject;
import static com.jk.soccer.etc.MyParser.myJSONString;

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

    public void close(){
        myLocal.close();
    }

    public String getLeagueInfo(Integer ID){
        return myRemote.download(Type.LEAGUE, ID, "overview");
    }

    public String getTeamInfo(Integer ID){
        return myRemote.download(Type.TEAM, ID, "overview");
    }

    public String getPlayerInfo(Integer ID){
        return myRemote.download(Type.PERSON, ID, "");
    }

    public void updateDB(RepositoryCallback<Boolean> result){
        Thread t = new Thread(() -> {
            List<TableSearch> leagueList = myLocal.getLeagueList();
            List<Thread> threadLeagueList = new ArrayList<>();
            for (int i = 0; i < leagueList.size(); i++) {
                int k = i;
                Thread threadLeague = new Thread(() -> {
                    Integer leagueID = leagueList.get(k).getID();
                    String leagueString = myRemote.download(Type.LEAGUE, leagueID, "table");
                    List<TableSearch> teamList = myTeamList(leagueString);
                    List<Thread> threadTeamList = new ArrayList<>();
                    for (int j = 0; j < teamList.size(); j++){
                        int w = j;
                        Thread threadTeam = new Thread(() -> {
                            TableSearch team = teamList.get(w);
                            myLocal.insertSearch(team);
                            Integer teamID = team.getID();
                            String teamString = myRemote.download(Type.TEAM, teamID, "squad");
                            List<TableSearch> personList = myPersonList(teamString);
                            for (TableSearch person : personList) {
                                myLocal.insertSearch(person);
                            }
                        });
                        threadTeam.start();
                        threadTeamList.add(threadTeam);
                    }
                    for (int j = 0; j < teamList.size(); j++){
                        try{
                            threadTeamList.get(j).join();
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
                threadLeague.start();
                threadLeagueList.add(threadLeague);
            }
            for (int i = 0; i < leagueList.size(); i++){
                try{
                    threadLeagueList.get(i).join();
                } catch (Exception e){
                    e.printStackTrace();
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


    private List<TableSearch> myTeamList(String jsonLeagueString){
        JSONObject jsonObject = myJSONObject(jsonLeagueString);
        JSONObject jsonTableData = myJSONObject(jsonObject, "tableData");
        JSONArray jsonTables = myJSONArray(jsonTableData, "tables");
        JSONObject jsonTables2 = myJSONObject(jsonTables, 0);
        Integer leagueID = myJSONInt(jsonTables2, "leagueId");
        JSONArray jsonTable = myJSONArray(jsonTables2, "table");
        if (jsonTable == null)
            return null;
        List<TableSearch> teamList = new ArrayList<>();
        for (int i = 0; i < jsonTable.length(); i++){
            JSONObject jsonTeam = myJSONObject(jsonTable, i);
            Integer teamID = myJSONInt(jsonTeam, "id");
            String teamName = myJSONString(jsonTeam, "name");
            teamList.add(new TableSearch(teamID, leagueID, Type.TEAM, teamName));
        }
        return teamList;
    }

    private List<TableSearch> myPersonList(String jsonTeamString){
        JSONObject jsonObject = myJSONObject(jsonTeamString);
        JSONObject jsonDetails = myJSONObject(jsonObject, "details");
        Integer teamID = myJSONInt(jsonDetails, "id");
        JSONArray jsonSquad = myJSONArray(jsonObject, "squad");
        if (jsonSquad == null)
            return null;
        List<TableSearch> playerList = new ArrayList<>();
        for (int i = 0; i < jsonSquad.length(); i++){
            JSONArray jsonSquadItem = myJSONArray(myJSONArray(jsonSquad, i), 1);
            if (jsonSquadItem == null)
                continue;
            for (int j = 0; j < jsonSquadItem.length(); j++){
                JSONObject jsonPlayer = myJSONObject(jsonSquadItem, j);
                Integer playerID = myJSONInt(jsonPlayer, "id");
                String name = myJSONString(jsonPlayer, "name");
                playerList.add(new TableSearch(playerID, teamID, Type.PERSON, name));
            }
        }
        return playerList;
    }


}

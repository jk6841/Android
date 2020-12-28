package com.jk.soccer.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.jk.soccer.etc.Role;
import com.jk.soccer.model.local.MyLocal;
import com.jk.soccer.etc.Type;
import com.jk.soccer.model.local.TableLeague;
import com.jk.soccer.model.local.TablePlayer;
import com.jk.soccer.model.local.TableTeam;
import com.jk.soccer.model.remote.MyRemote;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.jk.soccer.etc.MyParser.myJSONArray;
import static com.jk.soccer.etc.MyParser.myJSONInt;
import static com.jk.soccer.etc.MyParser.myJSONObject;
import static com.jk.soccer.etc.MyParser.myJSONString;

public class Repository {

    public Repository(Application application){
        myLocal = MyLocal.getInstance(application);
        myRemote = MyRemote.getInstance(application);
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

    public LiveData<List<TableLeague>> getLeagueList(){
        return myLocal.getLeagueList();
    }

    public LiveData<List<TableTeam>> getTeamList(Integer leagueIndex){
        if (leagueIndex == -1)
            return null;
        if (needUpdate(Type.LEAGUE, leagueIndex)){
            Integer leagueID = myLocal.getID(Type.LEAGUE, leagueIndex);
            String leagueString = myRemote.download(Type.LEAGUE, leagueID, "table");
            List<TableTeam> teamList = myTeamList(leagueString);
            myLocal.insertTeamList(teamList);
            myLocal.setChildrenDate(Type.LEAGUE, leagueIndex, new Date());
        }
        return myLocal.getTeamList(leagueIndex);
    }

    public LiveData<List<TablePlayer>> getPlayerList(Integer teamIndex){
        if (teamIndex == -1)
            return null;
        if (needUpdate(Type.TEAM, teamIndex)) {
            Integer teamID = myLocal.getID(Type.TEAM, teamIndex);
            String teamString = myRemote.download(Type.TEAM, teamID, "squad");
            List<TablePlayer> playerList = myPlayerList(teamString);
            myLocal.insertPlayerList(playerList);
            myLocal.setChildrenDate(Type.TEAM, teamIndex, new Date());
        }
        return myLocal.getPlayerList(teamIndex);
    }

    public String getMatchInfo(Integer ID){
        return myRemote.download(Type.MATCH, ID, "");
    }

    public String getLeagueInfo(Integer ID){
        return myRemote.download(Type.LEAGUE, ID, "overview");
    }

    public String getTeamInfo(Integer ID){
        return myRemote.download(Type.TEAM, ID, "overview");
    }

    public String getPlayerInfo(Integer ID){
        return myRemote.download(Type.PLAYER, ID, "");
    }

    private static Repository repository = null;
    final private MyLocal myLocal;
    final private MyRemote myRemote;

    private long msToDay(long ms){
        return ms / (1000 * 60 * 60 * 24);
    }

    private Boolean needUpdate(Type type, Integer index){
        Date date = myLocal.getChildrenDate(type, index);
        if (date == null)
            return true;
        Date today = new Date();
        long diff = today.getTime() - date.getTime();
        return msToDay(diff) >= 1;
    }

    private List<TableTeam> myTeamList(String jsonString){
        JSONObject jsonObject = myJSONObject(jsonString);
        JSONObject jsonTableData = myJSONObject(jsonObject, "tableData");
        JSONArray jsonTables = myJSONArray(jsonTableData, "tables");
        JSONObject jsonTables2 = myJSONObject(jsonTables, 0);
        Integer leagueID = myJSONInt(jsonTables2, "leagueId");
        JSONArray jsonTable = myJSONArray(jsonTables2, "table");
        if (jsonTable == null)
            return null;
        List<TableTeam> teamList = new ArrayList<>();
        for (int i = 0; i < jsonTable.length(); i++){
            JSONObject jsonTeam= myJSONObject(jsonTable, i);
            Integer teamID = myJSONInt(jsonTeam, "id");
            String teamName = myJSONString(jsonTeam, "name");
            teamList.add(new TableTeam(teamID, leagueID, i + 1, teamName, null));
        }
        return teamList;
    }

    private List<TablePlayer> myPlayerList(String jsonString){
        JSONObject jsonObject = myJSONObject(jsonString);
        JSONObject jsonDetails = myJSONObject(jsonObject, "details");
        Integer teamID = myJSONInt(jsonDetails, "id");
        JSONArray jsonSquad = myJSONArray(jsonObject, "squad");
        if (jsonSquad == null)
            return null;
        List<TablePlayer> playerList = new ArrayList<>();
        for (int i = 0; i < jsonSquad.length(); i++){
            JSONArray jsonSquadItem = myJSONArray(myJSONArray(jsonSquad, i), 1);
            if (jsonSquadItem == null)
                continue;
            for (int j = 0; j < jsonSquadItem.length(); j++){
                JSONObject jsonPlayer = myJSONObject(jsonSquadItem, j);
                Integer playerID = myJSONInt(jsonPlayer, "id");
                String name = myJSONString(jsonPlayer, "name");
                String playerRole = myJSONString(jsonPlayer, "role");
                Role role;
                switch (playerRole){
                    case "":
                        role = Role.COACH;
                        break;
                    case "goalkeepers":
                        role = Role.GK;
                        break;
                    case "defenders":
                        role = Role.DF;
                        break;
                    case "midfielders":
                        role = Role.MF;
                        break;
                    case "attackers":
                        role = Role.FW;
                        break;
                    default:
                        role = Role.NONE;
                        break;
                }
                playerList.add(new TablePlayer(playerID, teamID, name, role, false));
            }
        }
        return playerList;
    }

}

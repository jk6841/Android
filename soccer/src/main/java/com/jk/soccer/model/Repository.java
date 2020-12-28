package com.jk.soccer.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.jk.soccer.etc.Pair;
import com.jk.soccer.model.local.MyLocal;
import com.jk.soccer.etc.Type;
import com.jk.soccer.model.local.TablePlayer;
import com.jk.soccer.model.local.TableTeam;
import com.jk.soccer.model.remote.MyRemote;
import com.jk.soccer.model.local.MyParser;
import java.util.List;

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

    public LiveData<List<Pair>> getLeagueList(){
        return myLocal.getLeagueList();
    }

    public LiveData<List<Pair>> getTeamList(Integer leagueIndex){
        if (leagueIndex == -1)
            return null;
        Integer leagueID = myLocal.getID(Type.LEAGUE, leagueIndex);
        String leagueString = myRemote.download(Type.LEAGUE, leagueID, "table");
        List<TableTeam> teamList = MyParser.myTeamList(leagueString);
        myLocal.insertTeamList(teamList);
        return myLocal.getTeamList(leagueIndex);
    }

    public LiveData<List<Pair>> getPlayerList(Integer teamIndex){
        if (teamIndex == -1)
            return null;
        Integer teamID = myLocal.getID(Type.TEAM, teamIndex);
        String teamString = myRemote.download(Type.TEAM, teamID, "squad");
        List<TablePlayer> playerList = MyParser.myPlayerList(teamString);
        myLocal.insertPlayerList(playerList);
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


}

package com.jk.soccer.model.local;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.jk.soccer.etc.Pair;
import com.jk.soccer.etc.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MyLocal {

    final private Database database;

    public static MyLocal getInstance(Application application){
        if (myLocal != null)
            return myLocal;
        myLocal = new MyLocal(application);
        return myLocal;
    }

    public MyLocal(Application application){
        database = Database.getInstance(application);
        dao = database.dbDao();
    }

    public void close(){
        database.close();
    }

    public Integer getID(Type type, Integer index){
        try {
            return new IDTask(dao, type).execute(index).get();
        } catch (ExecutionException | InterruptedException e){
            e.printStackTrace();
        }
        return 0;
    }

    public LiveData<List<Pair>> getLeagueList(){
        try{
            return new ReadTask(dao, Type.LEAGUE).execute().get().leagueList;
        } catch (ExecutionException | InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    public LiveData<List<Pair>> getTeamList(Integer leagueIndex){
        try{
            return new ReadTask(dao, Type.TEAM).execute(leagueIndex).get().teamList;
        } catch (ExecutionException | InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    public LiveData<List<Pair>> getPlayerList(Integer teamIndex){
        try{
            return new ReadTask(dao, Type.PLAYER).execute(teamIndex).get().playerList;
        } catch (ExecutionException | InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    public void insertTeamList(Table[] teamList){
        TeamTask teamTask = new TeamTask(dao, TeamTask.Query.CREATE);
        teamTask.execute(teamList);
    }

    public void insertPlayerList(Table[] playerList){
         PlayerTask playerTask = new PlayerTask(dao, PlayerTask.Query.CREATE);
        playerTask.execute(playerList);
    }

    private static MyLocal myLocal = null;
    final private DBDao dao;

    private static class PlayerTask extends AsyncTask<Table, Void, List<List<Table>>> {

        public enum Query{
            CREATE, READ, UPDATE, DELETE
        }

        final private DBDao dao;
        final private Query query;

        public PlayerTask(DBDao dao, Query query) {
            this.dao = dao;
            this.query = query;
        }

        @Override
        protected List<List<Table>> doInBackground(Table... players) {
            List<List<Table>> result = new ArrayList<>();
            try {
                for (Table team : players) {
                    result.add(accessLocal(team));
                }
            }
            catch (Exception e){
                Log.e("Error: ", e.getMessage());
            }
            return result;
        }

        private List<Table> accessLocal(Table team){
            switch (query){
                case CREATE:
                    TablePlayer tablePlayer = new TablePlayer(team.getID(), team.getParentID(), team.getName(), 3, "adb");
                    dao.insertPlayer(tablePlayer);
                    return null;
                default:
                    return null;
            }
        }
    }

    private static class TeamTask extends AsyncTask<Table, Void, List<List<Table>>> {

        public enum Query{
            CREATE, READ, UPDATE, DELETE
        }

        final private DBDao dao;
        final private Query query;

        public TeamTask(DBDao dao, Query query) {
            this.dao = dao;
            this.query = query;
        }

        @Override
        protected List<List<Table>> doInBackground(Table... teams) {
            List<List<Table>> result = new ArrayList<>();
            for (Table team : teams) {
                result.add(accessLocal(team));
            }
            return result;
        }

        private List<Table> accessLocal(Table team){
            switch (query){
                case CREATE:
                    TableTeam tableTeam = new TableTeam(team.getID(), team.getParentID(), team.getName());
                    dao.insertTeam(tableTeam);
                    return null;
                default:
                    return null;
            }
        }
    }

    private static class IDTask extends AsyncTask<Integer, Void, Integer> {

        final private DBDao dao;
        final private Type type;

        public IDTask(DBDao dao, Type type) {
            this.dao = dao;
            this.type = type;
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            switch (type){
                case LEAGUE:
                    return dao.getLeagueID(integers[0]);
                case TEAM:
                    return dao.getTeamID(integers[0]);
                case PLAYER:
                    return dao.getPlayerID(integers[0]);
                default:
                    return null;
            }
        }
    }

    private static class ReadTask extends AsyncTask<Integer, Void, ReadTask.Result>{
        public static class Result{
            public LiveData<List<Pair>> leagueList;
            public LiveData<List<Pair>> teamList;
            public LiveData<List<Pair>> playerList;
        }

        final private DBDao dao;
        final private Type type;

        public ReadTask(DBDao dao, Type type) {
            this.dao = dao;
            this.type = type;
        }

        @Override
        protected Result doInBackground(Integer... integers) {
            Result result = new Result();
            switch (type){
                case LEAGUE:
                    result.leagueList = dao.getLeagueList();
                    break;
                case TEAM:
                    dao.clearLeague();
                    dao.selectLeague(integers[0]);
                    result.teamList = dao.getTeamList();
                    break;
                case PLAYER:
                    dao.clearTeam();
                    dao.selectTeam(integers[0]);
                    result.playerList = dao.getPlayerList();
                    break;
                default:
                    break;
            }
            return result;
        }
    }

}

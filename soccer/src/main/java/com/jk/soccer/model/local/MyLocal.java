package com.jk.soccer.model.local;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.jk.soccer.etc.Type;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MyLocal {

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

    public LiveData<List<TableLeague>> getLeagueList(){
        try{
            return new ReadTask(dao, Type.LEAGUE).execute().get().leagueList;
        } catch (ExecutionException | InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    public LiveData<List<TableTeam>> getTeamList(Integer leagueIndex){
        try{
            return new ReadTask(dao, Type.TEAM).execute(leagueIndex).get().teamList;
        } catch (ExecutionException | InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    public LiveData<List<TablePlayer>> getPlayerList(Integer teamIndex){
        try{
            return new ReadTask(dao, Type.PLAYER).execute(teamIndex).get().playerList;
        } catch (ExecutionException | InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    public void insertTeamList(List<TableTeam> teamList){
        InsertTask insertTask = new InsertTask(dao, Type.TEAM);
        insertTask.execute(new InsertTask.Input(teamList, null));
    }

    public void insertPlayerList(List<TablePlayer> playerList){
        InsertTask insertTask = new InsertTask(dao, Type.PLAYER);
        insertTask.execute(new InsertTask.Input(null, playerList));
    }

    private static MyLocal myLocal = null;
    final private Database database;
    final private DBDao dao;

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

    private static class InsertTask extends AsyncTask<InsertTask.Input , Void, Void>{
        public static class Input{
            public List<TableTeam> team;
            public List<TablePlayer> player;

            public Input(List<TableTeam> team, List<TablePlayer> player) {
                this.team = team;
                this.player = player;
            }
        }

        final private DBDao dao;
        final private Type type;

        public InsertTask(DBDao dao, Type type) {
            this.dao = dao;
            this.type = type;
        }

        @Override
        protected Void doInBackground(Input... inputs) {
            accessLocal(inputs[0]);
            return null;
        }

        private void accessLocal(Input input){
            switch (type){
                case TEAM:
                    for (int i = 0; i < input.team.size(); i++)
                        dao.insertTeam(input.team.get(i));
                    break;
                case PLAYER:
                    for (int i = 0; i < input.player.size(); i++)
                        dao.insertPlayer(input.player.get(i));
                    break;
                default:
                    break;
            }
        }

    }

    private static class ReadTask extends AsyncTask<Integer, Void, ReadTask.Result>{
        public static class Result{
            public LiveData<List<TableLeague>> leagueList;
            public LiveData<List<TableTeam>> teamList;
            public LiveData<List<TablePlayer>> playerList;
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

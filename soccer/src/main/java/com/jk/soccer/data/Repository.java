package com.jk.soccer.data;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.jk.soccer.R;
import com.jk.soccer.data.local.Database;
import com.jk.soccer.data.local.TableMatch;
import com.jk.soccer.data.local.TablePlayer;
import com.jk.soccer.data.local.DBDao;
import com.jk.soccer.data.local.TableTeam;
import com.jk.soccer.data.remote.RetrofitClient;
import com.jk.soccer.data.response.Player;
import com.jk.soccer.etc.MyParser;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    //// Public ////

    public Repository(Application application){
        Context appContext = application.getApplicationContext();
        retrofitClient = new RetrofitClient(appContext.getString(R.string.baseUrl1));
        database = Database.getInstance(application);
        mDao = database.dbPlayerDao();
        initialize();
    }

    public LiveData<Player> getPlayerLiveData(Integer id){
        return mDao.findPlayerLiveData(id);
    }

    public LiveData<List<TablePlayer>> getPlayerLiveData(){
        return mDao.findPlayerLiveData();
    }

    public LiveData<TableTeam> getTeamLiveData(Integer teamId){
        return mDao.findTeamLiveData(teamId);
    }

    public LiveData<List<TableTeam>> getTeamLiveData(){
        return mDao.findTeamLiveData();
    }

    public LiveData<TableMatch> getMatchLiveData(Integer matchId){
        return mDao.findMatchLiveData(matchId);
    }

    public LiveData<List<TableMatch>> getMatchLiveData(){
        return mDao.findMatchLiveData();
    }

    public static Repository getInstance(Application application){
        if (repository == null){
            repository = new Repository(application);
        }
        return repository;
    }

    public void close(){
        database.close();
    }

    public TablePlayer getPlayer(Integer id){
        try {
            return new PlayerTask(mDao, Query.Read).execute(id).get().get(0);
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<TablePlayer> getPlayer(){
        try {
            return new PlayerTask(mDao, Query.Read).execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public TableTeam getTeam(Integer id){
        try {
            return new TeamTask(mDao, Query.Read).execute(id).get().get(0);
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<TableTeam> getTeam() {
        try {
            return new TeamTask(mDao, Query.Read).execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public TableMatch getMatch(Integer id){
        try {
            return new MatchTask(mDao, Query.Read).execute(id).get().get(0);
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<TableMatch> getMatch() {
        try {
            return new MatchTask(mDao, Query.Read).execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    //// Private ////

    private static Repository repository = null;
    final private RetrofitClient retrofitClient;
    final private Database database;
    final private DBDao mDao;

    private void getRemoteMatchList(Integer teamID) {
        TableTeam team = getTeam(teamID);
        ArrayList<Integer> fixtures = team.getFixtures();
        for (int i = 0; i < fixtures.size(); i++){
            getRemoteMatchInfo(fixtures.get(i));
        }
    }

    private void initialize(){
        List<TableTeam> teams = getTeam();
        for (int i = 0; i < teams.size(); i++){
            TableTeam team = teams.get(i);
            getRemoteTeamInfo(team.getId());
            if (team.getBookmark() > 0){
                getRemoteMatchList(team.getId());
            }
        }
    }

    public void bookmark(boolean bookmark, Integer id){
        TablePlayer player = getPlayer(id);
        Integer query = bookmark? Query.BookmarkOn : Query.BookmarkOff;
        new PlayerTask(mDao, query).execute(id);
        if (bookmark){
            getRemoteMatchList(player.getTeamID());
        }
    }

    private void getRemotePlayerInfo(int playerID){
        Call<ResponseBody> call = retrofitClient.apiService[0].getPlayer(playerID);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if (response.isSuccessful()){

                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
            }
        });
    }

    private void getRemoteTeamInfo(Integer teamId){
        Call<ResponseBody> call = retrofitClient.apiService[0].getTeam(teamId, "overview");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try{
                        String jsonString = response.body().string();
                        new TeamTask(mDao, Query.Update, jsonString).execute(teamId);
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {

            }
        });
    }

    private void getRemoteMatchInfo(Integer matchId){
        Call<ResponseBody> call = retrofitClient.apiService[0].getMatch(matchId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try{
                        String jsonString = response.body().string();
                        new MatchTask(mDao, Query.Create, jsonString).execute(matchId);
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
            }
        });
    }

    private interface Query{
        Integer Create = 0;
        Integer Delete = 1;
        Integer Read = 2;
        Integer Update = 3;
        Integer BookmarkOn = 4;
        Integer BookmarkOff = 5;
    }

    private static abstract class DBTask<T> extends AsyncTask<Integer, Void, List<T>>{

        protected DBDao dao;
        protected Integer query;
        protected String jsonString;

        public DBTask(DBDao dao, Integer query){
            this.dao = dao;
            this.query = query;
        }

        public DBTask(DBDao dao, Integer query, String jsonString){
            this.dao = dao;
            this.query = query;
            this.jsonString = jsonString;
        }

    }

    private static class PlayerTask extends DBTask<TablePlayer>{
        public PlayerTask(DBDao dao, Integer action){
            super(dao, action);
        }

        public PlayerTask(DBDao dao, Integer action, String jsonString){
            super(dao, action, jsonString);
        }

        @Override
        protected List<TablePlayer> doInBackground(Integer... ids) {
            List<TablePlayer> result = null;
            if (query.equals(Query.Read)) {
                result = (ids.length > 0)? dao.findPlayer(ids[0]) : dao.findPlayer();
            } else if (query.equals(Query.BookmarkOn)) {
                dao.registerPlayerBookmark(ids[0]);
            } else if (query.equals(Query.BookmarkOff)) {
                dao.unregisterPlayerBookmark(ids[0]);
            }
            return result;
        }
    }

    private static class TeamTask extends DBTask<TableTeam>{
        public TeamTask(DBDao dao, Integer query){
            super(dao, query);
        }

        public TeamTask(DBDao dao, Integer query, String jsonString){
            super(dao, query, jsonString);
        }

        @Override
        protected List<TableTeam> doInBackground(Integer... ids) {
            List<TableTeam> result = null;
            if (query.equals(Query.Update)) {
                TableTeam team = MyParser.myJSONTeam(jsonString, ids[0]);
                dao.updateTeamByID(
                        team.getRank(),
                        team.getTopRating(),
                        team.getTopGoal(),
                        team.getTopAssist(),
                        team.getFixture(),
                        ids[0]);
            } else if (query.equals(Query.Read)) {
                result = (ids.length > 0)? dao.findTeam(ids[0]) : dao.findTeam();
            }
            return result;
        }
    }

    private static class MatchTask extends DBTask<TableMatch>{
        public MatchTask(DBDao dao, Integer action){
            super(dao, action);
        }

        public MatchTask(DBDao dao, Integer action, String jsonString){
            super(dao, action, jsonString);
        }

        @Override
        protected List<TableMatch> doInBackground(Integer... ids) {
            List <TableMatch> result = null;
            if (query.equals(Query.Create)) {
                TableMatch match = MyParser.myJSONMatch(jsonString, ids[0]);
                dao.insertMatch(match);
            } else if (query.equals(Query.Read)) {
                result = (ids.length > 0)? dao.findMatch(ids[0]) : dao.findMatch();
            }
            return result;
        }
    }
}

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
        appContext = application.getApplicationContext();
        retrofitClient = new RetrofitClient(appContext.getString(R.string.baseUrl1));
        database = Database.getInstance(application);
        mDao = database.dbPlayerDao();
        initialize();
    }

    public LiveData<TablePlayer> getPlayerLiveData(Integer index){
        return mDao.findPlayerLiveData(getPlayerId(index));
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

    public TablePlayer getPlayer(Integer id){
        try {
            return new PlayerTask(mDao, Query.Read, "").execute(new TablePlayer(id)).get().get(0);
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<TablePlayer> getPlayer(){
        try {
            return new PlayerTask(mDao, Query.ReadAll, "").execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public TableTeam getTeam(Integer id){
        try {
            return new TeamTask(mDao, Query.Read, "").execute(new TableTeam(id)).get().get(0);
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<TableTeam> getTeam() {
        try {
            return new TeamTask(mDao, Query.ReadAll, "").execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public TableMatch getMatch(Integer id){
        try {
            return new MatchTask(mDao, Query.Read, "").execute(new TableMatch(id)).get().get(0);
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<TableMatch> getMatch() {
        try {
            return new MatchTask(mDao, Query.ReadAll, "").execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    //// Private ////

    private static Repository repository = null;
    private Context appContext;
    private RetrofitClient retrofitClient;
    private Database database;
    private DBDao mDao;

    private void initialize(){
        List<TablePlayer> players = getPlayer();
        int playerLength = players.size();
        for (int i = 0; i < playerLength; i++){
            TablePlayer player = players.get(i);
            Integer id = player.getId();
            getRemotePlayerInfo(id);
            if (player.getBookmark()){
                getRemoteTeamInfo(player.getTeamID());
            }
        }
    }

    private Integer getPlayerId(Integer index){
        return getPlayer().get(index).getId();
    }

    public void bookmark(boolean bookmark, Integer id){
        TablePlayer player = getPlayer(id);
        player.setBookmark(bookmark);
        new PlayerTask(mDao, Query.Bookmark, "").execute(player);
        Integer teamID = player.getTeamID();
        if (bookmark){
            getRemoteTeamInfo(teamID);
        }
    }

    private void getRemotePlayerInfo(int playerId){
        Call<ResponseBody> call = retrofitClient.apiService[0].getPlayer(playerId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try{
                        String jsonString = response.body().string();
                        new PlayerTask(mDao, Query.Update, jsonString).execute();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

    private void getRemoteTeamInfo(Integer teamId){
        Call<ResponseBody> call = retrofitClient.apiService[0].getTeam(teamId, "overview");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try{
                        String jsonString = response.body().string();
                        new TeamTask(mDao, Query.Create, jsonString).execute();
                        TableTeam team = getTeam(teamId);
                        ArrayList<Integer> fixtures = team.getFixtures();
                        for (int i = 0; i < fixtures.size(); i++){
                            getRemoteMatchInfo(fixtures.get(i));
                        }
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void getRemoteMatchInfo(Integer matchId){
        Call<ResponseBody> call = retrofitClient.apiService[0].getMatch(matchId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try{
                        String jsonString = response.body().string();
                        new MatchTask(mDao, Query.Create, jsonString).execute();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private interface Query{
        Integer Create = 0;
        Integer Delete = 1;
        Integer ReadAll = 2;
        Integer Read = 3;
        Integer Update = 4;
        Integer Bookmark = 5;
    }

    private static abstract class DBTask<T> extends AsyncTask<T, Void, List<T>>{

        protected DBDao dao;
        protected Integer query;
        protected String jsonString;

        public DBTask(DBDao dao, Integer query, String jsonString){
            this.dao = dao;
            this.query = query;
            this.jsonString = jsonString;
        }

    }

    private static class PlayerTask extends DBTask<TablePlayer>{

        public PlayerTask(DBDao dao, Integer action, String jsonString){
            super(dao, action, jsonString);
        }

        @Override
        protected List<TablePlayer> doInBackground(TablePlayer... players) {
            List<TablePlayer> result = null;
            TablePlayer player = (players.length > 0)? players[0] : null;
            if (query.equals(Query.Create)) {

            } else if (query.equals(Query.Delete)) {

            } else if (query.equals(Query.ReadAll)) {
                result = dao.findPlayer();
            } else if (query.equals(Query.Read)) {
                result = dao.findPlayer(player.getId());
            } else if (query.equals(Query.Update)) {
                player = new TablePlayer(jsonString);
                dao.updatePlayerInfoById(
                        player.getTeamID(),
                        player.getPosition(),
                        player.getHeight(),
                        player.getFoot(),
                        player.getAge(),
                        player.getShirt(),
                        player.getId());
            } else if (query.equals(Query.Bookmark)) {
                if (player.getBookmark())
                    dao.registerPlayerBookmark(player.getId());
                else
                    dao.unregisterPlayerBookmark(player.getId());
            }
            return result;
        }
    }

    private static class TeamTask extends DBTask<TableTeam>{
        public TeamTask(DBDao dao, Integer query, String jsonString){
            super(dao, query, jsonString);
        }

        @Override
        protected List<TableTeam> doInBackground(TableTeam... teams) {
            List<TableTeam> result = null;
            TableTeam team = (teams.length > 0)? teams[0] : null;
            if (query.equals(Query.Create)) {
                team = new TableTeam(jsonString);
                dao.insertTeam(team);
            } else if (query.equals(Query.Delete)) {

            } else if (query.equals(Query.ReadAll)) {
                result = dao.findTeam();
            } else if (query.equals(Query.Read)) {
                result = dao.findTeam(team.getId());
            }
            return result;
        }
    }

    private static class MatchTask extends DBTask<TableMatch>{
        public MatchTask(DBDao dao, Integer action, String jsonString){
            super(dao, action, jsonString);
        }

        @Override
        protected List<TableMatch> doInBackground(TableMatch... matches) {
            List <TableMatch> result = null;
            TableMatch match = (matches.length > 0)? matches[0] : null;
            if (query.equals(Query.Create)) {
                match = new TableMatch(jsonString);
                dao.insertMatch(match);
            } else if (query.equals(Query.Delete)) {

            } else if (query.equals(Query.ReadAll)) {
                result = dao.findMatch();
            }
            return result;
        }
    }
}

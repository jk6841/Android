package com.jk.soccer.data;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.jk.soccer.R;
import com.jk.soccer.data.local.Database;
import com.jk.soccer.data.local.Match;
import com.jk.soccer.data.local.Player;
import com.jk.soccer.data.local.DBDao;
import com.jk.soccer.data.local.Team;
import com.jk.soccer.data.remote.RetrofitClient;

import java.io.IOException;
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

    public LiveData<Player> getPlayerLiveData(Integer index){
        return mDao.findPlayerLiveData(getPlayerId(index));
    }

    public LiveData<List<Player>> getPlayerLiveData(){
        return mDao.findPlayerLiveData();
    }

    public LiveData<Team> getTeamLiveData(Integer teamId){
        return mDao.findTeamLiveData(teamId);
    }

    public LiveData<List<Team>> getTeamLiveData(){
        return mDao.findTeamLiveData();
    }

    public LiveData<Match> getMatchLiveData(Integer matchId){
        return mDao.findMatchLiveData(matchId);
    }

    public LiveData<List<Match>> getMatchLiveData(){
        return mDao.findMatchLiveData();
    }

    public static Repository getInstance(Application application){
        if (repository == null){
            repository = new Repository(application);
        }
        return repository;
    }

    public Player getPlayer(Integer id){
        try {
            return new PlayerTask(mDao, Query.Read).execute(new Player(id)).get().get(0);
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Player> getPlayer(){
        try {
            return new PlayerTask(mDao, Query.ReadAll).execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Team getTeam(Integer id){
        try {
            return new TeamTask(mDao, Query.Read).execute().get().get(0);
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Team> getTeam() {
        try {
            return new TeamTask(mDao, Query.ReadAll).execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Match getMatch(Integer id){
        try {
            return new MatchTask(mDao, Query.Read).execute().get().get(0);
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Match> getMatch() {
        try {
            return new MatchTask(mDao, Query.ReadAll).execute().get();
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
        List<Player> players = getPlayer();
        int playerLength = players.size();
        for (int i = 0; i < playerLength; i++){
            Integer id = players.get(i).getId();
            getRemotePlayerInfo(id);
        }
    }

    private Integer getPlayerId(Integer index){
        return getPlayer().get(index).getId();
    }

    public void bookmark(boolean bookmark, Integer id){
        Player player = getPlayer(id);
        player.setBookmark(bookmark);
        new PlayerTask(mDao, Query.Bookmark).execute(player);
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
                        Player player = new Player(playerId, jsonString);
                        new PlayerTask(mDao, Query.Update).execute(player);
                    } catch (IOException | NullPointerException e){
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
        Call<ResponseBody> call0 = retrofitClient.apiService[0].getTeam(teamId, "overview");
        call0.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try{
                        String jsonString = response.body().string();
                        Team team = new Team(teamId, jsonString);
                        new TeamTask(mDao, Query.Create).execute(team);
                        List<Integer> fixtures = team.getFixtures();
                        for (int i = 0; i < fixtures.size(); i++)
                            getRemoteMatchInfo(fixtures.get(i));
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
                        Match match = new Match(matchId, jsonString);
                        new MatchTask(mDao, Query.Create).execute(match);
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

        public DBTask(DBDao dao, Integer query){
            this.dao = dao;
            this.query = query;
        }

    }

    private static class PlayerTask extends DBTask<Player>{

        public PlayerTask(DBDao dao, Integer action){
            super(dao, action);
        }

        @Override
        protected List<Player> doInBackground(Player... players) {
            List<Player> result = null;
            Player player = (players.length > 0)? players[0] : null;
            if (query.equals(Query.Create)) {

            } else if (query.equals(Query.Delete)) {

            } else if (query.equals(Query.ReadAll)) {
                result = dao.findPlayer();
            } else if (query.equals(Query.Read)) {
                result = dao.findPlayer(player.getId());
            } else if (query.equals(Query.Update)) {
                dao.updatePlayerInfoById(
                        player.getTeamID(),
                        player.getPosition(),
                        player.getHeight(),
                        player.getFoot(),
                        player.getAge(),
                        player.getShirt(),
                        player.getId());
            } else if (query.equals(Query.Bookmark)) {
                if (player.isBookmark())
                    dao.registerPlayerBookmark(player.getId());
                else
                    dao.unregisterPlayerBookmark(player.getId());
            }
            return result;
        }
    }

    private static class TeamTask extends DBTask<Team>{
        public TeamTask(DBDao dao, Integer query){
            super(dao, query);
        }

        @Override
        protected List<Team> doInBackground(Team... teams) {
            List<Team> result = null;
            Team team = (teams.length > 0)? teams[0] : null;
            if (query.equals(Query.Create)) {
                dao.insertTeam(team);
            } else if (query.equals(Query.Delete)) {

            } else if (query.equals(Query.ReadAll)) {
                result = dao.findTeam();
            }
            return result;
        }
    }

    private static class MatchTask extends DBTask<Match>{
        public MatchTask(DBDao dao, Integer action){
            super(dao, action);
        }

        @Override
        protected List<Match> doInBackground(Match... matches) {
            List <Match> result = null;
            Match match = (matches.length > 0)? matches[0] : null;
            if (query.equals(Query.Create)) {
                dao.insertMatch(match);
            } else if (query.equals(Query.Delete)) {

            } else if (query.equals(Query.ReadAll)) {
                result = dao.findMatch();
            }
            return result;
        }
    }
}

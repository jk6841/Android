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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    //// Public ////

    public interface Object{
        Integer Player = 0;
        Integer Team = 1;
        Integer Match = 2;
    }

    public Repository(Application application){
        appContext = application.getApplicationContext();
        retrofitClient = new RetrofitClient(appContext.getString(R.string.baseUrl1));
        database = Database.getInstance(application);
        mDao = database.dbPlayerDao();
        initialize();
    }

    public LiveData<Player> getPlayer(Integer index){
        return mDao.findPlayerById(getPlayerId(index));
    }

    public LiveData<List<Player>> getPlayer(){
        return mDao.findPlayerAll();
    }

    public LiveData<Team> getTeam(Integer teamId){
        return mDao.findTeamById(teamId);
    }

    public LiveData<Team> getTeamByPlayerIndex(Integer index) {
        return mDao.findTeamByPlayerId(getPlayerId(index));
    }

    public LiveData<List<Team>> getTeam(){
        return mDao.findTeamAll();
    }

    public LiveData<Match> getMatch(Integer matchId){
        return mDao.findMatchById(matchId);
    }

    public LiveData<List<Match>> getMatch(){
        return mDao.findMatchAll();
    }

    public void bookmark(Integer object, boolean bookmark, Integer id){
        bookmarkInternal(object, bookmark, id);
    }

    public void bookmark(Integer object, boolean bookmark){
        bookmarkInternal(object, bookmark, ID.ALL);
    }

    public static Repository getInstance(Application application){
        if (repository == null){
            repository = new Repository(application);
        }
        return repository;
    }

    public List<Player> getPlayerInit(){
        try{
            return new PlayerTask(mDao, ACTION.Read).execute().get();
        } catch(ExecutionException | InterruptedException e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Match> getMatchInit(){
        try{
            return new MatchTask(mDao, ACTION.Read).execute().get();
        } catch(ExecutionException | InterruptedException e){
            e.printStackTrace();
            return null;
        }
    }

    //// Private ////

    private interface ACTION {
        Integer Create = 0;
        Integer Read = 1;
        Integer Update = 2;
        Integer Delete = 3;
        Integer BookmarkOn = 4;
        Integer BookmarkOff = 5;
    }

    private interface ID{
        Integer ALL = -1;
    }

    private static Repository repository = null;
    private Context appContext;
    private RetrofitClient retrofitClient;
    private Database database;
    private DBDao mDao;

    private void initialize(){
        List<Player> players = getPlayerInit();
        int playerLength = players.size();
        for (int i = 0; i < playerLength; i++){
            Integer id = players.get(i).getId();
            getRemotePlayerInfo(id);
        }
    }

    private Integer getPlayerId(Integer index){
        return getPlayerInit().get(index).getId();
    }

    private void bookmarkInternal(Integer object, boolean bookmark, Integer id){
        Integer action = (bookmark)? ACTION.BookmarkOn : ACTION.BookmarkOff;
        if (object.equals(Object.Player))
            new PlayerTask(mDao, action).execute(new Player(id));
        else if (object.equals(Object.Team))
            new TeamTask(mDao, action).execute(new Team(id));
        else if (object.equals(Object.Match))
            new MatchTask(mDao, action).execute(new Match(id));
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
                        new PlayerTask(mDao, ACTION.Update).execute(player);
                        getRemoteTeamInfo(player.getTeamID());
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
                        new TeamTask(mDao, ACTION.Create).execute(team);
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
                        new MatchTask(mDao, ACTION.Create).execute(match);
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

    private static abstract class MyAsyncTask<T> extends AsyncTask<T, Void, List<T>>{

        protected DBDao dao;
        protected Integer action;

        public MyAsyncTask(DBDao dao, Integer action){
            this.dao = dao;
            this.action = action;
        }
    }

    private static class PlayerTask extends MyAsyncTask<Player>{
        public PlayerTask(DBDao dao, Integer action){
            super(dao, action);
        }

        @Override
        protected List<Player> doInBackground(Player... players) {
            List<Player> result = null;
            Player player = null;
            Integer id = ID.ALL;
            if (players.length > 0){
                player = players[0];
                id = player.getId();
            }
            if (action.equals(ACTION.Read)){
                result = dao.playerInit();
            }
            else if (action.equals(ACTION.Update)){
                dao.updatePlayerInfoById(
                        player.getTeamID(),
                        player.getPosition(),
                        player.getHeight(),
                        player.getFoot(),
                        player.getAge(),
                        player.getShirt(),
                        player.getId());
            }
            else{
                boolean bookmark = (action.equals(ACTION.BookmarkOn));
                if (id.equals(ID.ALL)){
                    dao.updatePlayerBookmarkAll(bookmark);
                }
                else{
                    dao.updatePlayerBookmarkById(bookmark, id);
                }
            }
            return result;
        }
    }

    private static class TeamTask extends MyAsyncTask<Team>{
        public TeamTask(DBDao dao, Integer action){
            super(dao, action);
        }

        @Override
        protected List<Team> doInBackground(Team... teams) {
            List<Team> result = null;
            Team team;
            Integer id = ID.ALL;
            if (teams.length > 0){
                team = teams[0];
                id = team.getId();
            }
            if (action.equals(ACTION.Create)){
                dao.insertTeam(teams[0]);
            }
            else if (action.equals(ACTION.Delete)){
                dao.deleteTeamById(id);
            }
            else{
                boolean bookmark = (action.equals(ACTION.BookmarkOn));
                if (id.equals(ID.ALL)){
                    dao.updatePlayerBookmarkAll(bookmark);
                }
                else{
                    dao.updatePlayerBookmarkById(bookmark, id);
                }
            }
            return result;
        }
    }

    private static class MatchTask extends MyAsyncTask<Match>{

        private ArrayList<String> bigLeague = new ArrayList<>();

        public MatchTask(DBDao dao, Integer action){
            super(dao, action);
            bigLeague.add("ENG");
            bigLeague.add("GER");
            bigLeague.add("ESP");
            bigLeague.add("FRA");
            bigLeague.add("ITA");
        }

        @Override
        protected List<Match> doInBackground(Match... matches) {
            if (action.equals(ACTION.Create)){
                dao.insertMatch(matches[0]);
            } else if (action.equals(ACTION.Read)){
                return dao.matchInit();
            } else if (action.equals(ACTION.Update)){

            }
            return null;
        }
    }
}

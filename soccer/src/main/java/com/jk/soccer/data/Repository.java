package com.jk.soccer.data;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
        baseUrl1 = appContext.getString(R.string.baseUrl1);
        baseUrl2 = appContext.getString(R.string.baseUrl2);
        retrofitClient = new RetrofitClient(baseUrl1, baseUrl2);
        database = Database.getInstance(application);
        mDao = database.dbPlayerDao();
        dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        initialize();
    }

    public LiveData<Player> getPlayer(Integer playerId){
        return mDao.findPlayerById(playerId);
    }

    public LiveData<List<Player>> getPlayer(){
        return mDao.findPlayerAll();
    }

    public LiveData<Team> getTeam(Integer teamId){
        return mDao.findTeamById(teamId);
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

    //// Private ////

    private interface ACTION {
        Integer Create = 0;
        Integer Read = 1;
        Integer Update = 2;
        Integer Delete = 3;
        Integer BookmarkOn = 4;
        Integer BookmarkOff = 5;
    }

    private interface MODE {
        Integer String = 0;
        Integer Image = 1;
    }

    private interface ID{
        Integer ALL = -1;
    }

    private static Repository repository = null;
    private Context appContext;
    private RetrofitClient retrofitClient;
    private Database database;
    private DBDao mDao;

    private String baseUrl1;
    private String baseUrl2;

    private SimpleDateFormat dateFormat;

    private List<Player> players;

    private void initialize(){
        try {
            players = new PlayerTask(mDao, ACTION.Read, 0, 0).execute().get();
            int length = players.size();
            for (int i = 0; i < length; i++){
                Integer id = players.get(i).getId();
                remotePlayerString(id);
                remotePlayerImage(id);
            }
        } catch (ExecutionException | InterruptedException e){
            e.printStackTrace();
        }
    }

    public void bookmarkInternal(Integer object, boolean bookmark, Integer id){
        Integer mode = (bookmark)? ACTION.BookmarkOn : ACTION.BookmarkOff;
        if (object.equals(Object.Player))
            new PlayerTask(mDao, mode, 0, id);
        else if (object.equals(Object.Team))
            new TeamTask(mDao, mode, 0, id);
        else if (object.equals(Object.Match))
            new MatchTask(mDao, mode, 0, id);
    }

    private void remotePlayerString(int playerId){
        Call<ResponseBody> call = retrofitClient.apiService[0].getPlayerString(playerId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try{
                        String jsonString = response.body().string();
                        Player player = new Player(playerId, jsonString);
                        new PlayerTask(mDao, ACTION.Update, MODE.String, playerId).execute(player);
                        Team team = new Team(player.getTeamID(), jsonString);
                        new TeamTask(mDao, ACTION.Create, MODE.String, team.getId()).execute(team);
                        remoteTeamImage(team.getId());
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

    private void remotePlayerImage(int playerId){
        Call<ResponseBody> call = retrofitClient.apiService[1].getPlayerImage(playerId + ".png");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try{
                        byte[] bytes = response.body().bytes();
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        Player player = new Player(playerId, bitmap);
                        new PlayerTask(mDao, ACTION.Update, MODE.Image, playerId).execute(player);
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

    private void remoteTeamImage(int teamId) {
        Call<ResponseBody> call = retrofitClient.apiService[0].getTeamImage(teamId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        byte[] bytes = response.body().bytes();
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        Team team = new Team(teamId, bitmap);
                        new TeamTask(mDao, ACTION.Create, MODE.Image, teamId).execute(team);
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

    private void remoteMatchString(Date date){
        Call<ResponseBody> call = retrofitClient.apiService[0].getMatchString(date);
    }

    private static abstract class MyAsyncTask<T> extends AsyncTask<T, Void, List<T>>{

        protected DBDao dao;
        protected Integer action;
        protected Integer mode;
        protected Integer id;

        public MyAsyncTask(DBDao dao, Integer action, Integer mode, Integer id){
            this.dao = dao;
            this.action = action;
            this.mode = mode;
            this.id = id;
        }
    }

    private static class PlayerTask extends MyAsyncTask<Player>{
        public PlayerTask(DBDao dao, Integer action, Integer mode, Integer id){
            super(dao, action, mode, id);
        }

        @Override
        protected List<Player> doInBackground(Player... players) {
            List<Player> result = null;
            if (action.equals(ACTION.Read)){
                result = dao.repoPlayerAll();
            }
            else if (action.equals(ACTION.Update)){
                Player player = players[0];
                if (mode.equals(MODE.String)){
                    dao.updatePlayerTeamIDById(player.getTeamID(), id);
                    dao.updatePlayerPositionById(player.getPosition(), id);
                    dao.updatePlayerHeightById(player.getHeight(), id);
                    dao.updatePlayerFootById(player.getFoot(), id);
                    dao.updatePlayerAgeById(player.getAge(), id);
                    dao.updatePlayerShirtById(player.getShirt(), id);
                }
                else if (mode.equals(MODE.Image)){
                    dao.updatePlayerImageById(player.getImage(), id);
                }

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
        public TeamTask(DBDao dao, Integer action, Integer mode, Integer id){
            super(dao, action, mode, id);
        }

        @Override
        protected List<Team> doInBackground(Team... teams) {
            List<Team> result = null;
            if (action.equals(ACTION.Create)){
                if (mode.equals(MODE.String)){
                    dao.insertTeam(teams[0]);
                }
                else if (mode.equals(MODE.Image)){
                    dao.updateTeamImageById(teams[0].getImage(), id);
                }
            }
            else if (action.equals(ACTION.Update)){
                if (mode.equals(MODE.String)){
                    dao.updateTeamImageById(teams[0].getImage(), id);
                }
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
        public MatchTask(DBDao dao, Integer action, Integer mode, Integer id){
            super(dao, action, mode, id);
        }

        @Override
        protected List<Match> doInBackground(Match... matches) {
            return null;
        }
    }
}

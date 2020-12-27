package com.jk.soccer.model;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.jk.soccer.R;
import com.jk.soccer.etc.Pair;
import com.jk.soccer.model.local.Database;
import com.jk.soccer.model.local.MyLocal;
import com.jk.soccer.model.local.Table;
import com.jk.soccer.model.local.TableMatch;
import com.jk.soccer.model.local.TablePlayer;
import com.jk.soccer.model.local.DBDao;
import com.jk.soccer.model.local.TableTeam;
import com.jk.soccer.etc.Type;
import com.jk.soccer.model.remote.MyRemote;
import com.jk.soccer.model.remote.RetrofitClient;
import com.jk.soccer.etc.Player;
import com.jk.soccer.model.local.MyParser;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
        mDao = database.dbDao();
        myLocal = MyLocal.getInstance(application);
        myRemote = MyRemote.getInstance(application);
        initialize();
    }

    public LiveData<Player> getPlayerLiveData(Integer id){
        return mDao.findPlayerLiveData(id);
    }

    public List<LiveData<Player>> getPlayerLiveData(){
        LiveData<List<Player>> listFromDB = mDao.findPlayerLiveData();
        List<LiveData<Player>> result = new ArrayList<>();
        int num = count(0).get(0);
        for (int i = 0; i < num; i++){
            int k = i;
            LiveData<Player> item = Transformations.map(listFromDB, input -> input.get(k));
            result.add(item);
        }
        return result;
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

    public List<LiveData<TableMatch>> getMatchLiveData(){
        LiveData<List<TableMatch>> listFromDB = mDao.findMatchLiveData();
        List<LiveData<TableMatch>> result = new ArrayList<>();
        int num = getMatch().size();
        for (int i = 0; i < num; i++){
            int k = i;
            LiveData<TableMatch> item = Transformations.map(listFromDB, input -> input.get(k));
            result.add(item);
        }
        return result;
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
    final private MyLocal myLocal;
    final private MyRemote myRemote;

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

    private static class CountTask extends AsyncTask<Integer, Void, List<Integer>>{
        private DBDao dao;
        public CountTask(DBDao dao){
            this.dao = dao;
        }

        @Override
        protected List<Integer> doInBackground(Integer... integers) {
            switch (integers[0]){
                case 0:
                    return dao.countPlayer();
                case 1:
                    return dao.countMatch();
                case 2:
                    return dao.countEvent();
                case 3:
                    return dao.countHomeLineup();
                case 4:
                    return dao.countAwayLineup();
                default:
                    return null;
            }
        }
    }

    public List<Integer> count(Integer target){
        try {
            return new CountTask(mDao).execute(target).get();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public String NetworkTest() {
        return myRemote.download(Type.PLAYER, 212867, "");
    }

    public List<Pair> getLeagueList(){
        return myLocal.getLeagueList();
    }

    public List<Pair> getTeamList(Integer leagueID){
        List<Pair> tryLocal = myLocal.getTeamList(leagueID);
        if (tryLocal.size() != 0){
            return tryLocal;
        }
        String leagueString = myRemote.download(Type.LEAGUE, leagueID, "table");
        Table[] teamList = MyParser.myTeamList(leagueString);
        myLocal.insertList(teamList);
        return myLocal.getTeamList(leagueID);
    }

    public List<Pair> getPlayerList(Integer teamID){
        List<Pair> tryLocal = myLocal.getPlayerList(teamID);
        if (tryLocal.size() != 0){
            return tryLocal;
        }
        String teamString = myRemote.download(Type.TEAM, teamID, "squad");
        Table[] playerList = MyParser.myPlayerList(teamString);
        myLocal.insertList(playerList);
        return myLocal.getPlayerList(teamID);
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



}

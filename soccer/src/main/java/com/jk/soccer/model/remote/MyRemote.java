package com.jk.soccer.model.remote;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.jk.soccer.R;
import com.jk.soccer.etc.Player;
import com.jk.soccer.etc.MyCallback;
import com.jk.soccer.etc.Team;
import com.jk.soccer.etc.enumeration.Type;
import com.jk.soccer.model.local.TableSearch;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.jk.soccer.etc.MyJson.myJSONArray;
import static com.jk.soccer.etc.MyJson.myJSONBoolean;
import static com.jk.soccer.etc.MyJson.myJSONInt;
import static com.jk.soccer.etc.MyJson.myJSONObject;
import static com.jk.soccer.etc.MyJson.myJSONString;

public class MyRemote {

    public static MyRemote getInstance(Application application){
        if (myRemote != null)
            return myRemote;
        myRemote = new MyRemote(application);
        return myRemote;
    }

    public MyRemote(Application application){
        Context appContext = application.getApplicationContext();
        RetrofitClient retrofitClient = new RetrofitClient(appContext.getString(R.string.baseUrl1));
        apiService = retrofitClient.getApiService(0);
    }

    //// Main thread cannot call this.
    public String downloadSync(Type type, Integer ID, String tab){
        Call<ResponseBody> call;
        switch (type) {
            case PERSON:
                call = apiService.getPlayer(ID);
                break;
            case TEAM:
                call = apiService.getTeam(ID, tab);
                break;
            case LEAGUE:
                call = apiService.getLeague(ID, tab);
                break;
            case MATCH:
                call = apiService.getMatch(ID);
                break;
            default:
                return null;
        }
        try {
            ResponseBody body = call.execute().body();
            return (body != null)? body.string() : null;
        } catch (Exception e) {
            return null;
        }
    }

    public void downloadAsync(Type type, Integer ID, String tab, Callback<ResponseBody> callback){
        switch (type) {
            case PERSON:
                apiService.getPlayer(ID).enqueue(callback);
                break;
            case TEAM:
                apiService.getTeam(ID, tab).enqueue(callback);
                break;
            case LEAGUE:
                apiService.getLeague(ID, tab).enqueue(callback);
                break;
            case MATCH:
                apiService.getMatch(ID).enqueue(callback);
                break;
            default:
                break;
        }
    }

    public void downloadTeamList(Integer leagueID, MyCallback<List<TableSearch>> callback){
        apiService.getLeague(leagueID, "table").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call,
                                   @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    ResponseBody responseBody = response.body();
                    try {
                        String responseString = (responseBody != null)? responseBody.string() : "";
                        List<TableSearch> teamList = parseTeamList(responseString);
                        callback.onComplete(teamList);
                    } catch (Exception e){
                        Log.e("Error: ", e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call,
                                  @NonNull Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });
    }

    public void downloadPlayerList(Integer teamID, MyCallback<List<TableSearch>> callback){
        apiService.getTeam(teamID, "squad").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call,
                                   @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    ResponseBody responseBody = response.body();
                    try {
                        String responseString = (responseBody != null)? responseBody.string() : "";
                        List<TableSearch> playerList = parsePlayerList(responseString);
                        callback.onComplete(playerList);
                    } catch (Exception e){
                        Log.e("Error: ", e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call,
                                  @NonNull Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });
    }

    public void downloadTeam(Integer ID, MyCallback<Team> callback){
        apiService.getTeam(ID, "overview").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call,
                                   @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    ResponseBody responseBody = response.body();
                    try {
                        String responseString
                                = (responseBody != null)? responseBody.string() : "";
                        Team team = parseTeam(responseString);
                        callback.onComplete(team);
                    } catch (Exception e){
                        Log.e("Error: ", e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call,
                                  @NonNull Throwable t) {

            }
        });
    }

    public void downloadPlayer(Integer ID, MyCallback<Player> callback){
        apiService.getPlayer(ID).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call,
                                   @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    ResponseBody responseBody = response.body();
                    try {
                        String responseString
                                = (responseBody != null)? responseBody.string() : "";
                        Player player = parsePlayer(responseString);
                        callback.onComplete(player);
                    } catch (Exception e){
                        Log.e("Error: ", e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call,
                                  @NonNull Throwable t) {
            }
        });
    }

    private static List<TableSearch> parseTeamList(String jsonString){
        JSONObject jsonObject = myJSONObject(jsonString);
        JSONObject jsonTableData = myJSONObject(jsonObject, "tableData");
        JSONArray jsonTables = myJSONArray(jsonTableData, "tables");
        JSONObject jsonTables2 = myJSONObject(jsonTables, 0);
        Integer leagueID = myJSONInt(jsonTables2, "leagueId");
        JSONArray jsonTable = myJSONArray(jsonTables2, "table");
        if (jsonTable == null)
            return null;
        List<TableSearch> teamList = new ArrayList<>();
        for (int i = 0; i < jsonTable.length(); i++){
            JSONObject jsonTeam = myJSONObject(jsonTable, i);
            Integer teamID = myJSONInt(jsonTeam, "id");
            String teamName = myJSONString(jsonTeam, "name");
            teamList.add(new TableSearch(teamID, leagueID, Type.TEAM, teamName));
        }
        return teamList;
    }

    private static Team parseTeam(String jsonString){
        Team team = new Team();
        JSONObject jsonObject = myJSONObject(jsonString);
        JSONObject jsonDetails = myJSONObject(jsonObject, "details");
        team.setID(myJSONString(jsonDetails, "id"));
        team.setName(myJSONString(jsonDetails, "name"));
        JSONArray jsonArray = myJSONArray(jsonObject, "fixtures");
        List<Team.Fixture> fixtures = new ArrayList<>();
        for (int i = 0 ; i < jsonArray.length(); i++){
            JSONObject jsonFixture = myJSONObject(jsonArray, i);
            Team.Fixture fixture = new Team.Fixture();
            fixture.setID(myJSONInt(jsonFixture, "id"));
            JSONObject jsonHome = myJSONObject(jsonFixture, "home");
            JSONObject jsonAway = myJSONObject(jsonFixture, "away");
            fixture.setHomeID(myJSONInt(jsonHome, "id"));
            fixture.setHomeName(myJSONString(jsonHome, "name"));
            fixture.setAwayID(myJSONInt(jsonAway, "id"));
            fixture.setAwayName(myJSONString(jsonAway, "name"));
            fixture.setColor(myJSONString(jsonFixture, "color"));
            JSONObject jsonStatus = myJSONObject(jsonFixture, "status");
            fixture.setStarted(myJSONBoolean(jsonStatus, "started"));
            fixture.setCancelled(myJSONBoolean(jsonStatus, "cancelled"));
            fixture.setFinished(myJSONBoolean(jsonStatus, "finished"));
            fixture.setScore(myJSONString(jsonStatus, "scoreStr"));
            fixture.setDate(myJSONString(jsonStatus, "startDateStr"));
            fixtures.add(fixture);
        }
        team.setFixtures(fixtures);

        JSONObject jsonTopPlayers = myJSONObject(jsonObject, "topPlayers");
        JSONArray jsonTopGoals = myJSONArray(jsonTopPlayers, "byGoals");
        List<Team.TopPlayer> topGoal = new ArrayList<>();
        for (int i = 0; i < jsonTopGoals.length(); i++){
            Team.TopPlayer p = new Team.TopPlayer();
            JSONObject jsonItem = myJSONObject(jsonTopGoals, i);
            p.setID(myJSONInt(jsonItem, "id"));
            p.setName(myJSONString(jsonItem, "name"));
            p.setGoal((myJSONInt(jsonItem, "goals")));
            p.setAssist(myJSONInt(jsonItem, "assists"));
            p.setCountry(myJSONString(jsonItem, "ccode").toLowerCase());
            topGoal.add(p);
        }
        team.setTopGoal(topGoal);

        List<Team.TopPlayer> topAssist = new ArrayList<>();
        JSONArray jsonTopAssists = myJSONArray(jsonTopPlayers, "byAssists");
        for (int i = 0; i < jsonTopAssists.length(); i++){
            Team.TopPlayer p = new Team.TopPlayer();
            JSONObject jsonItem = myJSONObject(jsonTopAssists, i);
            p.setID(myJSONInt(jsonItem, "id"));
            p.setName(myJSONString(jsonItem, "name"));
            p.setGoal((myJSONInt(jsonItem, "goals")));
            p.setAssist(myJSONInt(jsonItem, "assists"));
            p.setCountry(myJSONString(jsonItem, "ccode").toLowerCase());
            topGoal.add(p);
        }
        team.setTopAssist(topAssist);

        JSONObject jsonVenue = myJSONObject(jsonObject, "venue");
        JSONObject jsonWidget = myJSONObject(jsonVenue, "widget");
        team.setStadium(myJSONString(jsonWidget, "name"));
        team.setCity(myJSONString(jsonWidget, "city"));

        return team;
    }

    private static List<TableSearch> parsePlayerList(String jsonString){
        JSONObject jsonObject = myJSONObject(jsonString);
        JSONObject jsonDetails = myJSONObject(jsonObject, "details");
        Integer teamID = myJSONInt(jsonDetails, "id");
        JSONArray jsonSquad = myJSONArray(jsonObject, "squad");
        if (jsonSquad == null)
            return null;
        List<TableSearch> playerList = new ArrayList<>();
        for (int i = 0; i < jsonSquad.length(); i++){
            JSONArray jsonSquadItem = myJSONArray(myJSONArray(jsonSquad, i), 1);
            if (jsonSquadItem != null){
                for (int j = 0; j < jsonSquadItem.length(); j++){
                    JSONObject jsonPlayer = myJSONObject(jsonSquadItem, j);
                    Integer playerID = myJSONInt(jsonPlayer, "id");
                    String name = myJSONString(jsonPlayer, "name");
                    playerList.add(new TableSearch(playerID, teamID, Type.PERSON, name));
                }
            }
        }
        return playerList;
    }

    private static Player parsePlayer(String jsonString){
        Player player = new Player();
        JSONObject jsonObject = myJSONObject(jsonString);
        player.setName(myJSONString(jsonObject, "name"));
        JSONObject jsonOrigin = myJSONObject(jsonObject, "origin");
        player.setTeamID(myJSONString(jsonOrigin, "teamId"));
        player.setTeamID(myJSONString(jsonOrigin, "teamId"));
        player.setTeamName(myJSONString(jsonOrigin, "teamName"));
        JSONObject jsonPositionDesc = myJSONObject(jsonOrigin, "positionDesc");
        player.setPosition(myJSONString(jsonPositionDesc, "primaryPosition"));
        JSONArray jsonPlayerProps = myJSONArray(jsonObject, "playerProps");
        for (int i = 0; i < jsonPlayerProps.length(); i++){
            JSONObject jsonItem = myJSONObject(jsonPlayerProps, i);
            String item = myJSONString(jsonItem, "value");
            switch (myJSONString(jsonItem, "title")){
                case "Height":
                    player.setHeight(item);
                    break;
                case "Preferred foot":
                    player.setFoot(item);
                    break;
                case "Age":
                    player.setAge(item);
                    break;
                case "Country":
                    player.setCountryName(item);
                    JSONObject jsonIcon = myJSONObject(jsonItem, "icon");
                    player.setCountryID(myJSONString(jsonIcon, "id").toLowerCase());
                    break;
                case "Shirt":
                    player.setShirt(myJSONString(jsonItem, "value"));
                    break;
                default:
                    break;
            }
        }
        return player;
    }

    private static MyRemote myRemote = null;
    private static ApiService apiService;

}

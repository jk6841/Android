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

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyRemote {

    public static MyRemote getInstance(Application application){
        if (myRemote != null)
            return myRemote;
        myRemote = new MyRemote(application);
        return myRemote;
    }

    private MyRemote(Application application){
        Context appContext = application.getApplicationContext();
        RetrofitClient retrofitClient = RetrofitClient.getInstance();
        retrofitClient.register(appContext.getString(R.string.baseUrl1));
        apiService = retrofitClient.getApiService(0);
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
                        List<TableSearch> teamList = Parser.parseTeamList(responseString);
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
                        List<TableSearch> playerList = Parser.parsePlayerList(responseString);
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
                        Team team = Parser.parseTeam(responseString);
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
                        //Player player = parsePlayer(responseString);
                        Player player = Parser.parsePlayer(responseString);
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

    private static MyRemote myRemote = null;
    private static ApiService apiService;

}

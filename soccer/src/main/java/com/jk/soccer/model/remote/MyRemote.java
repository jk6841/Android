package com.jk.soccer.model.remote;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.jk.soccer.R;
import com.jk.soccer.etc.League;
import com.jk.soccer.etc.Player;
import com.jk.soccer.etc.MyCallback;
import com.jk.soccer.etc.Team;
import com.jk.soccer.etc.throwable.MyThrowable;
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
        className = this.getClass().getName();
    }

    public void downloadLeague(Integer leagueID, MyCallback<League> callback){
        apiService.getLeague(leagueID, "overview").enqueue(new RetrofitCallback() {
            @Override
            public void onSuccess(@NonNull String responseString) {

            }
        });
    }

    public void downloadTeamList(Integer leagueID, MyCallback<List<TableSearch>> callback){
        apiService.getLeague(leagueID, "table").enqueue(new RetrofitCallback() {
            @Override
            public void onSuccess(@NonNull String responseString) {
                callback.onComplete(Parser.parseTeamList(responseString));
            }
        });
    }

    public void downloadTeam(Integer ID, MyCallback<Team> callback){
        apiService.getTeam(ID, "overview").enqueue(new RetrofitCallback() {
            @Override
            public void onSuccess(@NonNull String responseString) {
                callback.onComplete(Parser.parseTeam(responseString));
            }
        });
    }

    public void downloadPlayerList(Integer teamID, MyCallback<List<TableSearch>> callback){
        apiService.getTeam(teamID, "squad").enqueue(new RetrofitCallback() {
            @Override
            public void onSuccess(@NonNull String responseString) {
                callback.onComplete(Parser.parsePlayerList(responseString));
            }
        });
    }

    public void downloadPlayer(Integer ID, MyCallback<Player> callback){
        apiService.getPlayer(ID).enqueue(new RetrofitCallback() {
            @Override
            public void onSuccess(@NonNull String responseString) {
                callback.onComplete(Parser.parsePlayer(responseString));
            }
        });
    }

    private static MyRemote myRemote = null;
    private static ApiService apiService;
    private static String className;

    private static abstract class RetrofitCallback implements Callback<ResponseBody>{

        public abstract void onSuccess(@NonNull String responseString);

        @Override
        public void onResponse(@NonNull Call<ResponseBody> call,
                               @NonNull Response<ResponseBody> response){
            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    try {
                        onSuccess(responseBody.string());
                    } catch (Exception e) {
                        printLog(e);
                    }
                }
            }
        }

        @Override
        public void onFailure(@NonNull Call<ResponseBody> call,
                              @NonNull Throwable t) {
            printLog(t);
        }
    }

    private static void printLog(Throwable t){
        MyThrowable.printLog(className, t);
    }

}

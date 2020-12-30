package com.jk.soccer.model.remote;

import android.app.Application;
import android.content.Context;

import com.jk.soccer.R;
import com.jk.soccer.etc.enumeration.Type;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class MyRemote {

    public static MyRemote getInstance(Application application){
        if (myRemote != null)
            return myRemote;
        myRemote = new MyRemote(application);
        return myRemote;
    }

    public MyRemote(Application application){
        Context appContext = application.getApplicationContext();
        retrofitClient = new RetrofitClient(appContext.getString(R.string.baseUrl1));
    }

    //// Main thread cannot call this.
    public String downloadSync(Type type, Integer ID, String tab){
        Call<ResponseBody> call;
        ApiService apiService = retrofitClient.getApiService(0);
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
        ApiService apiService = retrofitClient.getApiService(0);
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

    private static MyRemote myRemote = null;
    private static RetrofitClient retrofitClient;

}

package com.jk.soccer.model.remote;

import android.app.Application;
import android.content.Context;

import com.jk.soccer.R;
import com.jk.soccer.etc.Type;

import okhttp3.ResponseBody;
import retrofit2.Call;

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
    public String download(Type type, Integer ID, String tab){
        Call<ResponseBody> call;
        ApiService apiService = retrofitClient.apiService[0];
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
                return "";
        }
        try {
            ResponseBody body = call.execute().body();
            return (body != null)? body.string() : "";
        } catch (Exception e) {
            return "";
        }
    }

    private static MyRemote myRemote = null;
    private static RetrofitClient retrofitClient;

}

package com.jk.soccer.model.remote;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import com.jk.soccer.R;
import com.jk.soccer.etc.Type;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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

    public String download(Type type, Integer ID, String tab){
        RemoteTask remoteTask = new RemoteTask(retrofitClient, type, tab);
        try{
            return remoteTask.execute(ID).get().get(0);
        } catch (ExecutionException | InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    private static MyRemote myRemote = null;
    private static RetrofitClient retrofitClient;

    private static class RemoteTask extends AsyncTask<Integer, Void, List<String>> {
        final private Type type;
        final private ApiService apiService;
        final String tab;

        public RemoteTask(RetrofitClient retrofitClient, Type type, String tab) {
            this.type = type;
            apiService = retrofitClient.apiService[0];
            this.tab = tab;
        }

        @Override
        protected List<String> doInBackground(Integer... integers) {
            List<String> result = new ArrayList<>();
            for (Integer ID : integers) {
                result.add(accessRemote(ID));
            }
            return result;
        }

        private String accessRemote(Integer ID){
            Call<ResponseBody> call;
            switch (type) {
                case PLAYER:
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
                if (body != null)
                    return body.string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }
    }
}

package com.jk.soccer.model.remote;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class RetrofitClient {

    public ApiService getApiService(Integer index) {
        return apiService.get(index);
    }

    public static RetrofitClient getInstance(){
        if (retrofitClient != null)
            return retrofitClient;
        retrofitClient = new RetrofitClient();
        return retrofitClient;
    }

    private RetrofitClient(){
        apiService = new ArrayList<>();
    }

    public void register(String url){
        Retrofit retrofit;
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .build();
        client.dispatcher().setMaxRequests(100);
        client.dispatcher().setMaxRequestsPerHost(100);
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .build();
        apiService.add(retrofit.create(ApiService.class));
    }

    private static RetrofitClient retrofitClient = null;
    private static List<ApiService> apiService = null;
}

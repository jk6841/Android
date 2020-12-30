package com.jk.soccer.model.remote;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class RetrofitClient {

    private ApiService[] apiService;

    public ApiService getApiService(Integer index) {
        return apiService[index];
    }

    public RetrofitClient(String... baseUrl){
        String[] baseUrlList;
        Retrofit[] retrofit;
        int num = baseUrl.length;
        baseUrlList = new String[num];
        retrofit = new Retrofit[num];
        apiService = new ApiService[num];
        for (int i = 0; i < num; i++){
            baseUrlList[i] = baseUrl[i];
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(100, TimeUnit.SECONDS)
                    .build();
            client.dispatcher().setMaxRequests(100);
            client.dispatcher().setMaxRequestsPerHost(100);
            retrofit[i] = new Retrofit.Builder()
                    .baseUrl(baseUrlList[i])
                    .client(client)
                    .build();
            apiService[i] = retrofit[i].create(ApiService.class);
        }
    }
}

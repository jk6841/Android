package com.jk.soccer.model.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public ApiService[] apiService;
    public RetrofitClient(String... baseUrl){
        int MAX_SERVICES = 8;
        String[] baseUrlList;
        Retrofit[] retrofit;
        int num = baseUrl.length;
        if (baseUrl.length <= MAX_SERVICES) {
            baseUrlList = new String[num];
            retrofit = new Retrofit[num];
            apiService = new ApiService[num];
            for (int i = 0; i < num; i++){
                baseUrlList[i] = baseUrl[i];
                retrofit[i] = new Retrofit.Builder()
                        .baseUrl(baseUrlList[i])
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                apiService[i] = retrofit[i].create(ApiService.class);
            }
        }
    }
}

package com.jk.soccer.data.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private final int MAX_SERVICES = 8;
    private String[] baseUrl;
    private Retrofit[] retrofit;
    public ApiService[] apiService;

    public RetrofitClient(String... _baseUrl){
        int num = _baseUrl.length;
        if (num <= MAX_SERVICES) {
            baseUrl = new String[num];
            retrofit = new Retrofit[num];
            apiService = new ApiService[num];
            for (int i = 0; i < num; i++){
                baseUrl[i] = _baseUrl[i];
                retrofit[i] = new Retrofit.Builder()
                        .baseUrl(baseUrl[i])
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                apiService[i] = retrofit[i].create(ApiService.class);
            }
        }
    }
}

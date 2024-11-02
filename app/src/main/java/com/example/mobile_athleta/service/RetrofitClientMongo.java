package com.example.mobile_athleta.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientMongo {
    private static final String BASE_URL = "https://api-mongo-aktc.onrender.com";
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public static AthletaService getAthletaService() {
        return getRetrofitInstance().create(AthletaService.class);
    }
}

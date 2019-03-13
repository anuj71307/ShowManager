package com.android.showmanager.model;

import com.android.showmanager.utils.Constants;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataRequestHandler
{
    Retrofit retrofit;
    private volatile static DataRequestHandler instance;

    public DataRequestHandler()
    {

    }


    public Retrofit getRetroFitInstance()
    {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                .baseUrl(Constants.DATA_REQUEST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        }
        return retrofit;

    }
}

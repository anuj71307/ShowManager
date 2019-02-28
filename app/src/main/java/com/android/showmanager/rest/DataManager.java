package com.android.showmanager.rest;

import java.util.List;

import com.android.showmanager.pojo.ShowDetails;
import com.android.showmanager.pojo.ShowResponse;
import com.android.showmanager.utils.Constants;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataManager
{
    private static Retrofit retrofit;
    private static DataManager instance;
    private static final String TAG = DataManager.class.getSimpleName();

    private DataManager()
    {
        createRetroFitInstance();
    }

    public static final DataManager getInstance()
    {

        if (instance == null) {
            synchronized (DataManager.class) {
                if (instance == null) {
                    instance = new DataManager();
                }
            }
        }

        return instance;
    }

    private void createRetroFitInstance()
    {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                .baseUrl(Constants.DATA_REQUEST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        }
    }

    public Retrofit getRetrofit()
    {
        return retrofit;
    }
}

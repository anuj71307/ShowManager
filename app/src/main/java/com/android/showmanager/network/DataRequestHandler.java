package com.android.showmanager.network;

import com.android.showmanager.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This class should accessible through application class
 */
public class DataRequestHandler
{
    Retrofit retrofit;

    /**
     * return retrofit instance
     * @return
     */
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

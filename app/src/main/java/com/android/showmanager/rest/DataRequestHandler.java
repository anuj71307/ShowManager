package com.android.showmanager.rest;

import com.android.showmanager.utils.Constants;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataRequestHandler
{
    public static Retrofit getRetroFitInstance()
    {
            return new Retrofit.Builder()
                .baseUrl(Constants.DATA_REQUEST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    /**
     * This method take the application context and check on the internet connectivity.
     * if the device connected with the internet! the method will return true, else it will return false.
     * @param context  Activity context.
     * @return boolean true if internet connected, false if it not connected.
     * */
    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager connectivityManager =

            (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

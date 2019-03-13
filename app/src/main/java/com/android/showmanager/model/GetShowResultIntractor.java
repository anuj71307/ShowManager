package com.android.showmanager.model;

import java.util.List;

import com.android.showmanager.MyApplication;
import com.android.showmanager.contract.IShowSearchContract;
import com.android.showmanager.contract.OnFinishedListener;
import com.android.showmanager.pojo.ShowDetails;
import com.android.showmanager.pojo.ShowSearchDetails;
import com.android.showmanager.pojo.ShowSearchResponse;
import com.android.showmanager.task.BookMarkTask;
import com.android.showmanager.utils.Constants;
import com.android.showmanager.utils.Utils;

import android.content.Context;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetShowResultIntractor implements IShowSearchContract.IGetShowResultIntractor
{
    private static final String TAG = GetShowResultIntractor.class.getSimpleName();

    @Override
    public void getSearchResult(String title, int page, final OnFinishedListener onFinishedListener)
    {
        MyApplication context = MyApplication.getMyApplicationContext();
        if(!Utils.checkInternetConnection(context)){
            onFinishedListener.onInternetNotConnected();
            return;
        }
        ShowApi showApi = context.getDataHandler().getRetroFitInstance().create(ShowApi.class);
        Call<ShowSearchResponse> call = showApi.getSearchResults(title, page, Constants.API_KEY);
        call.enqueue(new Callback<ShowSearchResponse>()
        {
            @Override
            public void onResponse(Call<ShowSearchResponse> call, Response<ShowSearchResponse> response)
            {
                onFinishedListener.onFinished(response.body());
            }

            @Override
            public void onFailure(Call<ShowSearchResponse> call, Throwable t)

            {
                Log.e(TAG, t.toString());
                onFinishedListener.onFailure();
            }
        });
    }

    @Override
    public void getShowDetails(String imdbId, final OnFinishedListener onFinishedListener)
    {
        MyApplication context = MyApplication.getMyApplicationContext();
        if(!Utils.checkInternetConnection(context)){
            onFinishedListener.onInternetNotConnected();
            return;
        }
        ShowApi showApi = context.getDataHandler().getRetroFitInstance().create(ShowApi.class);
        Call<ShowDetails> call = showApi.getShowDetails(imdbId, Constants.API_KEY);
        call.enqueue(new Callback<ShowDetails>()
        {
            @Override
            public void onResponse(Call<ShowDetails> call, Response<ShowDetails> response)
            {
                onFinishedListener.onFinished(response.body());
            }

            @Override
            public void onFailure(Call<ShowDetails> call, Throwable t)
            {
                onFinishedListener.onFailure();

            }
        });

    }

    @Override
    public void loadBookMarkData(OnFinishedListener onFinishedListener)
    {
        new BookMarkTask(onFinishedListener).execute();
    }

}

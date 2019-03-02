package com.android.showmanager.rest;

import com.android.showmanager.contract.IShowSearchContract;
import com.android.showmanager.contract.OnFinishedListener;
import com.android.showmanager.pojo.ShowDetails;
import com.android.showmanager.pojo.ShowSearchResponse;
import com.android.showmanager.utils.Constants;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetShowResultIntractor implements IShowSearchContract.IGetShowResultIntractor
{
    private static final String TAG = GetShowResultIntractor.class.getSimpleName();

    @Override
    public void getSearchResult(String title, final OnFinishedListener onFinishedListener)
    {
        //todo add internet check
        ShowApi showApi = DataRequestHandler.getRetroFitInstance().create(ShowApi.class);
        Call<ShowSearchResponse> call = showApi.getSearchResults(title, 1, Constants.API_KEY);
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
        //todo add internet check
        ShowApi showApi = DataRequestHandler.getRetroFitInstance().create(ShowApi.class);
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


}

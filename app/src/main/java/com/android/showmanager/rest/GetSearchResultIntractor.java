package com.android.showmanager.rest;

import java.util.List;

import com.android.showmanager.contract.IMovieListContract;
import com.android.showmanager.pojo.ShowDetails;
import com.android.showmanager.pojo.ShowResponse;
import com.android.showmanager.utils.Constants;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetSearchResultIntractor implements IMovieListContract.IGetSearchResultIntractor
{
    private static final String TAG = GetSearchResultIntractor.class.getSimpleName();

    @Override
    public void getSearchResult(String title, final OnFinishedListener onFinishedListener)
    {
        ShowApi showApi = DataManager.getInstance().getRetrofit().create(ShowApi.class);
        Call<ShowResponse> call = showApi.getSearchResults(title, 1, Constants.API_KEY);
        call.enqueue(new Callback<ShowResponse>()
        {
            @Override
            public void onResponse(Call<ShowResponse> call, Response<ShowResponse> response)
            {
                List<ShowDetails> list = response.body().getShowDetailsList();
                onFinishedListener.onFinished(list);
            }

            @Override
            public void onFailure(Call<ShowResponse> call, Throwable t)

            {
                Log.e(TAG, t.toString());
                onFinishedListener.onFailure();
            }
        });
    }
}

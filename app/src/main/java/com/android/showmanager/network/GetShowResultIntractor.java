package com.android.showmanager.network;

import com.android.showmanager.MyApplication;
import com.android.showmanager.view.list.IShowSearchContract;
import com.android.showmanager.view.OnFinishedListener;
import com.android.showmanager.pojo.ShowDetails;
import com.android.showmanager.pojo.ShowSearchResponse;
import com.android.showmanager.task.BookMarkTask;
import com.android.showmanager.utils.Constants;
import com.android.showmanager.utils.Utils;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetShowResultIntractor implements IShowSearchContract.IGetShowResultIntractor
{
    private static final String TAG = GetShowResultIntractor.class.getSimpleName();

    /**
     * Get show search result
     * @param key key to search
     * @param page page to get in result // by default 10 results are received in one call
     * @param onFinishedListener listener to whom result to be sent
     */
    @Override
    public void getSearchResult(String key, int page, final OnFinishedListener onFinishedListener)
    {
        MyApplication context = MyApplication.getMyApplicationContext();
        if(!Utils.checkInternetConnection(context)){
            onFinishedListener.onInternetNotConnected();
            return;
        }
        ShowApi showApi = context.getDataHandler().getRetroFitInstance().create(ShowApi.class);
        Call<ShowSearchResponse> call = showApi.getSearchResults(key, page, Constants.API_KEY);
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

    /**
     * Get details of a show
     * @param imdbId id of show for which details needs to be found
     * @param onFinishedListener listener to whom result to be sent
     */
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

package com.android.showmanager.repo;

import com.android.showmanager.api.ShowApiService;
import com.android.showmanager.model.SearchResponse;
import com.android.showmanager.model.ShowSearchDetails;

import android.util.Log;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class Acts as data source
 */
public class SearchDataSource extends androidx.paging.PageKeyedDataSource<Integer, ShowSearchDetails>
{
    private static final String TAG = SearchDataSource.class.getSimpleName();
    //we will start from the first page which is 1
    private static final int FIRST_PAGE = 1;

    private String mSearchKey;
    private ShowApiService mService;
    private String mApiKey;

    public SearchDataSource(String searchKey, ShowApiService service, String apiKey)
    {
        this.mSearchKey = searchKey;
        this.mService = service;
        this.mApiKey = apiKey;
    }


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params,
        @NonNull final LoadInitialCallback<Integer, ShowSearchDetails> callback)
    {
        Log.i(TAG, "Load initial");

        final int page = FIRST_PAGE;
        Call<SearchResponse> call = mService.getApi().getSearchResults(mSearchKey, page, mApiKey);
        call.enqueue(new Callback<SearchResponse>()
        {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response)
            {
                Log.i(TAG, "Initial load completed");
                if(response == null || response.body()==null) return;
                callback.onResult(response.body().getShowDetailsList(), null, page + 1);
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t)

            {
                Log.e(TAG, t.toString());
            }
        });


    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params,
        @NonNull LoadCallback<Integer, ShowSearchDetails> callback)
    {
        Log.i(TAG, "Load before");

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params,
        @NonNull final LoadCallback<Integer, ShowSearchDetails> callback)
    {
        Log.i(TAG, "Load After");
        final int page = params.key;
        Call<SearchResponse> call = mService.getApi().getSearchResults(mSearchKey, page, mApiKey);
        call.enqueue(new Callback<SearchResponse>()
        {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response)
            {
                Log.i(TAG, "After load completed");
                if(response == null || response.body()==null) return;
                callback.onResult(response.body().getShowDetailsList(), page + 1);
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t)

            {
                Log.e(TAG, t.toString());

            }
        });

    }
}

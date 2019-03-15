package com.android.showmanager.network;

import com.android.showmanager.pojo.ShowDetails;
import com.android.showmanager.pojo.ShowSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ShowApi
{
    @GET("?")
    Call<ShowSearchResponse> getSearchResults(@Query("s") String title, @Query("page") int pages, @Query("apikey") String apikey);
    @GET("?")
    Call<ShowDetails> getShowDetails(@Query("i") String imdbId, @Query("apikey") String apiKey);
}

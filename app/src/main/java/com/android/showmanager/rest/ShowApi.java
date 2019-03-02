package com.android.showmanager.rest;

import com.android.showmanager.pojo.ShowSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ShowApi
{
    @GET("?")
    Call<ShowSearchResponse> getSearchResults(@Query("s") String title, @Query("page") int pages, @Query("apikey") String apikey);
}

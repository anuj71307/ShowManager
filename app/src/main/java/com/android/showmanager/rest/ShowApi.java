package com.android.showmanager.rest;

import com.android.showmanager.pojo.ShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ShowApi
{
    @GET("?")
    Call<ShowResponse> getSearchResults(@Query("s") String title, @Query("page") int pages, @Query("apikey") String apikey);
}

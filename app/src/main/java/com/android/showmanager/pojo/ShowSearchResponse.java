package com.android.showmanager.pojo;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ShowSearchResponse
{
    @SerializedName("Search")
    List<ShowSearchDetails> showDetailsList;
    @SerializedName("totalResults")
    String totalResult;

    public ShowSearchResponse(List<ShowSearchDetails> showDetailsList, String totalResult)
    {
        this.showDetailsList = showDetailsList;
        this.totalResult = totalResult;
    }

    public List<ShowSearchDetails> getShowDetailsList()
    {
        return showDetailsList;
    }

    public void setShowDetailsList(List<ShowSearchDetails> showDetailsList)
    {
        this.showDetailsList = showDetailsList;
    }

    public String getTotalResult()
    {
        return totalResult;
    }

    public void setTotalResult(String totalResult)
    {
        this.totalResult = totalResult;
    }
}

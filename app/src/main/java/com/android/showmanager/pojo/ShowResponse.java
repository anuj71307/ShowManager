package com.android.showmanager.pojo;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ShowResponse
{
    @SerializedName("Search")
    List<ShowDetails> showDetailsList;
    @SerializedName("totalResults")
    String totalResult;

    public ShowResponse(List<ShowDetails> showDetailsList, String totalResult)
    {
        this.showDetailsList = showDetailsList;
        this.totalResult = totalResult;
    }

    public List<ShowDetails> getShowDetailsList()
    {
        return showDetailsList;
    }

    public void setShowDetailsList(List<ShowDetails> showDetailsList)
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

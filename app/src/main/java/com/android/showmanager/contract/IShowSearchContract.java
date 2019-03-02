package com.android.showmanager.contract;

import java.util.List;

import com.android.showmanager.pojo.ShowSearchDetails;

public interface IShowSearchContract
{
    interface ShowSearchPresenter
    {
        void searchByTitle(String title);
        void onDestroy();
        void saveBookMark(ShowSearchDetails showDetails);
    }

    interface IShowSearchView
    {
       void showProgress();
       void hideProgress();
       void loadSearchResult(List<ShowSearchDetails> showDetailsList);
       void showEmptyErrorTitle();
       void showResponseFailure();
    }

    /**
     * Intractors are classes built for fetching data from omdb
     **/
    interface IGetShowResultIntractor
    {

        void getSearchResult(String title, OnFinishedListener onFinishedListener);
        void getShowDetails(String imdbId, OnFinishedListener onFinishedListener);
    }
}

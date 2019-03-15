package com.android.showmanager.view.list;

import java.util.List;

import com.android.showmanager.view.OnFinishedListener;
import com.android.showmanager.pojo.ShowSearchDetails;

public interface IShowSearchContract
{
    interface ShowSearchPresenter
    {
        void searchByTitle(String title, int page);
        void onDestroy();
        void saveBookMark(ShowSearchDetails showDetails);
        void loadBookMark();
        void deleteBookMark(ShowSearchDetails showDetails);
    }

    interface IShowSearchView
    {
       void showProgress();
       void hideProgress();
       void loadSearchResult(List<ShowSearchDetails> showDetailsList);
       void showEmptyErrorTitle();
       void showResponseFailure();
       void showToastMessage(String message);
       void onBookMarkLoaded(List<ShowSearchDetails> showSearchDetailsList);
    }

    /**
     * Intractors are classes built for fetching data from omdb
     **/
    interface IGetShowResultIntractor
    {

        void getSearchResult(String title, int page, OnFinishedListener onFinishedListener);
        void getShowDetails(String imdbId, OnFinishedListener onFinishedListener);
        void loadBookMarkData(OnFinishedListener onFinishedListener);
    }
}

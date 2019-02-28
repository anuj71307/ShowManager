package com.android.showmanager.contract;

import java.util.List;

import com.android.showmanager.pojo.ShowDetails;

public interface IMovieListContract
{
    interface IMovieListPresenter {

        void searchByTitle(String title);
        void onDestroy();
    }

    interface IMovieListView {

       void showProgress();
       void hideProgress();
       void loadSearchResult(List<ShowDetails> showDetailsList);
       void showEmptyErrorTitle();
       void showResponseFailure();
    }

    /**
     * Intractors are classes built for fetching data from omdb
     **/
    interface IGetSearchResultIntractor
    {

        interface OnFinishedListener
        {
            void onFinished(List<ShowDetails> showList);

            void onFailure();
        }

        void getSearchResult(String title, OnFinishedListener onFinishedListener);
    }
}

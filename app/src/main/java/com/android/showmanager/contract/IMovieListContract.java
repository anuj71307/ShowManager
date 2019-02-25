package com.android.showmanager.contract;

import java.util.List;

import com.android.showmanager.pojo.ShowDetails;

public interface IMovieListContract
{
    interface IMovieListPresenter {

        void searchByTitle(String title);
    }

    interface IMovieListView {

       void showProgress();
       void hideProgress();
       void loadSearchResult(List<ShowDetails> showDetailsList);
       void showEmptyErrorTitle();
    }
}

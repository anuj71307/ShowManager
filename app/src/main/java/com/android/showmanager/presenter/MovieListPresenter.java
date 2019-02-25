package com.android.showmanager.presenter;

import com.android.showmanager.contract.IMovieListContract;
import com.android.showmanager.rest.DataManager;

import android.text.TextUtils;

public class MovieListPresenter implements IMovieListContract.IMovieListPresenter
{
    private IMovieListContract.IMovieListView view;

    public MovieListPresenter(IMovieListContract.IMovieListView view)
    {
        this.view = view;
    }

    @Override
    public void searchByTitle(String title)
    {

     if(TextUtils.isEmpty(title)){
         view.showEmptyErrorTitle();
     }
     else{
         DataManager manager = DataManager.getInstance();
         manager.getSearchResult(title);
     }
    }
}

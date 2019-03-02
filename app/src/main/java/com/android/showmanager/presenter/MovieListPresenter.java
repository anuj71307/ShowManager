package com.android.showmanager.presenter;

import com.android.showmanager.contract.IMovieListContract;
import com.android.showmanager.contract.OnFinishedListener;
import com.android.showmanager.pojo.ShowSearchResponse;

import android.text.TextUtils;

/**
 * Presenter class responsible to interact with view
 */
public class MovieListPresenter<T> implements IMovieListContract.IMovieListPresenter, OnFinishedListener<T>
{
    private IMovieListContract.IMovieListView view;
    private IMovieListContract.IGetSearchResultIntractor iGetSearchResultIntractor;

    public MovieListPresenter(IMovieListContract.IMovieListView view,
        IMovieListContract.IGetSearchResultIntractor iGetSearchResultIntractor)
    {
        this.view = view;
        this.iGetSearchResultIntractor = iGetSearchResultIntractor;
    }

    @Override
    public void searchByTitle(String title)
    {

        if (view == null) {
            return;
        }
        if (TextUtils.isEmpty(title)) {
            view.showEmptyErrorTitle();
        }
        else {
            view.showProgress();
            iGetSearchResultIntractor.getSearchResult(title, this);
        }
    }

    /**
     * activity on destroy called
     */
    @Override
    public void onDestroy()
    {
        view = null;
    }

    @Override
    public void onFinished(T object)
    {
        if(view==null || !(object instanceof ShowSearchResponse)) return;;
        view.hideProgress();
        ShowSearchResponse response = (ShowSearchResponse) object;
        view.loadSearchResult(response.getShowDetailsList());

    }

    /**
     * call to get data has failed
     */
    @Override
    public void onFailure()
    {
        if (view == null) {
            return;
        }
        view.showResponseFailure();
        view.hideProgress();
    }

    @Override
    public void onInternetNotConnected()
    {

    }
}

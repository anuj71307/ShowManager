package com.android.showmanager.presenter;

import java.util.List;

import com.android.showmanager.contract.IMovieListContract;
import com.android.showmanager.pojo.ShowDetails;

import android.text.TextUtils;

/**
 * Presenter class responsible to interact with view
 */
public class MovieListPresenter implements IMovieListContract.IMovieListPresenter,
    IMovieListContract.IGetSearchResultIntractor.OnFinishedListener
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

    /**
     * network call executed successfully
     * @param showList
     */
    @Override
    public void onFinished(List<ShowDetails> showList)
    {
        if (view == null) {
            return;
        }
        view.loadSearchResult(showList);
        view.hideProgress();
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
}

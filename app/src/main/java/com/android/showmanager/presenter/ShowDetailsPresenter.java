package com.android.showmanager.presenter;

import com.android.showmanager.contract.IShowDetailsContract;
import com.android.showmanager.contract.IShowSearchContract;
import com.android.showmanager.contract.OnFinishedListener;
import com.android.showmanager.pojo.ShowDetails;

public class ShowDetailsPresenter<T> implements IShowDetailsContract.IShowDetailsPresenter, OnFinishedListener<T>
{
    IShowDetailsContract.IShowDetailsView view;
    IShowSearchContract.IGetShowResultIntractor showResultIntractor;

    public ShowDetailsPresenter(IShowDetailsContract.IShowDetailsView view,
        IShowSearchContract.IGetShowResultIntractor showResultIntractor)
    {
        this.view = view;
        this.showResultIntractor = showResultIntractor;
    }

    @Override
    public void loadShowDetails(String imdbId)
    {
        if (view == null) {
            return;
        }
        view.showProgress();
        showResultIntractor.getShowDetails(imdbId, this);
    }

    @Override
    public void onDestroy()
    {
        view = null;
    }

    @Override
    public void onFinished(T object)
    {
        if (view == null || !(object instanceof ShowDetails)) {
            return;
        }
        view.hideProgress();
        ShowDetails showDetails = (ShowDetails) object;
        view.loadSearchResult(showDetails);
    }

    @Override
    public void onFailure()
    {
      if(view==null) return;
      view.showResponseFailure();
    }

    @Override
    public void onInternetNotConnected()
    {

    }
}

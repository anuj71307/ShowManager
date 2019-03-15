package com.android.showmanager.view.detail;

import java.util.List;

import com.android.showmanager.view.list.IShowSearchContract;
import com.android.showmanager.view.OnFinishedListener;
import com.android.showmanager.network.GetShowResultIntractor;
import com.android.showmanager.pojo.ShowDetails;
import com.android.showmanager.pojo.ShowSearchDetails;

public class ShowDetailsPresenter<T> implements IShowDetailsContract.IShowDetailsPresenter, OnFinishedListener<T>
{
    IShowDetailsContract.IShowDetailsView view;
    IShowSearchContract.IGetShowResultIntractor showResultIntractor;

    public ShowDetailsPresenter(IShowDetailsContract.IShowDetailsView view)
    {
        this.view = view;
        this.showResultIntractor = new GetShowResultIntractor();
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
        if (view == null) {
            return;
        }
        view.showResponseFailure();
    }

    @Override
    public void onInternetNotConnected()
    {
        if (view != null) {
            view.hideProgress();
            view.showToastMessage("Please check your connectivity");
        }
    }

    @Override
    public void onBookMarkLoaded(List<ShowSearchDetails> showSearchDetailsList)
    {
        //DO Nothing
    }
}

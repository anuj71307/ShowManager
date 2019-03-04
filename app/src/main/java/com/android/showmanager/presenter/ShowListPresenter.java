package com.android.showmanager.presenter;

import com.android.showmanager.MyApplication;
import com.android.showmanager.contract.IShowSearchContract;
import com.android.showmanager.contract.OnFinishedListener;
import com.android.showmanager.pojo.ShowSearchDetails;
import com.android.showmanager.pojo.ShowSearchResponse;
import com.android.showmanager.dao.BookmarkRepository;

import android.text.TextUtils;

/**
 * Presenter class responsible to interact with view
 */
public class ShowListPresenter<T> implements IShowSearchContract.ShowSearchPresenter, OnFinishedListener<T>
{
    private IShowSearchContract.IShowSearchView view;
    private IShowSearchContract.IGetShowResultIntractor iGetShowResultIntractor;

    public ShowListPresenter(IShowSearchContract.IShowSearchView view,
        IShowSearchContract.IGetShowResultIntractor iGetShowResultIntractor)
    {
        this.view = view;
        this.iGetShowResultIntractor = iGetShowResultIntractor;
    }

    @Override
    public void searchByTitle(String title, int page)
    {

        if (view == null) {
            return;
        }
        if (TextUtils.isEmpty(title)) {
            view.showEmptyErrorTitle();
        }
        else {
            view.showProgress();
            iGetShowResultIntractor.getSearchResult(title, page, this);
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
    public void saveBookMark(ShowSearchDetails showDetails)
    {
        //save bookmark in db
        BookmarkRepository repository = MyApplication.getMyApplicationContext().getBookMarkRepository();
        repository.insertBookMark(showDetails);

    }

    @Override
    public void loadBookMark()
    {
      //TODO Load Bookmark
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

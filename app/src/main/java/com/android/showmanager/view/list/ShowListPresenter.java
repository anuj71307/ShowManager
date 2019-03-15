package com.android.showmanager.view.list;

import java.util.List;

import com.android.showmanager.MyApplication;
import com.android.showmanager.view.OnFinishedListener;
import com.android.showmanager.network.GetShowResultIntractor;
import com.android.showmanager.pojo.ShowSearchDetails;
import com.android.showmanager.pojo.ShowSearchResponse;
import com.android.showmanager.dao.BookmarkRepository;

import android.text.TextUtils;

/**
 * Presenter class responsible to interact with list view of results
 */
public class ShowListPresenter<T> implements IShowSearchContract.ShowSearchPresenter, OnFinishedListener<T>
{
    private IShowSearchContract.IShowSearchView view;
    private IShowSearchContract.IGetShowResultIntractor iGetShowResultIntractor;

    public ShowListPresenter(IShowSearchContract.IShowSearchView view)
    {
        this.view = view;
        this.iGetShowResultIntractor = new GetShowResultIntractor();
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
        boolean result = repository.insertBookMark(showDetails);
        if (view != null) {
            if (result) {
                view.showToastMessage("Bookmark saved");
                loadBookMark();
            }
            else {
                view.showToastMessage("Bookmark not saved");
            }
        }

    }

    @Override
    public void loadBookMark()
    {
        if (view == null) {
            return;
        }
        view.showToastMessage("Loading Bookmark..");
        iGetShowResultIntractor.loadBookMarkData(this);
    }

    @Override
    public void deleteBookMark(ShowSearchDetails showDetails)
    {
        BookmarkRepository repository = MyApplication.getMyApplicationContext().getBookMarkRepository();
        boolean result = repository.deleteBookMark(showDetails);
        if (view != null) {
            if (result) {
                view.showToastMessage("Bookmark deleted");
                loadBookMark();
            }
            else {
                view.showToastMessage("Bookmark not deleted");
            }
        }

    }

    @Override
    public void onFinished(T object)
    {
        if (view == null || !(object instanceof ShowSearchResponse)) {
            return;
        }

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
       if(view!=null){
           view.hideProgress();
           view.showToastMessage("Please check your connectivity");
       }
    }

    @Override
    public void onBookMarkLoaded(List<ShowSearchDetails> showSearchDetailsList)
    {
        if (view == null) {
            return;
        }
        view.onBookMarkLoaded(showSearchDetailsList);
    }
}

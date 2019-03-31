package com.android.showmanager.view.list;

import java.util.List;
import java.util.concurrent.Executor;

import com.android.showmanager.api.ShowApiService;
import com.android.showmanager.model.SearchResponse;
import com.android.showmanager.repo.SearchDataSource;
import com.android.showmanager.repo.ShowRepository;
import com.android.showmanager.model.ShowSearchDetails;
import com.android.showmanager.utils.Constants;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

public class ShowViewModel extends AndroidViewModel
{
    private ShowRepository mShowRepository;
    private LiveData<List<ShowSearchDetails>> mBookmarkList;

    public ShowViewModel(@NonNull Application application)
    {
        super(application);
        mShowRepository = ShowRepository.getInstance(application);
        mBookmarkList = mShowRepository.getAllBookMark();
    }

    public LiveData<List<ShowSearchDetails>> getBookmarkList()
    {
        return mBookmarkList;
    }

    public void insert(ShowSearchDetails showSearchDetails)
    {
        mShowRepository.insertBookMark(showSearchDetails);
    }

    public void delete(ShowSearchDetails showSearchDetails)
    {
        mShowRepository.deleteBookMark(showSearchDetails);
    }

    public PagedList<ShowSearchDetails> searchShow(String mSearchKey, Executor executor)
    {
        SearchDataSource searchDataSource =
            new SearchDataSource(mSearchKey, ShowApiService.getInstance(), Constants.API_KEY);
        PagedList.Config config = new PagedList.Config.Builder()
            // Number of items to fetch at once. [Required]
            .setPageSize(2)
            // Number of items to fetch on initial load. Should be greater than Page size. [Optional]
            .setEnablePlaceholders(true) // Show empty views until data is available
            .build();

        // Build PagedList
        PagedList<ShowSearchDetails> pagedList =
            new PagedList.Builder<>(searchDataSource, config) // Can pass `pageSize` directly instead of `config`
                // Do fetch operations on the main thread. We'll instead be using Retrofit's
                // built-in enqueue() method for background api calls.
                .setFetchExecutor(executor)
                // Send updates on the main thread
                .setNotifyExecutor(executor)
                .build();

        return pagedList;
    }
}

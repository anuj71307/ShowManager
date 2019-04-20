package com.android.showmanager.view.list;


import java.util.List;
import java.util.concurrent.Executor;

import com.android.showmanager.R;
import com.android.showmanager.adapter.BookMarkAdapter;
import com.android.showmanager.adapter.IShowClickListner;
import com.android.showmanager.adapter.ShowListAdapter;
import com.android.showmanager.model.ShowSearchDetails;
import com.android.showmanager.utils.Constants;
import com.android.showmanager.utils.ThreadExecutor;
import com.android.showmanager.view.detail.ShowDetailsActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ShowListActivity extends AppCompatActivity
{

    private static final String TAG = ShowListActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RecyclerView mBookmarkView;
    private BookMarkAdapter mBookMarkAdapter;
    private ShowListAdapter mAdapter;
    private LinearLayout mBookMarkLinearLayout;

    String mSearchKey = Constants.DEFAULT_SEARCH;
    private ShowViewModel mShowViewModel;
    private Executor mExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        mExecutor = new ThreadExecutor();
        initBookMarkView();
        initResultRecylerView();
        mShowViewModel = ViewModelProviders.of(this).get(ShowViewModel.class);
        registerBookMarkObserver();
        observe();
        loadDefaultSearch();
    }

    private void observe()
    {
        mShowViewModel.getmShowSearchLiveData().observe(this, new Observer<PagedList<ShowSearchDetails>>()
        {
            @Override
            public void onChanged(PagedList<ShowSearchDetails> showSearchDetails)
            {
                Log.i(TAG, "On Changed  list size is " + (showSearchDetails!=null?showSearchDetails.size():0));
                mAdapter.submitList(showSearchDetails);
            }
        });
    }

    private void registerBookMarkObserver()
    {
        mShowViewModel.getBookmarkList().observe(this, new Observer<List<ShowSearchDetails>>()
        {
            @Override
            public void onChanged(List<ShowSearchDetails> showSearchDetailsList)
            {
                if (showSearchDetailsList == null || showSearchDetailsList.isEmpty()) {
                    mBookMarkLinearLayout.setVisibility(View.GONE);
                    return;
                }
                mBookMarkLinearLayout.setVisibility(View.VISIBLE);
                mBookMarkAdapter.setShowList(showSearchDetailsList);
                mBookMarkAdapter.notifyDataSetChanged();
            }
        });
    }


    private void loadDefaultSearch()
    {
        refreshData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

            SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();

            search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

            search.setOnQueryTextListener(new SearchView.OnQueryTextListener()
            {

                @Override
                public boolean onQueryTextSubmit(String query)
                {
                    Log.i(TAG, "Text Submitted " + query);
                    mSearchKey = query;
                    refreshData();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String query)
                {

                    // DO Nothing
                    return true;

                }

            });

        }

        return true;

    }

    private void initBookMarkView()
    {
        mBookmarkView = findViewById(R.id.bookmarkRecylerView);
        mBookmarkView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
            false);
        mBookmarkView.setLayoutManager(layoutManager);
        mBookMarkAdapter = new BookMarkAdapter(this, showClickListener);
        mBookmarkView.setAdapter(mBookMarkAdapter);
        mBookmarkView.setItemAnimator(new DefaultItemAnimator());
        mBookMarkLinearLayout = findViewById(R.id.bookmarkLayout);
    }

    private void initResultRecylerView()
    {
        mRecyclerView = findViewById(R.id.showListRecylerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new ShowListAdapter(this, showClickListener);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    /**
     * RecyclerItem click event listener
     */
    private IShowClickListner showClickListener = new IShowClickListner()
    {
        @Override
        public void onShowClick(ShowSearchDetails showSearchDetails)
        {

            Log.i(TAG, "Show clicked " + showSearchDetails.getTitle());
            startDetailActivity(showSearchDetails.getImdbID());

        }

        @Override
        public void onSaveBookMark(ShowSearchDetails showDetails)
        {
            Log.i(TAG, "Save book Mark for " + showDetails.getTitle());
            mShowViewModel.insert(showDetails);
        }

        public void onDeleteBookMark(ShowSearchDetails showDetails)
        {
            mShowViewModel.delete(showDetails);
        }
    };

    /**
     * Start Detail Activity
     *
     * @param imdbID
     */
    private void startDetailActivity(String imdbID)
    {
        Intent intent = new Intent(this, ShowDetailsActivity.class);
        intent.putExtra(Constants.IMDB_ID, imdbID);
        startActivity(intent);
    }

    public void refreshData()
    {
        Log.i(TAG, "Search key is "+mSearchKey);
        mShowViewModel.searchShow(mSearchKey, mExecutor);

    }


}

package com.android.showmanager.view;


import java.util.List;

import com.android.showmanager.R;
import com.android.showmanager.adapter.BookMarkAdapter;
import com.android.showmanager.adapter.IShowClickListner;
import com.android.showmanager.adapter.ShowListAdapter;
import com.android.showmanager.contract.IShowSearchContract;
import com.android.showmanager.pojo.ShowSearchDetails;
import com.android.showmanager.presenter.ShowListPresenter;
import com.android.showmanager.model.GetShowResultIntractor;
import com.android.showmanager.utils.Constants;
import com.android.showmanager.utils.PaginationScrollListener;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ShowListActivity extends AppCompatActivity implements IShowSearchContract.IShowSearchView
{

    private static final String TAG = ShowListActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RecyclerView mBookmarkView;
    private BookMarkAdapter mBookMarkAdapter;
    private ShowListAdapter mAdapter;
    private ProgressBar progressBar;
    private Button button;
    private IShowSearchContract.ShowSearchPresenter presenter;
    private EditText searchParam;
    private LinearLayout mBookMarkLinearLayout;

    //TODO check these value on rotation
    private boolean isLoading = false;
    private boolean isLastPage = false;
    //TODO Change this based on actual result count received in response
    // limiting to 5 for this show, since total pages in actual API is very large
    private int TOTAL_PAGES = 5;
    private int currentPage = Constants.PAGE_START;
    String searchKey;
    static boolean searchKeyChanged;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        presenter = new ShowListPresenter(this, new GetShowResultIntractor());
        initView();
        initBookMarkView();
        initResultRecylerView();
        initProgressBar();
    }




    private void initView()
    {
        searchParam = findViewById(R.id.searchParam);
        button = findViewById(R.id.searchButton);
        searchParam = findViewById(R.id.searchParam);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                searchKey = searchParam.getText().toString();
                searchKeyChanged = true;
                currentPage = Constants.PAGE_START;
                searchByTitle(searchKey, currentPage);

            }
        });
    }


    private void searchByTitle(String title, int pages)
    {
        //TODO Anuj add loading ui in adapter and remove toast
        // mAdapter.addLoadingFooter();
        Toast.makeText(this, "Loading data....", Toast.LENGTH_SHORT).show();
        presenter.searchByTitle(title, pages);
    }

    private void initBookMarkView()
    {
        mBookmarkView = findViewById(R.id.bookmarkRecylerView);
        mBookmarkView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
            false);
        mBookmarkView.setLayoutManager(layoutManager);
        mBookMarkAdapter = new BookMarkAdapter(this, recyclerItemClickListener);
        mBookmarkView.setAdapter(mBookMarkAdapter);
        mBookmarkView.setItemAnimator(new DefaultItemAnimator());
        mBookMarkLinearLayout = findViewById(R.id.bookmarkLayout);
        presenter.loadBookMark();
        //TODO Move to presenter
    }
    private void initResultRecylerView()
    {
        mRecyclerView = findViewById(R.id.showListRecylerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new ShowListAdapter(this, recyclerItemClickListener);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.addOnScrollListener(new PaginationScrollListener(layoutManager)
        {
            @Override
            protected void loadMoreItems()
            {
                isLoading = true;
                currentPage += 1;

                Log.i(TAG, "isLoading? " + isLoading + " currentPage " + currentPage);
                searchByTitle(searchKey, currentPage);
            }

            @Override
            public int getTotalPageCount()
            {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage()
            {
                return isLastPage;
            }

            @Override
            public boolean isLoading()
            {
                return isLoading;
            }
        });
    }

    //showing progress bar programatically
    private void initProgressBar()
    {
        //TODO Anuj Show progress bar
        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);

        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setGravity(Gravity.CENTER);
        relativeLayout.addView(progressBar);

        RelativeLayout.LayoutParams params = new
            RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT);
        progressBar.setVisibility(View.INVISIBLE);
        addContentView(relativeLayout, params);
    }

    @Override
    public void showProgress()
    {
        Log.i(TAG, "Showing progress");
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress()
    {
        Log.i(TAG, "hiding progress");
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void loadSearchResult(List<ShowSearchDetails> showDetailsList)
    {
        Log.i(TAG, "load search result, searchKeyChanged ? " + searchKeyChanged);
        isLoading = false;
        if (searchKeyChanged) {
            mAdapter.clearList();
            searchKeyChanged = false;
        }

        int size = mAdapter.getShowDetailsList().size();
        Log.i(TAG, "CurrentList size?  " + size + " Fetched Data size " + (showDetailsList == null ? 0 :
            showDetailsList.size()));
        mAdapter.setShowDetailsList(showDetailsList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyErrorTitle()
    {
        Log.i(TAG, "load search result");
        showToastMessage("Empty title");
    }

    @Override
    public void showResponseFailure()
    {
        Log.i(TAG, "load search result");
        showToastMessage("Response error");
    }

    @Override
    public void showToastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy()
    {
        presenter.onDestroy();
        super.onDestroy();
    }

    /**
     * RecyclerItem click event listener
     */
    private IShowClickListner recyclerItemClickListener = new IShowClickListner()
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
             presenter.saveBookMark(showDetails);
        }
    };

    /**
     * Start Detai Activity
     * @param imdbID
     */
    private void startDetailActivity(String imdbID)
    {
        Intent intent = new Intent(this, ShowDetailsActivity.class);
        intent.putExtra(Constants.IMDB_ID, imdbID);
        startActivity(intent);
    }


    @Override
    public void onBookMarkLoaded(List<ShowSearchDetails> result)
    {
        if(result==null || result.isEmpty()){
            Log.i(TAG, "No boookmark in db");
            return;
        }
        mBookMarkLinearLayout.setVisibility(View.VISIBLE);
        mBookMarkAdapter.setShowList(result);
        mBookMarkAdapter.notifyDataSetChanged();

    }



}

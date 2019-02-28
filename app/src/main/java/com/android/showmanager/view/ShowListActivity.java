package com.android.showmanager.view;

import java.util.List;

import com.android.showmanager.R;
import com.android.showmanager.adapter.ItemClickListner;
import com.android.showmanager.adapter.ShowListAdapter;
import com.android.showmanager.pojo.ShowDetails;
import com.android.showmanager.contract.IMovieListContract;
import com.android.showmanager.presenter.MovieListPresenter;
import com.android.showmanager.rest.GetSearchResultIntractor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ShowListActivity extends AppCompatActivity implements IMovieListContract.IMovieListView
{

    private static final String TAG = ShowListActivity.class.getSimpleName();
    private Button button;
    private IMovieListContract.IMovieListPresenter presenter;
    private EditText searchParam;
    private RecyclerView mRecyclerView;
    private ShowListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MovieListPresenter(this, new GetSearchResultIntractor());
        initView();
        initRecylerView();
    }

    private void initRecylerView()
    {
        mRecyclerView = findViewById(R.id.showListRecylerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ShowListAdapter(this,null, recyclerItemClickListener);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initView()
    {
        initProgressBar();
        searchParam = findViewById(R.id.searchParam);
        button = findViewById(R.id.searchButton);
        searchParam = findViewById(R.id.searchParam);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String text = searchParam.getText().toString();
                presenter.searchByTitle(text);

            }
        });
    }

    //showing progress bar programatically
    private void initProgressBar()
    {
        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);

        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setGravity(Gravity.CENTER);
        relativeLayout.addView(progressBar);

        RelativeLayout.LayoutParams params = new
            RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT);
        progressBar.setVisibility(View.INVISIBLE);

        this.addContentView(relativeLayout, params);
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
    public void loadSearchResult(List<ShowDetails> showDetailsList)
    {
        Log.i(TAG, "load search result");
        mAdapter.setShowDetailsList(showDetailsList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyErrorTitle()
    {
        Log.i(TAG, "show error empty title");
        Toast.makeText(this, "empty title", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showResponseFailure()
    {
        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();

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
    private ItemClickListner recyclerItemClickListener = new ItemClickListner()
    {
        @Override
        public void onItemClick(ShowDetails showDetails)
        {

            Log.i(TAG, "Show clicked " + showDetails.getTitle());

        }
    };

}

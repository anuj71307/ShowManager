package com.android.showmanager.ui;

import java.util.List;

import com.android.showmanager.R;
import com.android.showmanager.pojo.ShowDetails;
import com.android.showmanager.contract.IMovieListContract;
import com.android.showmanager.presenter.MovieListPresenter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ShowListActivity extends AppCompatActivity implements IMovieListContract.IMovieListView
{

    private static final String TAG = ShowListActivity.class.getSimpleName();
    private Button button;
    private IMovieListContract.IMovieListPresenter presenter;
    private EditText searchParam;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MovieListPresenter(this);
        initView();
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
               String text = searchParam.getText().toString();
               presenter.searchByTitle(text);

            }
        });
    }

    @Override
    public void showProgress()
    {
        Log.i(TAG, "Showing progress");
    }

    @Override
    public void hideProgress()
    {
        Log.i(TAG, "hiding progress");
    }

    @Override
    public void loadSearchResult(List<ShowDetails> showDetailsList)
    {
        Log.i(TAG, "load search result");
    }

    @Override
    public void showEmptyErrorTitle()
    {
        Log.i(TAG, "show error empty title");
    }
}

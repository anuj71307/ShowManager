package com.android.showmanager.view;

import java.util.List;

import com.android.showmanager.R;
import com.android.showmanager.adapter.ItemClickListner;
import com.android.showmanager.adapter.ShowListAdapter;
import com.android.showmanager.contract.IShowSearchContract;
import com.android.showmanager.pojo.ShowSearchDetails;
import com.android.showmanager.presenter.ShowListPresenter;
import com.android.showmanager.rest.GetShowResultIntractor;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ShowListFragment extends Fragment implements IShowSearchContract.IShowSearchView
{
    private static final String TAG = ShowListFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private ShowListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar progressBar;
    private Button button;
    private IShowSearchContract.ShowSearchPresenter presenter;
    private EditText searchParam;
    private OnShowSelectedListener onShowSelectedListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.main_screen, container, false);
        presenter = new ShowListPresenter(this, new GetShowResultIntractor());
        initView(view);
        initRecylerView(view);
        initProgressBar(view);
        return  view;
    }

    private void initView(View view)
    {
        searchParam = view.findViewById(R.id.searchParam);
        button = view.findViewById(R.id.searchButton);
        searchParam = view.findViewById(R.id.searchParam);
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

    private void initRecylerView(View view)
    {
        mRecyclerView = view.findViewById(R.id.showListRecylerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ShowListAdapter(getContext(),null, recyclerItemClickListener);
        mRecyclerView.setAdapter(mAdapter);
    }

    //showing progress bar programatically
    private void initProgressBar(View view)
    {
        progressBar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);

        RelativeLayout relativeLayout = new RelativeLayout(getContext());
        relativeLayout.setGravity(Gravity.CENTER);
        relativeLayout.addView(progressBar);

        RelativeLayout.LayoutParams params = new
            RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT);
        progressBar.setVisibility(View.INVISIBLE);

        //getView().getContext().addContentView(relativeLayout, params);
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
        Log.i(TAG, "load search result");
        mAdapter.setShowDetailsList(showDetailsList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyErrorTitle()
    {
        Log.i(TAG, "load search result");
        Toast.makeText(getContext(), "Empty title", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showResponseFailure()
    {
        Log.i(TAG, "load search result");
        Toast.makeText(getContext(), "Response error", Toast.LENGTH_SHORT).show();
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
        public void onItemClick(ShowSearchDetails showSearchDetails)
        {

            Log.i(TAG, "Show clicked " + showSearchDetails.getTitle());
            onShowSelectedListener.onShowSelected(showSearchDetails.getImdbID());

        }

        @Override
        public void onSaveBookMark(ShowSearchDetails showDetails)
        {
            presenter.saveBookMark(showDetails);
        }
    };

    public void setOnShowSelectedListener(OnShowSelectedListener listener){

        onShowSelectedListener = listener;
    }

    interface OnShowSelectedListener
    {

        void onShowSelected(String id);
    }

}

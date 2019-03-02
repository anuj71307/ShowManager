package com.android.showmanager.view;


import com.android.showmanager.R;
import com.android.showmanager.pojo.ShowDetails;
import com.android.showmanager.pojo.ShowSearchDetails;
import com.android.showmanager.utils.Constants;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ShowListActivity extends AppCompatActivity implements ShowListFragment.OnShowSelectedListener
{

    private static final String TAG = ShowListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSearchFragment();
    }

    private void initSearchFragment()
    {
        ShowListFragment fragment = new ShowListFragment();
        doFragmentTransaction(fragment);
    }

    @Override
    public void onAttachFragment(Fragment fragment)
    {
        if (fragment instanceof ShowListFragment) {
            ShowListFragment headlinesFragment = (ShowListFragment) fragment;
            headlinesFragment.setOnShowSelectedListener(this);
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }


    @Override
    public void onShowSelected(String id)
    {
        ShowDetailsFragment fragment = new ShowDetailsFragment();
        Bundle args = new Bundle();
        args.putString(Constants.IMDB_ID, id);
        fragment.setArguments(args);
        doFragmentTransaction(fragment);
    }

    private void doFragmentTransaction(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}

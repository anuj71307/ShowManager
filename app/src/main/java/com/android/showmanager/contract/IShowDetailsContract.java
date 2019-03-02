package com.android.showmanager.contract;

import java.util.List;

import com.android.showmanager.pojo.ShowDetails;

public interface IShowDetailsContract
{
    interface IShowDetailsPresenter{
        void loadShowDetails(String imdbId);
        void onDestroy();
    }

    interface IShowDetailsView{
        void showProgress();
        void hideProgress();
        void loadSearchResult(ShowDetails showDetails);
        void showResponseFailure();
    }
}
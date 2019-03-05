package com.android.showmanager.adapter;

import com.android.showmanager.pojo.ShowSearchDetails;

public interface IShowClickListner
{
    void onShowClick(ShowSearchDetails showDetails);
    void onSaveBookMark(ShowSearchDetails showDetails);
}

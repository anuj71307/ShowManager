package com.android.showmanager.adapter;

import com.android.showmanager.pojo.ShowSearchDetails;

public interface ItemClickListner
{
    void onItemClick(ShowSearchDetails showDetails);
    void onSaveBookMark(ShowSearchDetails showDetails);
}

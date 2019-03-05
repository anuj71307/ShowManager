package com.android.showmanager.contract;


import java.util.List;

import com.android.showmanager.pojo.ShowSearchDetails;

public interface OnFinishedListener<T>
{
    void onFinished(T object);

    void onFailure();

    void onInternetNotConnected();

    void onBookMarkLoaded(List<ShowSearchDetails> showSearchDetailsList);
}

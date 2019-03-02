package com.android.showmanager.contract;


public interface OnFinishedListener<T>
{
    void onFinished(T object);

    void onFailure();

    void onInternetNotConnected();
}

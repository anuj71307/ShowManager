package com.android.showmanager.utils;

import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener
{
    private static final String TAG = PaginationScrollListener.class.getSimpleName();
    private LinearLayoutManager layoutManager;
    /**
     * Supporting only LinearLayoutManager for now.
     *
     * @param layoutManager
     */
    protected PaginationScrollListener(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        Log.i(TAG, "visibleItemCount " + visibleItemCount +" totalItemCount " + totalItemCount+" "
            + "firstVisibleItemPosition "+ firstVisibleItemPosition);
        Log.i(TAG, "IsLoading? " + isLoading() +" isLastPage? " + isLastPage());
        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                && firstVisibleItemPosition >= 0) {
                Log.i(TAG, "Loading more items");
                loadMoreItems();
            }
        }

    }
    protected abstract void loadMoreItems();

    public abstract int getTotalPageCount();

    public abstract boolean isLastPage();

    public abstract boolean isLoading();
}

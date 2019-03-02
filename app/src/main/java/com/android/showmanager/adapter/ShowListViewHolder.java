package com.android.showmanager.adapter;

import com.android.showmanager.R;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class ShowListViewHolder extends RecyclerView.ViewHolder
{
    ImageView imageView;
    TextView  showNameView;
    TextView showYearView;
    ImageButton bookmarkView;
    public ShowListViewHolder(@NonNull View itemView)
    {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView);
        showNameView =itemView.findViewById(R.id.showName);
        showYearView = itemView.findViewById(R.id.showYear);
    }
}

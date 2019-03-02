package com.android.showmanager.adapter;

import java.util.ArrayList;
import java.util.List;

import com.android.showmanager.R;
import com.android.showmanager.pojo.ShowSearchDetails;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ShowListAdapter extends RecyclerView.Adapter<ShowListViewHolder>
{

    List<ShowSearchDetails> showDetailsList;
    ItemClickListner listner;
    private Context context;

    public ShowListAdapter(Context context, ArrayList<ShowSearchDetails> showDetailsList, ItemClickListner listner)
    {
        this.showDetailsList = showDetailsList;
        this.listner = listner;
        this.context = context;
    }

    public void setShowDetailsList(List<ShowSearchDetails> showDetailsList)
    {
        this.showDetailsList = showDetailsList;
    }

    @NonNull
    @Override
    public ShowListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_list_layout, parent, false);

        ShowListViewHolder viewHolder = new ShowListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShowListViewHolder holder, final int position)
    {
        final ShowSearchDetails showDetails = showDetailsList.get(position);
        holder.showYearView.setText(showDetails.getYear());
        holder.showNameView.setText(showDetails.getTitle());
        Picasso.with(context)
            .load(showDetails.getPoster())
            .placeholder(R.drawable.placeholder_background)
            .error(R.drawable.placeholder_background)
            .fit()
            .noFade()
            .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listner.onItemClick(showDetails);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return showDetailsList != null ? showDetailsList.size() : 0;
    }
}

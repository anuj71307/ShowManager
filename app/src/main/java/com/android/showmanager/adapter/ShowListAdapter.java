package com.android.showmanager.adapter;

import java.util.ArrayList;
import java.util.List;

import com.android.showmanager.R;
import com.android.showmanager.pojo.ShowSearchDetails;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ShowListAdapter extends RecyclerView.Adapter<ShowListAdapter.ShowListViewHolder>
{

    private static final String TAG = ShowListAdapter.class.getSimpleName();
    // View Types
    List<ShowSearchDetails> showDetailsList;
    IShowClickListner listner;
    private Context context;

    public ShowListAdapter(Context context, IShowClickListner listner)
    {
        this.showDetailsList = new ArrayList<>();
        this.listner = listner;
        this.context = context;
    }

    public void setShowDetailsList(List<ShowSearchDetails> showDetailsList)
    {
        this.showDetailsList.addAll(showDetailsList);
    }

    public void clearList(){
        this.showDetailsList.clear();
    }
    public List<ShowSearchDetails> getShowDetailsList()
    {
        return showDetailsList;
    }

    @NonNull
    @Override
    public ShowListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View viewItem = inflater.inflate(R.layout.show_list_layout, parent, false);
        ShowListViewHolder viewHolder = new ShowListViewHolder(viewItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShowListViewHolder holder, int position)
    {
        final ShowSearchDetails showDetails = showDetailsList.get(position);
        holder.showYearView.setText(showDetails.getYear());
        holder.showNameView.setText(showDetails.getTitle());
        //TODO Anuj scroll progress bar
        Picasso.with(context)
            .load(showDetails.getPoster())
            .placeholder(R.drawable.placeholder_background)
            .error(R.drawable.placeholder_background)
            .fit()
            .noFade()
            .into(holder.imageView, new Callback()
            {
                @Override
                public void onSuccess()
                {
                    //TODO Anuj
                }

                @Override
                public void onError()
                {
                    //TODO Anuj
                }
            });

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listner.onShowClick(showDetails);
            }
        });

        holder.button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listner.onSaveBookMark(showDetails);
            }
        });

    }

    @Override
    public int getItemCount()
    {
        Log.i(TAG, "showDetails count " + showDetailsList.size());
        return showDetailsList != null ? showDetailsList.size() : 0;
    }


    class ShowListViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView showNameView;
        TextView showYearView;
        ImageButton button;

        public ShowListViewHolder(@NonNull View itemView)
        {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            showNameView = itemView.findViewById(R.id.showName);
            showYearView = itemView.findViewById(R.id.showYear);
            button = itemView.findViewById(R.id.bookmarkButton);
        }
    }

}

package com.android.showmanager.adapter;

import java.util.ArrayList;
import java.util.List;

import com.android.showmanager.R;
import com.android.showmanager.pojo.ShowSearchDetails;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BookMarkAdapter extends RecyclerView.Adapter<BookMarkAdapter.BookMarkViewHolder>
{
    List<ShowSearchDetails> showList;
    private Context context;

    public BookMarkAdapter(Context context)
    {
        this.showList = new ArrayList<>();
        this.context = context;
    }

    public List<ShowSearchDetails> getShowList()
    {
        return showList;
    }

    public void setShowList(List<ShowSearchDetails> showList)
    {
        this.showList = showList;
    }

    @NonNull
    @Override
    public BookMarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.bookmark_layout, parent, false);
        BookMarkViewHolder viewHolder = new BookMarkViewHolder(viewItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookMarkViewHolder holder, int position)
    {
        ShowSearchDetails showDetail = showList.get(position);
        holder.showName.setText(showDetail.getTitle());
        Picasso.with(context)
            .load(showDetail.getPoster())
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
    }

    @Override
    public int getItemCount()
    {
        return showList != null ? showList.size() : 0;
    }

    class BookMarkViewHolder extends RecyclerView.ViewHolder
    {

        ImageView imageView;
        TextView showName;

        public BookMarkViewHolder(@NonNull View itemView)
        {
            super(itemView);
            showName = itemView.findViewById(R.id.showName);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}

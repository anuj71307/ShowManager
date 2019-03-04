package com.android.showmanager.task;

import java.util.List;

import com.android.showmanager.MyApplication;
import com.android.showmanager.dao.BookmarkRepository;
import com.android.showmanager.pojo.ShowSearchDetails;

import android.os.AsyncTask;

/**
 * Class to insert/fect data from db on background thread;
 */
public class BookMarkTask extends AsyncTask<Void, Void, List<ShowSearchDetails>>
{
    onTaskExecuted listener;
    public BookMarkTask(onTaskExecuted listener){
       this.listener = listener;
    }


    @Override
    protected List<ShowSearchDetails> doInBackground(Void... voids)
    {
        BookmarkRepository repository = MyApplication.getMyApplicationContext().getBookMarkRepository();
        return  repository.getAllBookMark();
    }

    protected void onPostExecute(List<ShowSearchDetails> result)
    {
        if (listener != null) {
            listener.onBookMarkLoaded(result);
        }
    }

   public interface onTaskExecuted{
        void onBookMarkLoaded(List<ShowSearchDetails> result);
     }
}

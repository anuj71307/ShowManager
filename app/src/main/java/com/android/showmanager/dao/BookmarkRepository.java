package com.android.showmanager.dao;

import java.util.List;

import com.android.showmanager.MyApplication;
import com.android.showmanager.pojo.ShowSearchDetails;
import com.android.showmanager.task.BookMarkTask;

import android.util.Log;

import androidx.room.Room;

/**
 * class to handle bookmark db
 */
public class BookmarkRepository
{
    private String DB_NAME = "showdb";
    private BookMarkDatabase bookMarkDatabase;
    private static final String TAG = BookmarkRepository.class.getSimpleName();

    public BookmarkRepository()
    {
        bookMarkDatabase = Room.databaseBuilder(MyApplication.getMyApplicationContext(), BookMarkDatabase.class,
            DB_NAME).allowMainThreadQueries().build();
    }

    /**
     * insert data in bookmark table
     */
    public void insertBookMark(ShowSearchDetails showSearchDetails)
    {
        try {
            bookMarkDatabase.daoAccess().insertBookmark(showSearchDetails);
        }
        catch (Exception e){
            Log.i(TAG, "Exception while inserting bookmark " + e);
        }
    }

    /**
     * delete data from bookmark db
     */
    public void deleteBookMark(ShowSearchDetails showSearchDetails)
    {
        bookMarkDatabase.daoAccess().deleteBookmark(showSearchDetails);
    }


    /**
     * fetch all bookmarks from DB
     * @return
     */
    public List<ShowSearchDetails> getAllBookMark()
    {
        return bookMarkDatabase.daoAccess().getAllBookMarks();
    }
}

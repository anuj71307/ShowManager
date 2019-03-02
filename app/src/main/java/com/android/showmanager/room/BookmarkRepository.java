package com.android.showmanager.room;

import java.util.List;

import com.android.showmanager.MyApplication;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

/**
 * class to handle bookmark db
 */
public class BookmarkRepository
{
    private String DB_NAME = "showdb";
    private BookMarkDatabase bookMarkDatabase;

    public BookmarkRepository()
    {
        bookMarkDatabase = Room.databaseBuilder(MyApplication.getMyApplicationContext(), BookMarkDatabase.class, DB_NAME).build();
    }

    /**
     * insert data in bookmark table
     * @param imdbID
     * @param title
     * @param year
     * @param poster
     */
    public void insertBookMark(String imdbID, String title, String year, String poster)
    {
        BookMark bookMark = new BookMark();
        bookMark.setImdbID(imdbID);
        bookMark.setTitle(title);
        bookMark.setYear(year);
        bookMark.setPoster(poster);
        bookMarkDatabase.daoAccess().insertBookmark(bookMark);
    }

    /**
     * delete data from bookmark db
     * @param imdbID
     * @param title
     * @param year
     * @param poster
     */
    public void deleteBookMark(String imdbID, String title, String year, String poster)
    {
        BookMark bookMark = new BookMark();
        bookMark.setImdbID(imdbID);
        bookMark.setTitle(title);
        bookMark.setYear(year);
        bookMark.setPoster(poster);
        bookMarkDatabase.daoAccess().deleteBookmark(bookMark);
    }


    /**
     * fetch all bookmarks from DB
     * @return
     */
    public LiveData<List<BookMark>> getAllBookMark()
    {
        return bookMarkDatabase.daoAccess().getAllBookMarks();
    }
}

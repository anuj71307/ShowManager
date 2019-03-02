package com.android.showmanager.room;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface BookmarkDao
{
    @Insert
    boolean insertBookmark(BookMark bookMark);

    @Query("SELECT * FROM bookmarkdata order by _id desc")
    LiveData<List<BookMark>> getAllBookMarks();

    @Delete
    void deleteBookmark(BookMark bookMark);
}

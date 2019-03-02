package com.android.showmanager.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {BookMark.class}, version = 1, exportSchema = false)
public abstract class BookMarkDatabase extends RoomDatabase
{
    public abstract BookmarkDao daoAccess();
}

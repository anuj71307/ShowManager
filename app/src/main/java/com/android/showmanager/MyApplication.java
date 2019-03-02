package com.android.showmanager;

import com.android.showmanager.room.BookmarkRepository;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application
{
    private static MyApplication application;
    private static BookmarkRepository bookmarkRepository;
    private static final Object DAO_SERVICE = new Object();

    @Override
    public void onCreate()
    {
        super.onCreate();
        // Required initialization logic here!
    }

    public static MyApplication getMyApplicationContext()
    {
        return application;
    }

    @Override
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);
        application = this;

    }

    public BookmarkRepository getBookMarkRepository(){
        if(bookmarkRepository==null){
            synchronized (DAO_SERVICE){
                if(bookmarkRepository==null){
                    bookmarkRepository = new BookmarkRepository();
                }
            }

        }

        return  bookmarkRepository;
    }
}
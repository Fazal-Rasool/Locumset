package com.adaxiom.utils;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;


/**
 * Created by Ravi on 13/08/15.
 */
public class MyApplication extends Application {
    public static final String TAG = MyApplication.class
            .getSimpleName();

    private static MyApplication mInstance;

    private static MyApplication sInstance;

    @Override
    public void onCreate() {

        super.onCreate();
        mInstance = this;
        sInstance=this;

        Stetho.initializeWithDefaults(this);
    }

//    private void universalImageLoader() {
//
//
//        // UNIVERSAL IMAGE LOADER SETUP
//        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
//                .cacheOnDisc(true).cacheInMemory(true)
//                .imageScaleType(ImageScaleType.EXACTLY)
//                .displayer(new FadeInBitmapDisplayer(300)).build();
//
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
//                getApplicationContext())
//                .defaultDisplayImageOptions(defaultOptions)
//                .memoryCache(new WeakMemoryCache())
//                .discCacheSize(100 * 1024 * 1024).build();
//
//        ImageLoader.getInstance().init(config);
//        // END - UNIVERSAL IMAGE LOADER SETUP
//
//
//
//
//
//    }




    public static MyApplication getsInstance(){

        return sInstance;
    }

    public static Context getAppContext(){

        return sInstance.getApplicationContext();
    }

}

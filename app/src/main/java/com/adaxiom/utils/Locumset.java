package com.adaxiom.utils;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;

import com.adaxiom.manager.DownloaderManager;
import com.adaxiom.network.BackendConnector;
import com.adaxiom.network.RetrofitConnector;
import com.facebook.stetho.Stetho;
import com.pixplicity.easyprefs.library.Prefs;

import static com.adaxiom.utils.Constants.BASE_URL_LIVE;


/**
 * Created by Ravi on 13/08/15.
 */
public class Locumset extends Application {
    public static final String TAG = Locumset.class
            .getSimpleName();

    BackendConnector connector;
    DownloaderManager downloaderManager;

    private static Locumset mInstance;

    private static Locumset sInstance;

    @Override
    public void onCreate() {

        super.onCreate();
        mInstance = this;
        sInstance=this;

        Stetho.initializeWithDefaults(this);


        //EasyPreferences Library
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();



        connector = new RetrofitConnector();
        connector.setupConnector(BASE_URL_LIVE, BASE_URL_LIVE);

        downloaderManager = new DownloaderManager(connector);
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




    public static Locumset getsInstance(){

        return sInstance;
    }

    public static Context getAppContext(){

        return sInstance.getApplicationContext();
    }

}

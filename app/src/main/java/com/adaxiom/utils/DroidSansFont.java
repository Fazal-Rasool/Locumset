package com.adaxiom.utils;

import android.content.Context;
import android.graphics.Typeface;


public class DroidSansFont {

    private static DroidSansFont instance;
    private static Typeface typeface;

    public static DroidSansFont getInstance(Context context) {
        synchronized (DroidSansFont.class) {
            if (instance == null) {
                instance = new DroidSansFont();
                typeface = Typeface.createFromAsset(context.getResources().getAssets(), "droid_sans.ttf");
            }
            return instance;
        }
    }

    public Typeface getTypeFace() {
        return typeface;
    }
}

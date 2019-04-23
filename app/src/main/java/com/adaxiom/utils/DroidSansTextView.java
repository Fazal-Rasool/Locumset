package com.adaxiom.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by dell on 3/9/2017.
 */

public class DroidSansTextView extends TextView {

    public DroidSansTextView(Context context) {
        super(context);
        setTypeface(DroidSansFont.getInstance(context).getTypeFace());
    }

    public DroidSansTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(DroidSansFont.getInstance(context).getTypeFace());
    }

    public DroidSansTextView(Context context, AttributeSet attrs,
                             int defStyle) {
        super(context, attrs, defStyle);
        setTypeface(DroidSansFont.getInstance(context).getTypeFace());
    }

}

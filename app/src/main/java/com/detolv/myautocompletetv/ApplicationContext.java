package com.detolv.myautocompletetv;

import android.app.Application;
import android.content.Context;

/**
 * Created by detolv on 8/13/17.
 */

public class ApplicationContext extends Application {
    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
}

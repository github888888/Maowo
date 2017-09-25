package com.music.maowo;

import android.app.Application;
import android.util.DisplayMetrics;

/**
 * Created by Administrator on 2017-9-16 0016.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        DisplayMetrics dm = getResources().getDisplayMetrics();
        Constants.screenHeight = dm.heightPixels;
        Constants.screenWidth  = dm.widthPixels;
    }
}

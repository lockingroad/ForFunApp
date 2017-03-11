package com.linewow.xhyy.forfunapp.app;

import android.app.Application;
import android.content.res.Resources;

/**
 * Created by LXR on 2017/1/17.
 */

public class ForFunApp extends Application {
    private static ForFunApp instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }

    public static ForFunApp getAppContext(){
        return instance;
    }

    public static Resources getAppResources() {
        return instance.getResources();
    }
}

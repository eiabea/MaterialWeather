package com.materialweather.widget.util;

import android.app.Application;
import android.util.Log;

import com.google.gson.Gson;

/**
 * Created by eiabea on 11/23/14.
 */
public class App extends Application {

    public static final String TAG = App.class.getSimpleName();

    /**
     * Object of own Class
     */
    private static App me;

    private Gson gson;

    public static App getInstance() {
        return me;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate");

        gson = new Gson();

        me = this;

    }

    public Gson getGson() {
        return gson;
    }
}

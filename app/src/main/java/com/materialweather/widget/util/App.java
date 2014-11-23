package com.materialweather.widget.util;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.materialweather.widget.service.UpdateService;

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

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate");

        gson = new Gson();

        me = this;

    }

    public static App getInstance(){
        return me;
    }

    public Gson getGson(){
        return gson;
    }
}

package com.materialweather.widget.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.materialweather.widget.model.OpenWeatherData;
import com.materialweather.widget.util.OpenWeatherRequest;

import java.util.ArrayList;

public class UpdateService extends Service {

    public static final String TAG = Service.class.getSimpleName();

    public static final String PARAM_CITY = "param_city";

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static ArrayList<UpdateInterface> interfaces = new ArrayList<UpdateInterface>();
    private RequestQueue queue;

    public UpdateService() {

    }

    public static void addUpdateInterface(UpdateInterface interfaceToAdd) {
        interfaces.add(interfaceToAdd);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");

        queue = Volley.newRequestQueue(this);
        queue.start();

        Log.d(TAG, "RequestQueue started");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.d(TAG, "onStartCommand");

        String city = intent.getStringExtra(PARAM_CITY);

        String url = BASE_URL + city;

        queue.add(new OpenWeatherRequest(url, null, new Response.Listener<OpenWeatherData>() {
            @Override
            public void onResponse(OpenWeatherData data) {
                Log.d(TAG, "onResponse: " + data.getName());
                data.dump();
                onData(data);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse");
            }
        }));


        return START_NOT_STICKY;
    }

    private void onData(OpenWeatherData data) {
        for (UpdateInterface tmpInterface : interfaces) {
            tmpInterface.onData(data);
        }
    }

    public interface UpdateInterface {
        public void onData(OpenWeatherData data);
    }
}

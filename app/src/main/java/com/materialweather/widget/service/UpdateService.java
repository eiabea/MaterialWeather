package com.materialweather.widget.service;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.materialweather.widget.data.DataProvider;
import com.materialweather.widget.model.City;
import com.materialweather.widget.model.OpenWeatherData;
import com.materialweather.widget.util.App;
import com.materialweather.widget.util.OpenWeatherRequest;

import java.util.ArrayList;

public class UpdateService extends Service {

    public static final String TAG = Service.class.getSimpleName();

    public static final String PARAM_ACTION = "param_action";
    public static final String PARAM_SEARCH_STRING = "param_search_string";
    public static final String PARAM_CITY_ID = "param_city_id";

    public enum Action{
        RELOAD_ALL_CITIES,
        RELOAD_ONE_CITY,
        SEARCH_CITY
    }

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";
    private static final String BY_ID = BASE_URL + "?id=";
    private static final String BY_STRING = BASE_URL + "?q=";

    private RequestQueue queue;

    private static ArrayList<UpdateInterface> interfaces = new ArrayList<>();

    public UpdateService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");

        queue = Volley.newRequestQueue(this);
        queue.start();

        Log.d(TAG, "RequestQueue started");
    }

    public static void addUpdateInterface(UpdateInterface interfaceToAdd){
        interfaces.add(interfaceToAdd);
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

        Action action = (Action) intent.getSerializableExtra(PARAM_ACTION);

        Log.d(TAG, "Action: " + action.name());

        switch (action){
            case SEARCH_CITY:

                String searchString = intent.getStringExtra(PARAM_SEARCH_STRING);

                searchCity(searchString);

                break;
            case RELOAD_ONE_CITY:

                long cityId = intent.getLongExtra(PARAM_CITY_ID, 0);

                reloadOneCity(cityId);

                break;
            case RELOAD_ALL_CITIES:

                String[] projection = { City.CITY_ID };

                Cursor allCities = getContentResolver().query(City.CONTENT_URI, projection, null,null, null);

                while (allCities.moveToNext()) {
                    long currentCityId = allCities.getLong(allCities.getColumnIndex(City.CITY_ID));

                    reloadOneCity(currentCityId);
                }

                break;
        }

        return START_NOT_STICKY;
    }

    private void searchCity(String searchString){
        String url = BY_STRING + searchString;

        queue.add(new OpenWeatherRequest(url, null, new Response.Listener<OpenWeatherData>() {
            @Override
            public void onResponse(OpenWeatherData data) {
                if(data.isValid()){
                    Log.d(TAG, "onResponse: " + data.getName());

                    City city = new City();
                    city.setCityId(data.getId());
                    city.setName(data.getName());
                    city.setWeatherJson(App.getInstance().getGson().toJson(data));

                    DataProvider.insertOrUpdateCity(getApplicationContext(), city);

                    onData(data);
                }else{
                    onError("Couldn't find city, sorry!");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse");
                onError("Some kind of servererror!");
            }
        }));
    }

    private void reloadOneCity(long cityId){

        Log.d(TAG, "Reload City: " + cityId);

        String url = BY_ID + cityId;

        queue.add(new OpenWeatherRequest(url, null, new Response.Listener<OpenWeatherData>() {
            @Override
            public void onResponse(OpenWeatherData data) {
                if(data.isValid()){
                    Log.d(TAG, "onResponse: " + data.getName());

                    City city = new City();
                    city.setCityId(data.getId());
                    city.setName(data.getName());
                    city.setWeatherJson(App.getInstance().getGson().toJson(data));

                    DataProvider.insertOrUpdateCity(getApplicationContext(), city);

                    onData(data);
                }else{
                    onError("Couldn't find city, sorry!");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse");
                onError("Some kind of servererror!");
            }
        }));
    }

    private void onData(OpenWeatherData data){
        for(UpdateInterface tmpInterface : interfaces){
            tmpInterface.onData(data);
        }
    }

    private void onError(String message){
        for(UpdateInterface tmpInterface : interfaces){
            tmpInterface.onError(message);
        }
    }

    public interface UpdateInterface{
        public void onData(OpenWeatherData data);
        public void onError(String message);
    }
}

package com.materialweather.widget.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.materialweather.widget.data.DataProvider;
import com.materialweather.widget.data.DatabaseHelper;

public class City {

    private static final String URL = "content://" + DataProvider.PROVIDER_NAME + "/" + DatabaseHelper.CITIES_TABLE_NAME;
    public static final Uri CONTENT_URI = Uri.parse(URL);

    public static final String _ID = "_id";
    public static final String CITY_ID = "city_id";
    public static final String NAME = "name";
    public static final String WEATHER_JSON = "weather_json";

    private long id;
    private long cityId;
    private String name;
    private String weatherJson;

    public City() {

    }

    public City(Cursor c) {
        setId(c.getLong(c.getColumnIndex(_ID)));
        setCityId(c.getLong(c.getColumnIndex(CITY_ID)));
        setName(c.getString(c.getColumnIndex(NAME)));
        setWeatherJson(c.getString(c.getColumnIndex(WEATHER_JSON)));
    }

    public ContentValues getContentValues(boolean forInsert) {
        ContentValues values = new ContentValues();

        if (forInsert) {
            //values.put(_ID, getId());
        }

        values.put(NAME, getName());
        values.put(CITY_ID, getCityId());
        values.put(WEATHER_JSON, getWeatherJson());

        return values;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeatherJson() {
        return weatherJson;
    }

    public void setWeatherJson(String weatherJson) {
        this.weatherJson = weatherJson;
    }
}

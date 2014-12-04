package com.materialweather.widget.model;

import android.util.Log;

import com.materialweather.widget.util.App;

import org.apache.http.HttpStatus;
import org.apache.http.protocol.HTTP;

import java.util.ArrayList;

/**
 * Created by eiabea on 11/23/14.
 */
public class OpenWeatherData {
    public  static final String TAG = OpenWeatherData.class.getSimpleName();

    private Coord coord;
    private Sys sys;
    private ArrayList<Weather> weather;
    private String base;
    private Main main;
    private Wind wind;
    private Clouds clouds;
    private long dt;
    private long id;
    private String name;
    private int cod;

    public boolean isValid(){
        return cod == HttpStatus.SC_OK;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public ArrayList<Weather> getWeather() {
        return weather;
    }

    public void setWeather(ArrayList<Weather> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String dump(){
        String jsonDump = App.getInstance().getGson().toJson(this);
        Log.d(TAG, jsonDump);

        return jsonDump;
    }
}

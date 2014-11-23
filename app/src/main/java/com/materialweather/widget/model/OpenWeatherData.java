package com.materialweather.widget.model;

import java.util.ArrayList;

/**
 * Created by eiabea on 11/23/14.
 */
public class OpenWeatherData {
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
}

package com.materialweather.widget.util;

/**
 * Created by eiabea on 11/23/14.
 */
public class Helper {

    //1 kelvin = -273.15 degrees Celsius
    public static double kelvinToCelsius(double kelvin){
        return kelvin - 273.15d;
    }

    //1 kelvin = -459,67 degrees Fahrenheit
    public static double kelvinToFahrenheit(double kelvin){
        return kelvin - 459.67d;
    }
}

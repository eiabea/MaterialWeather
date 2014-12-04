package com.materialweather.widget.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by eiabea on 11/23/14.
 */
public class Helper {

    //1 kelvin = -273.15 degrees Celsius
    public static double kelvinToCelsius(double kelvin){
        return kelvin - 273.15d;
    }

    public static String formatCelsius(double celsius){
        return round(celsius, 2) + "°C";
    }

    //1 kelvin = -459,67 degrees Fahrenheit
    public static double kelvinToFahrenheit(double kelvin){
        return kelvin - 459.67d;
    }

    public static String formatFahrenheit(double fahrenheit){
        return round(fahrenheit, 2) + "°F";
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}

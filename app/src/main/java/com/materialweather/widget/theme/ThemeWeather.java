package com.materialweather.widget.theme;

import android.graphics.Color;

import com.materialweather.widget.theme.interfaces.IThemeWeather;

/**
 * Created by sprik on 26/11/14.
 */
public class ThemeWeather implements IThemeWeather {
    int header, content, footer;

    public ThemeWeather(int header, int content, int footer) {
        this.header = header;
        this.content = content;
        this.footer = footer;
    }

    @Override
    public int getHeaderBarColor() {
        return header;
    }

    @Override
    public void setHeaderBarColor(int header) {
        this.header = header;
    }

    @Override
    public int getContentBarColor() {
        return content;
    }

    @Override
    public void getContentBarColor(int content) {
        this.content = content;
    }

    @Override
    public int getFooterBarColor() {
        return footer;
    }

    @Override
    public void setFooterBarColor(int footer) {
       this.footer = footer;
    }
}

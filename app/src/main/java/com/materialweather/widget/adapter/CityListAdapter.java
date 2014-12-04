package com.materialweather.widget.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.materialweather.widget.R;
import com.materialweather.widget.model.City;
import com.materialweather.widget.model.OpenWeatherData;
import com.materialweather.widget.util.App;
import com.materialweather.widget.util.Helper;

public class CityListAdapter extends CursorAdapter {

    private LayoutInflater inflater;

    public CityListAdapter(Context context, Cursor c) {
        super(context, c, true);

        inflater = LayoutInflater.from(context);

    }

    @Override
    public View newView(Context ctx, Cursor c, ViewGroup parent) {

        View v = inflater.inflate(R.layout.view_city, parent, false);

        CityViewHolder viewHolder = new CityViewHolder();

        viewHolder.txtTitle = (TextView) v.findViewById(R.id.txt_city_name);

        v.setTag(viewHolder);

        return v;
    }

    public static class CityViewHolder {
        long cityId;

        TextView txtTitle;

        public long getCityId() {
            return cityId;
        }
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        CityViewHolder viewHolder = (CityViewHolder) view.getTag();

        City city = new City(cursor);
        OpenWeatherData data = App.getInstance().getGson().fromJson(city.getWeatherJson(), OpenWeatherData.class);

        viewHolder.cityId = city.getCityId();

        if(city.getCityId() != 0){
            viewHolder.txtTitle.setText(city.getName() + " - " + Helper.formatCelsius(Helper.kelvinToCelsius(data.getMain().getTemp())));
        }
    }

}

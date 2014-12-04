
package com.materialweather.widget.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.materialweather.widget.model.City;

public class DatabaseHelper extends SQLiteOpenHelper {
	
    /**
     * Database specific constant declarations
     */

    public static final String TAG = DatabaseHelper.class.getSimpleName();
    
    public static final String CITIES_TABLE_NAME = "cities";

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "materialweather";
    
    private static final String CREATE_CITIES_TABLE =
            " CREATE TABLE " + CITIES_TABLE_NAME +
                    " (" + City._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " " + City.CITY_ID + " INTEGER, " +
                    " " + City.NAME + " TEXT, " +
                    " " + City.WEATHER_JSON + " TEXT" +
                    ");";

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    	db.execSQL(CREATE_CITIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        
    	Log.d(TAG, "Upgrading Database from v" + oldVersion + " to v" + newVersion);
        
    	db.execSQL("DROP TABLE IF EXISTS " + CITIES_TABLE_NAME);
        onCreate(db);
    }
}

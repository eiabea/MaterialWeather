package com.materialweather.widget.provider;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

public class WeatherProvider extends AppWidgetProvider {
    public static String ACTION_WIDGET_RECEIVER = "ActionReceiverWidget";

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        /*RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_main);
        Intent action = new Intent(context, FloatingWidget.class);
        action.setAction(ACTION_WIDGET_RECEIVER);
        PendingIntent actionPendingIntent = PendingIntent.getBroadcast(context, 0, action, 0);
        remoteViews.setOnClickPendingIntent(R.id.txtText, actionPendingIntent);
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);*/
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_WIDGET_RECEIVER)) {

        }
        super.onReceive(context, intent);
    }
}


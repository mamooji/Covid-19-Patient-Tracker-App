package com.example.dbmarch11;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

//CLASS         : WidgetProvider
//PURPOSE       : The widgetProvider page class for the application.
//                This class sets up the widget for display, and handle the onlick method
//                to allow the button to go into our application
public class WidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        for (int appWidgetId : appWidgetIds)
        {
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);


            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.tracker_widget);
            views.setOnClickPendingIntent(R.id.widget_btn, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);

        }
    }
}

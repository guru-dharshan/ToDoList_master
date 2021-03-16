package com.devgd.todolist;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {
    public static final String ACTION_OPEN="ACTIONOPEN";
    public static final String EXTRA_POS="EXTRAPOS";
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

       //list adapter
        Intent serviceIntent = new Intent(context, AppWidgetSevice.class);
        serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,appWidgetId);

        ///set onclick listner
        Intent clickintent = new Intent(context,AddTask.class);
        clickintent.setAction(ACTION_OPEN);
        PendingIntent clickpendingintent = PendingIntent.getActivity(context,0,clickintent,0);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setPendingIntentTemplate(R.id.listview,clickpendingintent);
        views.setRemoteAdapter(R.id.listview,serviceIntent);



        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


}
package com.balinasoft.forexnews.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.balinasoft.forexnews.NewsActivity;
import com.balinasoft.forexnews.R;
import com.balinasoft.forexnews.utils.Constants;

import java.util.Arrays;

/**
 * Created by Serega on 07.08.2016.
 */
public class DateWidget extends AppWidgetProvider {

    final String LOG_TAG = "myLog";

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int i : appWidgetIds) {
            updateWidget(context, appWidgetManager, i);
        }
        Log.d(LOG_TAG, "onUpdate " + Arrays.toString(appWidgetIds));
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.d(LOG_TAG, "onDeleted " + Arrays.toString(appWidgetIds));
    }

    static void updateWidget(Context ctx, AppWidgetManager appWidgetManager, int widgetID) {

        SharedPreferences preferences = ctx.getSharedPreferences(Constants.SP_FOREX_NEWS, Context.MODE_PRIVATE);

        RemoteViews widgetView = new RemoteViews(ctx.getPackageName(), R.layout.widget_date);
        widgetView.setTextViewText(R.id.tvLastUpdateDate, ctx.getString(R.string.text_last_update) + preferences.getString(Constants.LAST_UPDATE_DATE, "Вы еще не получали новости"));

        Intent configIntent = new Intent(ctx, NewsActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(ctx, widgetID, configIntent, 0);
        widgetView.setOnClickPendingIntent(R.id.llWidget, pIntent);

        // Обновляем виджет
        appWidgetManager.updateAppWidget(widgetID, widgetView);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(Constants.ACTION_CHANGE_DATE)) {
            int[] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, DateWidget.class));
            for (int id : ids) {
                updateWidget(context, AppWidgetManager.getInstance(context), id);
            }
        }
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }
}

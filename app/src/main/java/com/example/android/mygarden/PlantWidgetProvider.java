package com.example.android.mygarden;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.android.mygarden.ui.MainActivity;

public class PlantWidgetProvider extends AppWidgetProvider {

    static void updateWidget(Context context,AppWidgetManager manager,int imgRes, int id){
        Intent i = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context,0,i,0);
        RemoteViews views = new RemoteViews(context.getPackageName(),
                R.layout.plant_widget);
        views.setOnClickPendingIntent(R.id.plant_image,pi);

        Intent waterIntent = new Intent(context, PlantWateringService.class);
        waterIntent.setAction(PlantWateringService.ACTION_WATER_PLANTS);
        PendingIntent Pintent = PendingIntent.getService(
                context,0,waterIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setImageViewResource(R.id.plant_image,imgRes);
        views.setOnClickPendingIntent(R.id.water_image,Pintent);

        manager.updateAppWidget(id, views);
    }

    public static void updateAppWidgets(Context context, AppWidgetManager manager, int imgRes, int[] ids) {
        for (int id : ids)
            updateWidget(context,manager,imgRes,id);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        PlantWateringService.startUpdatePlantWidget(context);
//        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}

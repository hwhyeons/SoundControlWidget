package com.example.soundcontrol;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.util.Log;
import android.widget.Button;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;

import static android.content.Context.AUDIO_SERVICE;

/**
 * Implementation of App Widget functionality.
 */
public class SoundControlWidget extends AppWidgetProvider {
    private final String ACTION_MUTE = "MUTE";
    private final String ACTION_YOUTUBE = "YOUTUBE";
    private final String ACTION_MUSIC = "MUSIC";

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.sound_control_widget);
        Intent intent1 = new Intent(context, SoundControlWidget.class).setAction(ACTION_MUTE);
        Intent intent2 = new Intent(context, SoundControlWidget.class).setAction(ACTION_YOUTUBE);
        Intent intent3 = new Intent(context, SoundControlWidget.class).setAction(ACTION_MUSIC);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context,0, intent1, 0);
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context,0, intent2, 0);
        PendingIntent pendingIntent3 = PendingIntent.getBroadcast(context,0, intent3, 0);
        views.setOnClickPendingIntent(R.id.bt_mute, pendingIntent1);
        views.setOnClickPendingIntent(R.id.bt_youtube, pendingIntent2);
        views.setOnClickPendingIntent(R.id.bt_music, pendingIntent3);
//        views.setTextViewText(R.id.appwidget_text, widgetText);

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

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        if(action.equals(ACTION_MUTE)){
            AudioManager mAudioManager = (AudioManager)context.getSystemService(AUDIO_SERVICE);
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,mAudioManager.getStreamMinVolume(AudioManager.STREAM_MUSIC),AudioManager.FLAG_SHOW_UI);
        } else if (action.equals(ACTION_YOUTUBE)) {
            AudioManager mAudioManager = (AudioManager)context.getSystemService(AUDIO_SERVICE);
            int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            int settingVolume = (int)(maxVolume*0.6);
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,settingVolume,AudioManager.FLAG_SHOW_UI);
        } else if (action.equals(ACTION_MUSIC)) {
            AudioManager mAudioManager = (AudioManager)context.getSystemService(AUDIO_SERVICE);
            int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            int settingVolume = (int)(maxVolume*0.45);
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,settingVolume,AudioManager.FLAG_SHOW_UI);
        }
    }
}
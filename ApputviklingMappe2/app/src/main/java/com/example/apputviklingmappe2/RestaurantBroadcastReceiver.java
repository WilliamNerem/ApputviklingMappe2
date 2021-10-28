package com.example.apputviklingmappe2;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;

public class RestaurantBroadcastReceiver extends BroadcastReceiver {
    SharedPreferences prefs;

    @Override
    public void onReceive(Context context, Intent intent) {
        prefs = context.getSharedPreferences("com.example.apputviklingmappe2", Context.MODE_PRIVATE);
        try {
            prefs.getBoolean("SMS_Boolean", false);
        } catch (Exception e) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("SMS_Boolean", false);
            editor.apply();
        }
        if (prefs.getBoolean("SMS_Boolean", false)) {
            Log.v("Dette", "Er en test : " + context.getSharedPreferences("SMS_Boolean", Activity.MODE_PRIVATE));
            Intent iPeri = new Intent(context, SetPeriodicalService.class);
            context.startService(iPeri);
            Log.v("onReceive test", "Er i onReceive");
        }
    }
    }


package com.example.apputviklingmappe2;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class RestaurantBroadcastReceiver extends BroadcastReceiver {
    SharedPreferences prefs;

    @Override
    public void onReceive(Context context, Intent intent) {
        prefs = context.getSharedPreferences("com.example.apputviklingmappe2", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        try {
            prefs.getBoolean("SMS_Boolean", false);
        } catch (Exception e) {
            editor.putBoolean("SMS_Boolean", false);
            editor.apply();
        }
        editor.putString("SMS_Time", "12:00");
        if (prefs.getBoolean("SMS_Boolean", false)) {
            Intent iPeri = new Intent(context, SetPeriodicalService.class);
            context.startService(iPeri);
        }
    }
    }


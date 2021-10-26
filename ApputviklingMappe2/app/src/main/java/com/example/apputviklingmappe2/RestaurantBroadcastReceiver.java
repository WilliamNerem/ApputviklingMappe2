package com.example.apputviklingmappe2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class RestaurantBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent iPeri = new Intent(context, SetPeriodicalService.class);
        context.startService(iPeri);
    }

}

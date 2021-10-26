package com.example.apputviklingmappe2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class RestaurantBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "In BroadcastReceiver", Toast.LENGTH_SHORT).show();
        System.out.println("Heihei er i Breceiver");
        Intent iPeri = new Intent(context, SetPeriodicalService.class);
        context.startService(iPeri);
        /*Intent i = new Intent(context, SMSService.class);
      context.startService(i);
         */
    }

}

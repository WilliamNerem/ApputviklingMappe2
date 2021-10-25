package com.example.apputviklingmappe2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class SetPeriodicalService extends Service {
    DBHandler db;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /*java.util.Calendar cal = Calendar.getInstance();
        Intent i = new Intent(this, RestaurantService.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, i, 0);
        AlarmManager alarm =
                (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 60, pintent);

         */

        Intent i = new Intent(this, RestaurantService.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, i, 0);
        SharedPreferences prefs = this.getSharedPreferences("com.example.apputviklingmappe2", Context.MODE_PRIVATE);
        String time = prefs.getString("SMS_TIME", "");
        if(time.equals(Preferanser.getCurrentTime())) {
            // Nå skal den sjekke databasen for bestillinger
            if (db.findNumberofuniqueBestillinger() > 0) {
                Toast.makeText(getBaseContext(), "Nå skal ting sendes ut til folk", Toast.LENGTH_SHORT).show();
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

}

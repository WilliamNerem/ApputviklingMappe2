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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class SetPeriodicalService extends Service {
    DBHandler db;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    /*
    public int onStartCommand(Intent intent, int flags, int startId) {
        while (true) {
            SharedPreferences prefs = this.getSharedPreferences("com.example.apputviklingmappe2", Context.MODE_PRIVATE);
            String time = prefs.getString("SMS_TIME", "");
            if (time.equals(Preferanser.getCurrentTime())) {
                System.out.println(Preferanser.getCurrentTime());
                // Nå skal den sjekke databasen for bestillinger
                if (db.findNumberofuniqueBestillinger() > 0) {
                    ArrayList<Venn> venner = new ArrayList<>();
                    List<Bestilling> bestillinger = db.findAllBestillinger();
                    for (Bestilling bestilling : bestillinger) {
                        venner.add(bestilling.venn);
                    }
                    Preferanser.SendSMS(this, venner);
                }
            }

            return super.onStartCommand(intent, flags, startId);
            wait(1000);
        }
    }
     */
    public int onStartCommand(Intent intent, int flags, int startId) {
        java.util.Calendar cal = Calendar.getInstance();
        Intent i = new Intent(this, RestaurantService.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, i, 0);
        Intent iPer = new Intent(this, SMSService.class);
        PendingIntent pintentPer = PendingIntent.getService(this,0,iPer,0);
        System.out.println("Er i Periodical onStart");
        AlarmManager alarm1 =
                (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        AlarmManager alarm2 =
                (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm1.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 60 * 1000, pintent);
        alarm2.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 60 * 1000, pintentPer);
        return super.onStartCommand(intent, flags, startId);
    }


}

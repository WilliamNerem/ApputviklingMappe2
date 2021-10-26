package com.example.apputviklingmappe2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.util.List;


public class RestaurantService extends Service {
    DBHandler db;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        db = new DBHandler(this);
        SharedPreferences prefs = this.getSharedPreferences("com.example.apputviklingmappe2", Context.MODE_PRIVATE);
        String time = prefs.getString("SMS_Time", "");
        String currentTime = Preferanser.getCurrentTime();
        List<Bestilling> bestillinger = db.findAllBestillinger();
        if (currentTime.equals(time)) {
            if (db.findNumberofuniqueBestillinger() > 0) {
                System.out.println(time + " this is time");
                System.out.println(currentTime + " this is currenttime");
                // Nå skal den sjekke databasen for bestillinger
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Intent i = new Intent(this, MainActivity.class);
                PendingIntent pIntent = PendingIntent.getActivity(this, 0, i, 0);
                Notification notification = new NotificationCompat.Builder(this, "22")
                        .setContentTitle("Restaurant notifikasjon")
                        .setContentText("Du har bestilt restaurant!!")
                        .setSmallIcon(R.drawable.ic_baseline_restaurant_24)
                        .setContentIntent(pIntent).build();
                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                notificationManager.notify(0, notification);
            }
        }



        return super.onStartCommand(intent, flags, startId);
    }
}

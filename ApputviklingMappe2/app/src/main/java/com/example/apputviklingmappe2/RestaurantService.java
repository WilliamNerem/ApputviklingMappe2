package com.example.apputviklingmappe2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
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
                long curId;
                long lastId = 0;
                int channelId = 20;
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Intent i = new Intent(this, BestillBordList.class);
                PendingIntent pIntent = PendingIntent.getActivity(this, 0, i, 0);
                for (Bestilling bestilling : bestillinger) {
                    curId = bestilling.get_ID();
                    if (curId != lastId) {
                        lastId = curId;
                        Notification notification = new NotificationCompat.Builder(this, Integer.toString(channelId))
                                .setContentTitle(bestilling.getRestaurant().getNavn())
                                .setContentText("Velkommen til oss klokken " + bestilling.getTime())
                                .setSmallIcon(R.drawable.ic_baseline_restaurant_24)
                                .setContentIntent(pIntent).build();
                        notification.flags |= Notification.FLAG_AUTO_CANCEL;
                        notificationManager.notify(channelId, notification);
                        channelId++;
                    }
                }
            }
        }



        return super.onStartCommand(intent, flags, startId);
    }
}

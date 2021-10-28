package com.example.apputviklingmappe2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.List;

public class DeleteService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        DBHandler db = new DBHandler(this);
        String currentTime = Preferanser.getCurrentTime();
        System.out.println("Tiden er "+currentTime);
        if (db.findNumberofuniqueBestillinger() > 0) {
            List<Bestilling> bestillinger = db.findAllBestillinger();
            for (Bestilling bestilling : bestillinger) {
                if (bestilling.getTime().equals(currentTime)) {
                    db.deleteBestilling(bestilling.get_ID(),bestilling.getVenn().get_ID());
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }
}

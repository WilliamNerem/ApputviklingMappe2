package com.example.apputviklingmappe2;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SMSService extends Service {
    private static int MY_PERMISSIONS_REQUEST_SEND_SMS;
    private static int MY_PHONE_STATE_PERMISSION;
    private DBHandler db;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        db = new DBHandler(this);
        String currentTime = Preferanser.getCurrentTime();
        System.out.println("Er i periodical SMSSERvice");
        if (MY_PERMISSIONS_REQUEST_SEND_SMS == PackageManager.PERMISSION_GRANTED &&
                MY_PHONE_STATE_PERMISSION ==
                        PackageManager.PERMISSION_GRANTED) {
            SmsManager smsMan = SmsManager.getDefault();
            SharedPreferences prefs = this.getSharedPreferences("com.example.apputviklingmappe2", Context.MODE_PRIVATE);
            String SMS_time = prefs.getString("SMS_Time", "");
            System.out.println(SMS_time);
            List<Bestilling> bestillinger = db.findAllBestillinger();
            ArrayList<Venn> venner = new ArrayList<>();
            SharedPreferences.Editor editor = prefs.edit();
            //editor.putString("SMS_Message","Hei "+)
            //editor.apply();
            String smsstdout = prefs.getString("SMS_Message", "");
            for(Bestilling bestilling : bestillinger) {
                venner.add(bestilling.venn);
            }
            if (currentTime.equals(SMS_time)) {
                if (venner.size() > 0) {
                    for (Venn venn : venner) {
                        System.out.println("Sender SMS til" + venn.getTelefon());
                        smsMan.sendTextMessage("+15" + venn.getTelefon(), null, smsstdout, null, null);
                    }
                }
            }
        }
        else{
            Toast.makeText(this, "SMS ikke tillatt", Toast.LENGTH_SHORT).show();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public String smsout(Venn venn) {
        return "Hei "+venn.getNavn()+"! \n Minner om bordbestillingen du er satt opp på på resta";
    }
}

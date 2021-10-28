package com.example.apputviklingmappe2;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import java.util.List;

public class SMSService extends Service {
    private static int MY_PERMISSIONS_REQUEST_SEND_SMS;
    private static int MY_PHONE_STATE_PERMISSION;
    SharedPreferences prefs;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        DBHandler db = new DBHandler(this);
        String currentTime = Preferanser.getCurrentTime();
        prefs = this.getSharedPreferences("com.example.apputviklingmappe2", Context.MODE_PRIVATE);
        Log.v("Heiheisann", "LOL SE HER : " + prefs.getBoolean("SMS_Boolean", false));
        if (MY_PERMISSIONS_REQUEST_SEND_SMS == PackageManager.PERMISSION_GRANTED &&
                MY_PHONE_STATE_PERMISSION ==
                        PackageManager.PERMISSION_GRANTED) {
            SmsManager smsMan = SmsManager.getDefault();
            SharedPreferences prefs = this.getSharedPreferences("com.example.apputviklingmappe2", Context.MODE_PRIVATE);
            String SMS_time = prefs.getString("SMS_Time", "");
            List<Bestilling> bestillinger = db.findAllBestillinger();
            String smsstdout = prefs.getString("SMS_Message", "");
            if (currentTime.equals(SMS_time)) {
                if (db.findNumberofuniqueBestillinger() > 0) {
                    for (Bestilling bestilling : bestillinger) {
                        smsMan.sendTextMessage("+15" + bestilling.getVenn().getTelefon(), null, smsstdout+"\n"+smsout(bestilling), null, null);
                    }
                }
            }
        }
        else{
            Toast.makeText(this, "SMS ikke tillatt", Toast.LENGTH_SHORT).show();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public String smsout (Bestilling bestilling) {
        return "Info om bestillingen:\nNavn: "+bestilling.venn.getNavn()+"\nRestaurant: "+bestilling.getRestaurant().getNavn()+"\nKlokken: "+bestilling.getTime();
    }
}

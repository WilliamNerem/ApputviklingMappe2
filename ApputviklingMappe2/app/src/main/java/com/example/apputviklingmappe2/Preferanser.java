package com.example.apputviklingmappe2;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;

import java.util.Calendar;

public class Preferanser extends AppCompatActivity {
    private ImageButton toolbarBack;
    private SwitchCompat settingsSwitch;
    private TimePickerDialog timePickerDialog;
    private Button timeButton;
    private ImageButton savePreferanse;
    int MY_PERMISSIONS_REQUEST_SEND_SMS;
    int MY_PHONE_STATE_PERMISSION;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferanser);
        prefs = this.getSharedPreferences("com.example.apputviklingmappe2", Context.MODE_PRIVATE);
        TextView tvTitle = (TextView) findViewById(R.id.title);
        tvTitle.setText(R.string.titlePreferanser);
        settingsSwitch = (SwitchCompat)findViewById(R.id.settingsSwitch);
        toolbarBack = (ImageButton) findViewById(R.id.back);
        timeButton = findViewById(R.id.time);
        savePreferanse = (ImageButton) findViewById(R.id.savePreferanse);
        timeButton.setText(getCurrentTime());

        if (settingsSwitch.isChecked()) {
            timeButton.setEnabled(true);
            timeButton.setText(getCurrentTime());
        }
        else {
            timeButton.setEnabled(false);
            timeButton.setText("--:--");
        }
        standardPrefs();

        toolbarButtons();
        buttons();
        initTimePicker();
    }

    private void toolbarButtons(){
        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Preferanser.this, MainActivity.class));
                finishAffinity();
            }
        });
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "ChannelName";
            String description = "ChannelDescription";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("22", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

        private void buttons() {
        settingsSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (settingsSwitch.isChecked()) {
                    Toast.makeText(getBaseContext(), "SMS varsling skrudd på", Toast.LENGTH_SHORT).show();
                    timeButton.setEnabled(true);
                    timeButton.setText(getCurrentTime());
                    editPrefs("SMS_Time",timeButton.getText().toString());
                    editPrefs("SMS_Boolean", "true");
                    SendSMS(view);
                    startService(view);
                }
                else {
                    Toast.makeText(getBaseContext(), "SMS varsling skrudd av", Toast.LENGTH_SHORT).show();
                    timeButton.setEnabled(false);
                    timeButton.setText("--:--");
                    editPrefs("SMS_Boolean", "false");
                    stopPeriodical(view);
                }
            }
        });
        savePreferanse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Innstillingene er lagret", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void editPrefs(String pref, String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(pref,value);
        editor.apply();
    }

    public void standardPrefs() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("SMS_Message", "Dette er den standarde SMS beskjeden du får");
        editor.putString("SMS_Time", "12:00");
        editor.putString("SMS_Boolean", "true");
        editor.apply();
    }

    public void startService(View v) {
        Intent intent = new Intent();
        intent.setAction("com.example.apputviklingmappe2.RestaurantBroadcastReceiver");
        sendBroadcast(intent);
    }

    public void stopPeriodical(View v) {
        Intent i = new Intent(this, RestaurantService.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, i, 0);
        AlarmManager alarm =
                (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarm!= null) {
            alarm.cancel(pintent);
        }

    }

    public void SendSMS(View v) {
        MY_PERMISSIONS_REQUEST_SEND_SMS = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS);
        MY_PHONE_STATE_PERMISSION = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE);
        if (MY_PERMISSIONS_REQUEST_SEND_SMS == PackageManager.PERMISSION_GRANTED &&
                MY_PHONE_STATE_PERMISSION ==
                        PackageManager.PERMISSION_GRANTED) {
            SmsManager smsMan = SmsManager.getDefault();
            String smsout = prefs.getString("SMS_Message", "");
            System.out.println(smsout);
            smsMan.sendTextMessage("+1555521556", null, smsout, null, null);
            Toast.makeText(this, "Har sendt sms", Toast.LENGTH_SHORT).show();
        }
        else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS,
                    Manifest.permission.READ_PHONE_STATE}, 0);
        }
    }

    static String getCurrentTime() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        return makeTimeString(hour, minute);
    }

    private void initTimePicker() {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                String time = makeTimeString(hour, minute);
                timeButton.setText(time);
            }
        };

        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        timePickerDialog = new TimePickerDialog(this, style, timeSetListener, hour, minute, true);
    }

    static String makeTimeString(int hour, int minute) {
        String strHour = Integer.toString(hour);
        String strMinute = Integer.toString(minute);
        if (hour < 10){
            strHour = "0" + strHour;
        }
        if (minute < 10){
            strMinute = "0" + strMinute;
        }
        return strHour + ":" + strMinute;
    }

    public void openTimePicker(View view) {
        timePickerDialog.show();
    }
}

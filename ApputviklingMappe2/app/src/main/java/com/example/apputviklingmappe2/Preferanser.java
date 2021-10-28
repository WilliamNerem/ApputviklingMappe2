package com.example.apputviklingmappe2;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
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
    private static int MY_PHONE_STATE_PERMISSION;
    private ImageButton toolbarBack;
    private SwitchCompat settingsSwitch;
    private TimePickerDialog timePickerDialog;
    private Button timeButton;
    private ImageButton savePreferanse;
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
        settingsSwitch.setChecked(prefs.getBoolean("SMS_Boolean", false));
        if (settingsSwitch.isChecked()) {
            timeButton.setEnabled(true);
            timeButton.setText(prefs.getString("SMS_Time", ""));
        }
        else {
            timeButton.setEnabled(false);
            timeButton.setText("--:--");
        }
        standardPrefs();
        toolbarButtons();
        buttons();
        initTimePicker();
        Log.v("Heiheisann", "LOL SE HER : " + prefs.getBoolean("SMS_Boolean", false));
        System.out.println(prefs.getBoolean("SMS_Boolean", false));
    }

    private boolean checkPermissions() {
        int MY_PERMISSIONS_REQUEST_SEND_SMS = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS);
        MY_PHONE_STATE_PERMISSION = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE);
        if (!(MY_PERMISSIONS_REQUEST_SEND_SMS == PackageManager.PERMISSION_GRANTED &&
                MY_PHONE_STATE_PERMISSION ==
                        PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions( this, new String[]{Manifest.permission.SEND_SMS,
                    Manifest.permission.READ_PHONE_STATE}, 0);
            return false;
        }
        return true;
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

        private void buttons() {
        settingsSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (settingsSwitch.isChecked()) {
                    if(!checkPermissions()) {
                        settingsSwitch.setChecked(false);
                    }
                    else {
                        Toast.makeText(getBaseContext(), "SMS varsling skrudd på", Toast.LENGTH_SHORT).show();
                        timeButton.setEnabled(true);
                        timeButton.setText(getCurrentTime());
                    }
                }
                else {
                    Toast.makeText(getBaseContext(), "SMS varsling skrudd av", Toast.LENGTH_SHORT).show();
                    timeButton.setEnabled(false);
                    timeButton.setText("--:--");
                }
            }
        });
        savePreferanse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (settingsSwitch.isChecked()) {
                    editPrefs("SMS_Time", timeButton.getText().toString());
                    editPrefs("SMS_Boolean", true);
                    startService(view);
                }
                else {
                    stopPeriodical(view);
                    editPrefs("SMS_Boolean", false);
                }
                Toast.makeText(getBaseContext(), "Innstillingene er lagret", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void editPrefs(String pref, Object value) {
        SharedPreferences.Editor editor = prefs.edit();
        if (value instanceof String){
            editor.putString(pref, (String) value);
        } else {
            editor.putBoolean(pref, (boolean) value);
        }
        editor.apply();
    }

    public void standardPrefs() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("SMS_Message", "Minner om bordbestilling i dag. Vel møtt!");
        editor.putString("SMS_Time", "12:00");
        editor.apply();
    }

    public void startService(View v) {
        Intent intent = new Intent(this, RestaurantBroadcastReceiver.class);
        sendBroadcast(intent);
    }

    public void stopPeriodical(View v) {
        Intent i = new Intent(this, RestaurantService.class);
        Intent iPer = new Intent(this, SMSService.class);
        // Intent iLaunch = new Intent(this, RestaurantBroadcastReceiver.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, i, 0);
        PendingIntent pIPER = PendingIntent.getService(this, 0, iPer, 0);
        // PendingIntent pILAUNCH = PendingIntent.getService(this,0,iLaunch,0);
        AlarmManager alarm =
                (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarm!= null) {
            alarm.cancel(pintent);
            alarm.cancel(pIPER);
          //  alarm.cancel(pILAUNCH);
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

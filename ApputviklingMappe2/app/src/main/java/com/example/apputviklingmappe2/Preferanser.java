package com.example.apputviklingmappe2;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import java.util.Calendar;

public class Preferanser extends AppCompatActivity {
    private ImageButton toolbarList;
    private SwitchCompat settingsSwitch;
    private TimePickerDialog timePickerDialog;
    private Button timeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferanser);
        settingsSwitch = (SwitchCompat)findViewById(R.id.settingsSwitch);
        toolbarList = (ImageButton) findViewById(R.id.list);
        timeButton = findViewById(R.id.time);
        timeButton.setText(getCurrentTime());

        if (settingsSwitch.isChecked()) {
            timeButton.setEnabled(true);
            timeButton.setText(getCurrentTime());
        }
        else {
            timeButton.setEnabled(false);
            timeButton.setText("--:--");
        }

        // toolbarButtons();
        buttons();
        initTimePicker();
    }

    /*private void toolbarButtons(){
        toolbarList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Preferanser.this, Preferanser.class));
            }
        });
    }
     */

    private void buttons() {
        settingsSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (settingsSwitch.isChecked()) {
                    Toast.makeText(getBaseContext(), "SMS varsling skrudd på", Toast.LENGTH_SHORT).show();
                    timeButton.setEnabled(true);
                    timeButton.setText(getCurrentTime());
                }
                else {
                    Toast.makeText(getBaseContext(), "SMS varsling skrudd av", Toast.LENGTH_SHORT).show();
                    timeButton.setEnabled(false);
                    timeButton.setText("--:--");
                }
            }
        });
    }

    private String getCurrentTime() {
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

    private String makeTimeString(int hour, int minute) {
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

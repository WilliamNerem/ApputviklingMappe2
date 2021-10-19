package com.example.apputviklingmappe2;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

public class BestillBord extends AppCompatActivity {

    private TimePickerDialog timePickerDialog;
    private Button timeButton;
    private Button friendsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bestill_bord);
        initTimePicker();
        timeButton = findViewById(R.id.choseTime);
        timeButton.setText(getCurrentTime());
        friendsButton = findViewById(R.id.chooseFriend);
        friendsButtonOnclick();
    }

    private void friendsButtonOnclick() {
        friendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friendsAlertDialog();
            }
        });
    }

    private void friendsAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(BestillBord.this);
        alertDialog.setTitle("Velg venner");
        String[] items = {"venn1", "venn2", "venn3", "venn4"};
        boolean[] checkedItems = {false, false, false, false};
        alertDialog.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                switch (which) {
                    case 0:
                        if(isChecked)
                            //Legg til i bestill bord databasen her
                            Toast.makeText(BestillBord.this, "Clicked on java", Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        if(isChecked)
                            Toast.makeText(BestillBord.this, "Clicked on android", Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        if(isChecked)
                            Toast.makeText(BestillBord.this, "Clicked on Data Structures", Toast.LENGTH_LONG).show();
                        break;
                    case 3:
                        if(isChecked)
                            Toast.makeText(BestillBord.this, "Clicked on HTML", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    private String getCurrentTime() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR);
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
        int hour = cal.get(Calendar.HOUR);
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

    public void openFriendCheckList(View view) {
    }
}

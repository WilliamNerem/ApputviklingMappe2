package com.example.apputviklingmappe2;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BestillBord extends AppCompatActivity {
    private TimePickerDialog timePickerDialog;
    private Button timeButton;
    private Button friendsButton;
    private Spinner restaurantSpinner;
    private ImageButton toolbarBack;
    private ImageButton toolbarAdd;
    private ImageView ivPreferanser;
    private DBHandler db;
    private String strAntallVennerValgt;
    private final ArrayList<Venn> chosenVenner= new ArrayList<>();
    private int antallVennerValgt = 0;
    static boolean[] checkedItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bestill_bord);
        TextView tvTitle = (TextView) findViewById(R.id.title);
        tvTitle.setText(R.string.titleBestillBord);
        initTimePicker();
        timeButton = findViewById(R.id.choseTime);
        timeButton.setText(getCurrentTime());
        friendsButton = findViewById(R.id.chooseFriend);
        restaurantSpinner = (Spinner) findViewById(R.id.chooseRestaurant);
        toolbarBack = (ImageButton) findViewById(R.id.back);
        toolbarAdd = (ImageButton) findViewById(R.id.list);
        ivPreferanser = findViewById(R.id.settings);
        strAntallVennerValgt = getString(R.string.hintAntallVenner, antallVennerValgt);
        friendsButton.setText(strAntallVennerValgt);
        checkedItems = new boolean[0];
        friendsButtonOnclick();
        db = new DBHandler(this);
        buttons();
        setSpinner();
    }

    private void buttons(){
        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BestillBord.this, MainActivity.class));
                finishAffinity();
            }
        });

        toolbarAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BestillBord.this, BestillBordList.class));
                finishAffinity();
            }
        });

        ivPreferanser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BestillBord.this, Preferanser.class));
            }
        });
    }

    public void addinDB(View v) {
        if (!(restaurantSpinner.getSelectedItem().toString().equals("Legg til restaurant") || (antallVennerValgt == 0))){
            String enRestaurant = restaurantSpinner.getSelectedItem().toString();
            Restaurant valgt = null;
            for(Restaurant res : db.findAllRestauranter()) {
                if(enRestaurant.equals(res.navn)) {
                    valgt = res;
                }
            }
            int bes_id_number = db.findNumberofuniqueBestillinger() + 1;
            for(Venn enVenn : chosenVenner) {
                Bestilling enBestilling = new Bestilling(bes_id_number, valgt, enVenn, timeButton.getText().toString());
                db.addBestilling(enBestilling);
            }
            chosenVenner.clear();
            Log.d("Legg inn: ", "legger til bestillinger");
            Toast.makeText(getBaseContext(),"Bestilling lagt til", Toast.LENGTH_SHORT).show();

            restaurantSpinner.setSelection(0);
            antallVennerValgt = 0;
            strAntallVennerValgt = getString(R.string.hintAntallVenner, antallVennerValgt);
            friendsButton.setText(strAntallVennerValgt);
            checkedItems = new boolean[0];
            timeButton.setText(getCurrentTime());
        } else {
            Toast.makeText(getBaseContext(),"Alle felt må fylles ut", Toast.LENGTH_SHORT).show();
        }
    }

    private void friendsButtonOnclick() {
        friendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friendsAlertDialog();
            }
        });
    }

    private void setSpinner() {
        List<Restaurant> restaurants;
        restaurants = db.findAllRestauranter();
        String[] items = new String[restaurants.size()+1];
        items[0] = "Legg til restaurant";
        for(int i=1 ; i <= restaurants.size();i++){
            items[i] = restaurants.get(i-1).getNavn();
        }
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, items) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        restaurantSpinner.setAdapter(adapter);
    }

    private void updateAntallVenner(){
        strAntallVennerValgt = getString(R.string.hintAntallVenner, antallVennerValgt);
        friendsButton.setText(strAntallVennerValgt);
    }

    private void friendsAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(BestillBord.this);
        alertDialog.setTitle("Velg venner");
        List<Venn> venner;
        venner = db.findAllVenner();
        String[] items = new String[venner.size()];
        boolean[] checkedItems = new boolean[venner.size()];
        if(BestillBord.checkedItems.length > 0) {
            checkedItems = BestillBord.checkedItems;
        }
        else {
            BestillBord.checkedItems = checkedItems;
        }
        for(int i=0 ; i< venner.size();i++){
            items[i] =venner.get(i).getNavn();
        }
        alertDialog.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                BestillBord.checkedItems[which] = isChecked;
                if(isChecked) {
                 chosenVenner.add(venner.get(which));
                    antallVennerValgt ++;
                    updateAntallVenner();
                }
                if(!isChecked) {
                    chosenVenner.remove(venner.get(which));
                    antallVennerValgt --;
                    updateAntallVenner();
                }
            }
        });
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        alertDialog.show();
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("tvAntallVennerValgt", friendsButton.getText().toString());
        outState.putBooleanArray("checkedItems", checkedItems);
        outState.putInt("antallVennerInt", antallVennerValgt);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        friendsButton.setText(savedInstanceState.getString("tvAntallVennerValgt"));
        checkedItems = savedInstanceState.getBooleanArray("checkedItems");
        antallVennerValgt = savedInstanceState.getInt("antallVennerInt");
    }

}

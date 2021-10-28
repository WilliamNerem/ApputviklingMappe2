package com.example.checkcontentprovider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public final static String PROVIDER = "com.example.apputviklingmappe2.RestaurantProvider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER + "/restaurants");
    static final String id = "id";
    static final String name = "name";
    static final String address = "address";
    static final String phone = "phone";
    static final String type = "type";
    EditText tvName;
    EditText tvAddress;
    EditText tvPhone;
    Spinner tvType;
    TextView visRestaurant;
    int newId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvName = (EditText) findViewById(R.id.name);
        tvAddress = (EditText) findViewById(R.id.address);
        tvPhone = (EditText) findViewById(R.id.phone);
        tvType = (Spinner) findViewById(R.id.type);
        Button leggtil = (Button) findViewById(R.id.leggtil);
        visRestaurant = (TextView) findViewById(R.id.vis);
        setSpinner();
        getId();
    }

    private void setSpinner() {
        String[] items = getResources().getStringArray(R.array.RestaurantTypes);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, items) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
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
        tvType.setAdapter(adapter);

        tvType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getId() {
        Cursor cur = getContentResolver().query(CONTENT_URI, null, null, null, null);
        if (cur != null) {
            if (cur.moveToFirst()) {
                do {
                    newId = Integer.parseInt(cur.getString(0));
                }
                while (cur.moveToNext());
                cur.close();
            }
        }
    }

    public boolean validation(EditText name, EditText address, EditText phone, Context context) {
        String strName = name.getText().toString();
        String strAddress = address.getText().toString();
        String strPhone = phone.getText().toString();

        if (strName.equals("") || strAddress.equals("") || strPhone.equals("")) {
            Toast.makeText(context, "Alle felt må fylles ut", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!strPhone.matches("^[0-9]+$")) {
            Toast.makeText(context, "Telefonnummer kan kun inneholde siffere", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public void leggtil(View v) {
        if (validation(tvName, tvAddress, tvPhone, v.getContext())) {
            ContentValues values = new ContentValues();
            String innName = tvName.getText().toString();
            String innAddress = tvAddress.getText().toString();
            String innPhone = tvPhone.getText().toString();
            String innType = tvType.getSelectedItem().toString();
            values.put(id, newId + 1);
            values.put(name, innName);
            values.put(address, innAddress);
            values.put(phone, innPhone);
            values.put(type, innType);
            Uri uri = getContentResolver().insert(CONTENT_URI, values);
            tvName.setText("");
            tvAddress.setText("");
            tvPhone.setText("");
            tvType.setSelection(0);
        }
    }

    public void visalle(View v) {
        String tekst;
        tekst = "";
        Cursor cur = getContentResolver().query(CONTENT_URI, null, null, null, null);
        if (cur != null) {
            if (cur.moveToFirst()) {
                do {
                    newId = Integer.parseInt(cur.getString(0));
                    tekst = tekst + "Restaurant " + (cur.getString(0)) + ":\nNavn: " + (cur.getString(1)) + "\nAdresse: " + (cur.getString(2)) + "\nTelefonnummer: " + (cur.getString(3)) + "\nType: " + (cur.getString(4)) + "\r\n\n";
                }
                while (cur.moveToNext());
                cur.close();
                visRestaurant.setText(tekst);
            }
        }
    }
}
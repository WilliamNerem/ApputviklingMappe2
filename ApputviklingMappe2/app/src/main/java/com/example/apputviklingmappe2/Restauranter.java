package com.example.apputviklingmappe2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;
import java.util.Objects;

public class Restauranter extends AppCompatActivity {
    EditText namein;
    EditText adressin;
    EditText phonein;
    Spinner typein;
    DBHandler db;

    private Spinner spinner;
    private ImageButton toolbarList;
    private ImageView ivPreferanser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restauranter);
        toolbarList = (ImageButton) findViewById(R.id.list);
        ivPreferanser = findViewById(R.id.settings);
        toolbarButtons();
        namein = (EditText) findViewById(R.id.restaurantName);
        adressin = (EditText) findViewById(R.id.restaurantAddress);
        phonein = (EditText) findViewById(R.id.restaurantPhone);
        typein = (Spinner) findViewById(R.id.restaurantType);
        setSpinner();
        db = new DBHandler(this);
    }

    private void toolbarButtons(){
        toolbarList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Restauranter.this, RestauranterList.class));
                finish();
            }
        });

        ivPreferanser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Restauranter.this, Preferanser.class));
            }
        });
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
        typein.setAdapter(adapter);

        typein.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    public void addinDB(View v) {
        Restaurant restaurant = new Restaurant(namein.getText().toString(), adressin.getText().toString(), phonein.getText().toString(), typein.getSelectedItem().toString());
        db.addRestaurant(restaurant);
        Log.d("Legg inn: ", "legger til restauranter");
        namein.setText("");
        adressin.setText("");
        phonein.setText("");
        typein.setSelection(0);
        Toast.makeText(getBaseContext(), "Restaurant lagt til", Toast.LENGTH_SHORT).show();
    }

    public void showallDB(View v) {
        String text = "";
        List<Restaurant> restauranter = db.findAllRestauranter();

        for (Restaurant restaurant : restauranter) {
            text = text + "Id: " + restaurant.get_ID() + ",Adresse: " +
                    restaurant.getAdresse() + " ,Navn: " +
                    restaurant.getNavn() + " ,Telefon: " +
                    restaurant.getTelefon() + " ,Type: " +
                    restaurant.getType();
            Log.d("Navn: ", text);
        }

    }

    public void deleteinDB(View v) {
        Long restaurantid = (Long.parseLong("1"));
        db.deleteRestaurant(restaurantid);
    }

    public void updateinDB(View v) {
        Restaurant restaurant = new Restaurant();
        restaurant.setNavn(namein.getText().toString());
        restaurant.setAdresse(adressin.getText().toString());
        restaurant.setTelefon(phonein.getText().toString());
        restaurant.setType(typein.getSelectedItem().toString());
        restaurant.set_ID(Long.parseLong("1"));
        db.updateRestaurant(restaurant);
    }
}

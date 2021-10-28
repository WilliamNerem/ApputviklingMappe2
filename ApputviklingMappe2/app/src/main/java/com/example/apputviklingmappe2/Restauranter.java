package com.example.apputviklingmappe2;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class Restauranter extends AppCompatActivity {
    private EditText namein;
    private EditText adressin;
    private EditText phonein;
    private Spinner typein;
    private DBHandler db;
    private ImageButton toolbarBack;
    private ImageButton toolbarList;
    private ImageView ivPreferanser;
    public final static String PROVIDER="com.example.apputviklingmappe2.RestaurantProvider";
    public static final Uri CONTENT_URI= Uri.parse("content://"+ PROVIDER + "/restaurants");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restauranter);
        TextView tvTitle = (TextView) findViewById(R.id.title);
        tvTitle.setText(R.string.titleRestauranter);
        toolbarBack = (ImageButton) findViewById(R.id.back);
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
        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Restauranter.this, MainActivity.class));
                finishAffinity();
            }
        });

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
        ContentValues resValues = new ContentValues();
        int id = db.findAllRestauranter().size();
        resValues.clear();
        resValues.put("id", id);
        resValues.put("name", restaurant.navn);
        resValues.put("address", restaurant.adresse);
        resValues.put("phone", restaurant.telefon);
        resValues.put("type", restaurant.type);
        Uri uri = getContentResolver().insert( CONTENT_URI, resValues);
        Log.d("Legg inn: ", "legger til restauranter");
        namein.setText("");
        adressin.setText("");
        phonein.setText("");
        typein.setSelection(0);
        Toast.makeText(getBaseContext(), "Restaurant lagt til", Toast.LENGTH_SHORT).show();
    }

    public void validation(View v){
        String strName = namein.getText().toString();
        String strAddress = adressin.getText().toString();
        String strPhone = phonein.getText().toString();
        String strType = typein.getSelectedItem().toString();

        if (strName.equals("") || strAddress.equals("") || strPhone.equals("") || strType.equals("Velg type restaurant")){
            Toast.makeText(getBaseContext(),"Alle felt må fylles ut", Toast.LENGTH_SHORT).show();
        } else if(!strPhone.matches("^[0-9]+$")){
            Toast.makeText(getBaseContext(),"Telefonnummer kan kun inneholde siffere", Toast.LENGTH_SHORT).show();
        } else {
            addinDB(v);
        }
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

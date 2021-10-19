package com.example.apputviklingmappe2;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;
import java.util.Objects;

public class Restauranter extends AppCompatActivity {
    Spinner spinner;
    EditText namein;
    EditText adressin;
    EditText phonein;
    Spinner typein;
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restauranter);
        namein = (EditText) findViewById(R.id.restaurantName);
        adressin = (EditText) findViewById(R.id.restaurantAddress);
        phonein = (EditText) findViewById(R.id.restaurantPhone);
        typein = (Spinner) findViewById(R.id.restaurantType);
        setSpinner();
        db = new DBHandler(this);
    }

    private void setSpinner() {
        String[] items = getResources().getStringArray(R.array.RestaurantTypes);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, items){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view;
                if(position == 0){
                    tv.setTextColor(Color.GRAY);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void addinDB(View v) {
        Restaurant restaurant = new Restaurant(namein.getText().toString(), adressin.getText().toString(), phonein.getText().toString(), typein.toString());
        db.addRestaurant(restaurant);
        Log.d("Legg inn: ", "legger til restauranter");
        namein.setText("");
        adressin.setText("");
        phonein.setText("");
        Toast.makeText(getBaseContext(),"Venn lagt til", Toast.LENGTH_SHORT).show();
    }

    public void showallDB(View v) {
        String text = "";
        List<Venn> venner = db.findAllVenner();

        for (Venn venn : venner) {
            text = text + "Id: " + venn.get_ID() + ",Navn: " +
                    venn.getNavn() + " ,Telefon: " +
                    venn.getTelefon();
            Log.d("Navn: ", text);
        }

    }

    public void deleteinDB(View v) {
        Long vennid = (Long.parseLong("1"));
        db.deleteVenn(vennid);
    }

    public void updateinDB(View v) {
        Venn venn = new Venn();
        venn.setNavn(namein.getText().toString());
        venn.setTelefon(phonein.getText().toString());
        venn.set_ID(Long.parseLong("1"));
        db.updateVenn(venn);
    }
}
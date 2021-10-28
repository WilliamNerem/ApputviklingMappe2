package com.example.apputviklingmappe2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Venner extends AppCompatActivity {
    private EditText namein;
    private EditText phonein;
    private DBHandler db;
    private ImageButton toolbarList;
    private ImageButton toolbarBack;
    private ImageView ivPreferanser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venner);
        TextView tvTitle = (TextView) findViewById(R.id.title);
        tvTitle.setText(R.string.titleVenner);
        namein = (EditText) findViewById(R.id.name);
        phonein = (EditText) findViewById(R.id.phone);
        db = new DBHandler(this);
        toolbarList = (ImageButton) findViewById(R.id.list);
        toolbarBack = (ImageButton) findViewById(R.id.back);
        ivPreferanser = findViewById(R.id.settings);
        toolbarButtons();
        db = new DBHandler(this);
    }

    private void toolbarButtons(){
        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Venner.this, MainActivity.class));
                finishAffinity();
            }
        });

        toolbarList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Venner.this, VennerList.class));
                finish();
            }
        });

        ivPreferanser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Venner.this, Preferanser.class));
            }
        });
    }


    public void addinDB(View v) {
        Venn venn = new Venn(namein.getText().toString(), phonein.getText().toString());
        db.addVenn(venn);
        Log.d("Legg inn: ", "legger til venner");
        namein.setText("");
        phonein.setText("");
        Toast.makeText(getBaseContext(),"Venn lagt til", Toast.LENGTH_SHORT).show();
    }

    public void validation(View v){
        String strName = namein.getText().toString();
        String strPhone = phonein.getText().toString();

        if (strName.equals("") || strPhone.equals("")){
            Toast.makeText(getBaseContext(),"Alle felt må fylles ut", Toast.LENGTH_SHORT).show();
        } else if(!strName.matches("^[A-Z][a-z-., ]+$")){
            Toast.makeText(getBaseContext(),"Navn må være gyldig med stor forbokstav", Toast.LENGTH_SHORT).show();
        } else if(!strPhone.matches("^[0-9]{8}$")){
            Toast.makeText(getBaseContext(),"Telefonnummer må være gyldig (8 siffer)", Toast.LENGTH_SHORT).show();
        } else {
            addinDB(v);
        }
    }
}
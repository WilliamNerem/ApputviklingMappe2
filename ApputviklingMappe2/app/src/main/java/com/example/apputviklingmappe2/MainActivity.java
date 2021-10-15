package com.example.apputviklingmappe2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Button tvButtonBestillBord;
    private Button tvButtonRestauranter;
    private Button tvButtonVenner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvButtonBestillBord = findViewById(R.id.button_bestill_bord);
        tvButtonRestauranter = findViewById(R.id.button_restauranter);
        tvButtonVenner = findViewById(R.id.button_venner);
        buttons();


    }

    private void buttons(){
        tvButtonBestillBord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BestillBord.class));
            }

        });

        tvButtonRestauranter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Restauranter.class));
            }
        });
        tvButtonVenner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Venner.class));
            }
        });
    }
}
package com.example.apputviklingmappe2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button tvButtonBestillBord;
    private Button tvButtonRestauranter;
    private Button tvButtonVenner;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        tvButtonBestillBord = findViewById(R.id.button_bestill_bord);
        tvButtonRestauranter = findViewById(R.id.button_restauranter);
        tvButtonVenner = findViewById(R.id.button_venner);
        buttons();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
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
package com.example.apputviklingmappe2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class Preferanser extends AppCompatActivity {
    private ImageButton toolbarList;
    private SwitchCompat settingsSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferanser);
        settingsSwitch = (SwitchCompat)findViewById(R.id.settingsSwitch);
        toolbarList = (ImageButton) findViewById(R.id.list);
        // toolbarButtons();
        buttons();
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
                }
                else {
                    Toast.makeText(getBaseContext(), "SMS varsling skrudd av", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

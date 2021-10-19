package com.example.apputviklingmappe2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;
import java.util.Objects;

public class Venner extends AppCompatActivity {
    EditText namein;
    EditText phonein;
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venner);
        namein = (EditText) findViewById(R.id.name);
        phonein = (EditText) findViewById(R.id.phone);
        db = new DBHandler(this);
    }

    public void addinDB(View v) {
        Venn venn = new Venn(namein.getText().toString(), phonein.getText().toString());
        db.addVenn(venn);
        Log.d("Legg inn: ", "legger til venner");
        namein.setText("");
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
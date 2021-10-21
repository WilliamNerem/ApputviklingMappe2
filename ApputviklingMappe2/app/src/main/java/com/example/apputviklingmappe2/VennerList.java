package com.example.apputviklingmappe2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class VennerList extends AppCompatActivity {

    private ImageButton toolbarList;
    private ImageButton buttonEditVenn;
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venner_list);

        toolbarList = (ImageButton) findViewById(R.id.list);
        buttonEditVenn = (ImageButton) findViewById(R.id.buttonEdit);

        ListView listView = (ListView) findViewById(R.id.list_view);

        db = new DBHandler(this);
        List<Venn> vennList = db.findAllVenner();

        VennListAdapter adapter = new VennListAdapter(this, R.layout.list_item, vennList);
        listView.setAdapter(adapter);


        toolbarButtons();
    }

    private void toolbarButtons(){
        toolbarList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VennerList.this, Venner.class));
            }
        });
    }

    private void editButton(){
        buttonEditVenn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VennerList.this, MainActivity.class));
            }
        });
    }
}


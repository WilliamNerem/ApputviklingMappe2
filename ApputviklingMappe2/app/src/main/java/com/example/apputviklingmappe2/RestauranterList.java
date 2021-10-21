package com.example.apputviklingmappe2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RestauranterList extends AppCompatActivity {

    private ImageButton toolbarList;
    private ImageView ivPreferanser;
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restauranter_list);

        toolbarList = (ImageButton) findViewById(R.id.list);
        ivPreferanser = findViewById(R.id.settings);

        ListView listView = (ListView) findViewById(R.id.list_view_restauranter);

        db = new DBHandler(this);
        List<Restaurant> restaurantList = db.findAllRestauranter();

        RestaurantListAdapter adapter = new RestaurantListAdapter(this, R.layout.list_item_restauranter, restaurantList);
        listView.setAdapter(adapter);


        toolbarButtons();
    }

    private void toolbarButtons(){
        toolbarList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RestauranterList.this, Restauranter.class));
                finish();
            }
        });

        ivPreferanser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RestauranterList.this, Preferanser.class));
            }
        });
    }
}
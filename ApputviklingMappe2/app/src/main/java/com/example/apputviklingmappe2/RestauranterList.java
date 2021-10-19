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

public class RestauranterList extends AppCompatActivity {

    private ImageButton toolbarList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restauranter_list);

        toolbarList = (ImageButton) findViewById(R.id.list);

        ListView listView = (ListView) findViewById(R.id.list_view_restauranter);

        Restaurant mcDonalds = new Restaurant("McDonalds", "Oslogata1", "12341234", "Amerikansk" );
        Restaurant mirabel = new Restaurant("Mirabel", "Tjuvholmen3", "43214321", "Italiensk");

        ArrayList<Restaurant> restaurantList = new ArrayList<>();
        restaurantList.add(mcDonalds);
        restaurantList.add(mirabel);

        RestaurantListAdapter adapter = new RestaurantListAdapter(this, R.layout.list_item_restauranter, restaurantList);
        listView.setAdapter(adapter);


        toolbarButtons();
    }

    private void toolbarButtons(){
        toolbarList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RestauranterList.this, Restauranter.class));
            }
        });
    }
}
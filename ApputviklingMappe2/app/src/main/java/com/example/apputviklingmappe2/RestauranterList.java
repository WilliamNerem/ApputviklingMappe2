package com.example.apputviklingmappe2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class RestauranterList extends AppCompatActivity {
    private ImageButton toolbarList;
    private ImageButton toolbarBack;
    private ImageView ivPreferanser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restauranter_list);
        TextView tvTitle = (TextView) findViewById(R.id.title);
        tvTitle.setText(R.string.titleRestauranterList);

        toolbarList = (ImageButton) findViewById(R.id.list);
        toolbarBack = (ImageButton) findViewById(R.id.back);
        ivPreferanser = findViewById(R.id.settings);

        ListView listView = (ListView) findViewById(R.id.list_view_restauranter);

        DBHandler db = new DBHandler(this);
        List<Restaurant> restaurantList = db.findAllRestauranter();

        RestaurantListAdapter adapter = new RestaurantListAdapter(this, R.layout.list_item_restauranter, restaurantList);
        listView.setAdapter(adapter);


        toolbarButtons();
    }

    private void toolbarButtons(){
        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RestauranterList.this, MainActivity.class));
                finishAffinity();
            }
        });

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
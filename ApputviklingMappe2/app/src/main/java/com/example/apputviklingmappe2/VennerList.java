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

public class VennerList extends AppCompatActivity {
    private ImageButton toolbarBack;
    private ImageButton toolbarList;
    private ImageButton buttonEditVenn;
    private ImageView ivPreferanser;
    private DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venner_list);
        TextView tvTitle = (TextView) findViewById(R.id.title);
        tvTitle.setText(R.string.titleVennerList);

        toolbarList = (ImageButton) findViewById(R.id.list);
        toolbarBack = (ImageButton) findViewById(R.id.back);
        buttonEditVenn = (ImageButton) findViewById(R.id.buttonEdit);
        ivPreferanser = findViewById(R.id.settings);

        ListView listView = (ListView) findViewById(R.id.list_view);

        db = new DBHandler(this);
        List<Venn> vennList = db.findAllVenner();

        VennListAdapter adapter = new VennListAdapter(this, R.layout.list_item, vennList);
        listView.setAdapter(adapter);


        toolbarButtons();
    }

    private void toolbarButtons(){
        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VennerList.this, MainActivity.class));
                finishAffinity();
            }
        });

        toolbarList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VennerList.this, Venner.class));
                finish();
            }
        });

        ivPreferanser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VennerList.this, Preferanser.class));
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


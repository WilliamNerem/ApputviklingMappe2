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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venner_list);

        toolbarList = (ImageButton) findViewById(R.id.list);

        ListView listView = (ListView) findViewById(R.id.list_view);

        Venn william = new Venn(0, "William", "12341234");
        Venn martin = new Venn(1, "Martin", "43214321");

        ArrayList<Venn> vennList = new ArrayList<>();
        vennList.add(william);
        vennList.add(martin);

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
}
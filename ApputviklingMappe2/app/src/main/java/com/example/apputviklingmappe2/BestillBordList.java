package com.example.apputviklingmappe2;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.List;

public class BestillBordList extends AppCompatActivity {
    private ImageButton toolbarAdd;
    private ImageButton toolbarBack;
    private ImageView ivPreferanser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bestill_bord_list);
        TextView tvTitle = (TextView) findViewById(R.id.title);
        tvTitle.setText(R.string.titleBestillBordList);

        toolbarAdd = (ImageButton) findViewById(R.id.add);
        toolbarBack = (ImageButton) findViewById(R.id.back);
        ivPreferanser = findViewById(R.id.settings);

        ListView listView = (ListView) findViewById(R.id.list_view_bestillinger);

        DBHandler db = new DBHandler(this);
        List<Bestilling> bestillingList = db.findAllBestillinger();
        List<Bestilling> uniqueBestillingList = new ArrayList<>();
        long curId;
        long lastId = 0;
        StringBuilder venner = new StringBuilder();
        if (bestillingList.size() > 0){
            for (Bestilling bestilling : bestillingList) {
                venner.append(bestilling.venn.getNavn()).append(", ");
                curId = bestilling.get_ID();
                if (curId != lastId) {
                    lastId = curId;
                    uniqueBestillingList.add(bestilling);
                    venner.setLength(0);
                }
            }
            BestillBordListAdapter adapter = new BestillBordListAdapter(this, R.layout.list_item_bestillinger, uniqueBestillingList);
            listView.setAdapter(adapter);
        }



        toolbarButtons();
    }

    private void toolbarButtons(){
        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BestillBordList.this, MainActivity.class));
                finishAffinity();
            }
        });

        toolbarAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BestillBordList.this, BestillBord.class));
                finish();
            }
        });

        ivPreferanser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BestillBordList.this, Preferanser.class));
            }
        });
    }
}
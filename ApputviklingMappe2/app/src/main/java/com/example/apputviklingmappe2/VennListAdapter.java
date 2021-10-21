package com.example.apputviklingmappe2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class VennListAdapter extends ArrayAdapter<Venn> {
    private final static String TAG = "VennListAdapter";

    private final Context mContext;
    int mResource;

    private ImageButton buttonEditVenn;

    public VennListAdapter(Context context, int resource, ArrayList<Venn> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        buttonEditVenn = (ImageButton) convertView.findViewById(R.id.buttonEdit);

        long id = getItem(position).get_ID();
        String name = getItem(position).getNavn();
        String phone = getItem(position).getTelefon();
        Venn venn = new Venn(id, name, phone);

        TextView tvId = (TextView) convertView.findViewById(R.id.itemFriend);
        TextView tvName = (TextView) convertView.findViewById(R.id.itemName);
        TextView tvPhone = (TextView) convertView.findViewById(R.id.itemPhone);

        String strId = String.valueOf(id);
        tvId.setText(strId);
        tvName.setText(name);
        tvPhone.setText(phone);

        buttonEditVenn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());
                alertDialog.setTitle("Velg venner");
            }
        });

        return convertView;
    }
}

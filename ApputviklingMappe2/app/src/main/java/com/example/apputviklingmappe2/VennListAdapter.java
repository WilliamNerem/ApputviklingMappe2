package com.example.apputviklingmappe2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class VennListAdapter extends ArrayAdapter<Venn> {
    private final static String TAG = "VennListAdapter";

    private final Context mContext;
    int mResource;
    DBHandler db;
    List<Venn> vennList;

    private ImageButton buttonEditVenn;
    private ImageButton buttonDeleteVenn;

    public VennListAdapter(Context context, int resource, List<Venn> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        vennList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        View currentView = convertView;

        buttonEditVenn = (ImageButton) convertView.findViewById(R.id.buttonEdit);
        buttonDeleteVenn = (ImageButton) convertView.findViewById(R.id.buttonDelete);

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
                AlertDialog created = buildAlertDialog(currentView, id, tvName, tvPhone);
                created.show();
            }
        });

        buttonDeleteVenn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = new DBHandler(mContext);
                db.deleteVenn(id);
                vennList.clear();
                vennList.addAll(db.findAllVenner());
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    public boolean validation(EditText name, EditText phone, Context context){
        String strName = name.getText().toString();
        String strPhone = phone.getText().toString();

        if (strName.equals("") || strPhone.equals("")){
            Toast.makeText(context,"Venn er ikke endret.\nAlle felt må fylles ut", Toast.LENGTH_SHORT).show();
            return false;
        } else if(!strName.matches("^[A-Z][a-z-., ]+$")){
            Toast.makeText(context,"Venn er ikke endret.\nNavn må være gyldig med stor forbokstav", Toast.LENGTH_SHORT).show();
            return false;
        } else if(!strPhone.matches("^[0-9]{8}$")){
            Toast.makeText(context,"Venn er ikke endret.\nTelefonnummer må være gyldig", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private AlertDialog buildAlertDialog(View view, long id, TextView tvName, TextView tvPhone){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());
        alertDialog.setView(R.layout.alert_edit_venn);

        LayoutInflater alertInflater = LayoutInflater.from(view.getContext());
        View alertConvertView = alertInflater.inflate(R.layout.alert_edit_venn, null);

        EditText editName = alertConvertView.findViewById(R.id.name);
        EditText editPhone = alertConvertView.findViewById(R.id.phone);

        alertDialog.setNegativeButton("Avbryt", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });

        alertDialog.setPositiveButton("Lagre", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (validation(editName, editPhone, alertConvertView.getContext())){
                    db = new DBHandler(alertConvertView.getContext());
                    Venn enVenn = db.findVenn(id);
                    enVenn.setNavn(editName.getText().toString());
                    enVenn.setTelefon(editPhone.getText().toString());
                    db.updateVenn(enVenn);
                    vennList.clear();
                    vennList.addAll(db.findAllVenner());
                    notifyDataSetChanged();
                }
            }
        });

        alertDialog.setView(alertConvertView);

        editName.setText(tvName.getText());
        editPhone.setText(tvPhone.getText());

        return alertDialog.create();

    }
}

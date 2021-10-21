package com.example.apputviklingmappe2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class RestaurantListAdapter extends ArrayAdapter<Restaurant> {
    private final static String TAG = "RestaurantListAdapter";

    private final Context mContext;
    int mResource;
    DBHandler db;
    List<Restaurant> listRestaurant;
    private ImageButton buttonEditRestaurant;
    private ImageButton buttonDeleteRestaurant;

    public RestaurantListAdapter(Context context, int resource, List<Restaurant> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        listRestaurant = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        View currentView = convertView;

        buttonEditRestaurant = (ImageButton) convertView.findViewById(R.id.buttonEditRestaurant);
        buttonDeleteRestaurant = (ImageButton) convertView.findViewById(R.id.buttonDeleteRestaurant);

        long id = getItem(position).get_ID();
        String name = getItem(position).getNavn();
        String address = getItem(position).getAdresse();
        String phone = getItem(position).getTelefon();
        String type = getItem(position).getType();
        Restaurant restaurant = new Restaurant(name, address, phone, type);

        TextView tvName = (TextView) convertView.findViewById(R.id.itemRestaurantName);
        TextView tvAddress = (TextView) convertView.findViewById(R.id.itemRestaurantAddress);
        TextView tvPhone = (TextView) convertView.findViewById(R.id.itemRestaurantPhone);
        TextView tvType = (TextView) convertView.findViewById(R.id.itemRestaurantType);

        tvName.setText(name);
        tvAddress.setText(address);
        tvPhone.setText(phone);
        tvType.setText(type);


        buttonEditRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog created = buildAlertDialog(currentView, id, tvName, tvAddress, tvPhone, tvType);
                created.show();
            }
        });

        buttonDeleteRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = new DBHandler(mContext);
                db.deleteRestaurant(id);
                listRestaurant.clear();
                listRestaurant.addAll(db.findAllRestauranter());
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    private AlertDialog buildAlertDialog(View view, long id, TextView tvName, TextView tvAddress, TextView tvPhone, TextView tvType){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());
        alertDialog.setView(R.layout.alert_edit_restaurant);

        LayoutInflater alertInflater = LayoutInflater.from(view.getContext());
        View alertConvertView = alertInflater.inflate(R.layout.alert_edit_restaurant, null);

        alertDialog.setNegativeButton("Avbryt", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });

        alertDialog.setPositiveButton("Lagre", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                EditText editName = alertConvertView.findViewById(R.id.name);
                EditText editAddress = alertConvertView.findViewById(R.id.address);
                EditText editPhone = alertConvertView.findViewById(R.id.phone);
                Spinner editType = alertConvertView.findViewById(R.id.type);

                db = new DBHandler(alertConvertView.getContext());
                Restaurant enRestaurant = db.findRestaurant(id);
                enRestaurant.setNavn(editName.getText().toString());
                enRestaurant.setAdresse(editAddress.getText().toString());
                enRestaurant.setTelefon(editPhone.getText().toString());
                enRestaurant.setType(editType.getSelectedItem().toString());
                db.updateRestaurant(enRestaurant);
                listRestaurant.clear();
                listRestaurant.addAll(db.findAllRestauranter());
                notifyDataSetChanged();
            }
        });

        alertDialog.setView(alertConvertView);

        EditText editName = alertConvertView.findViewById(R.id.name);
        EditText editAddress = alertConvertView.findViewById(R.id.address);
        EditText editPhone = alertConvertView.findViewById(R.id.phone);
        Spinner editType = alertConvertView.findViewById(R.id.type);
        editName.setText(tvName.getText());
        editAddress.setText(tvAddress.getText());
        editPhone.setText(tvPhone.getText());

        setSpinner(alertConvertView, editType);

        for (int i = 0; i<editType.getCount(); i++){
            if(editType.getItemAtPosition(i).toString().contentEquals(tvType.getText())){
                editType.setSelection(i);
            }
        }

        return alertDialog.create();

    }

    private void setSpinner(View v, Spinner spinner) { //evt context
        String[] items = v.getResources().getStringArray(R.array.RestaurantTypes);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(mContext, android.R.layout.simple_spinner_item, items) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}

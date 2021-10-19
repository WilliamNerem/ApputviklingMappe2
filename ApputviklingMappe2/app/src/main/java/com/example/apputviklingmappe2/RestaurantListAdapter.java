package com.example.apputviklingmappe2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class RestaurantListAdapter extends ArrayAdapter<Restaurant> {
    private final static String TAG = "RestaurantListAdapter";

    private final Context mContext;
    int mResource;

    public RestaurantListAdapter(Context context, int resource, ArrayList<Restaurant> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

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

        return convertView;
    }
}

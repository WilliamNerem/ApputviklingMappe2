package com.example.apputviklingmappe2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class BestillBordListAdapter extends ArrayAdapter<Bestilling> {
    private final static String TAG = "BestillBordListAdapter";
    private final Context mContext;
    private final int mResource;
    private final List<Bestilling> listBestilling;
    private List<Bestilling> allBestillingerList = new ArrayList<>();
    private DBHandler db;

    public BestillBordListAdapter(Context context, int resource, List<Bestilling> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        listBestilling = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        db = new DBHandler(convertView.getContext());
        allBestillingerList = db.findAllBestillinger();
        long id = getItem(position).get_ID();
        String restaurant = getItem(position).getRestaurant().getNavn();
        StringBuilder friend = new StringBuilder();
        for (Bestilling bes : allBestillingerList){
            if(id == bes._ID){
                friend.append(bes.getVenn().getNavn());
                friend.append(", ");
            }
        }
        friend.setLength(friend.length() - 2);
        String time = getItem(position).getTime();

        TextView tvId = (TextView) convertView.findViewById(R.id.itemBestilling);
        TextView tvRestaurant = (TextView) convertView.findViewById(R.id.itemBestillingRestaurant);
        TextView tvFriend = (TextView) convertView.findViewById(R.id.itemBestillingFriend);
        TextView tvTime = (TextView) convertView.findViewById(R.id.itemBestillingTime);

        String strId = String.valueOf(id);
        tvId.setText(strId);
        tvRestaurant.setText(restaurant);
        tvFriend.setText(friend.toString());
        tvTime.setText(time);

        return convertView;
    }
}

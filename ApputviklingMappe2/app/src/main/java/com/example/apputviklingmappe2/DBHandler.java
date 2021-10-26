package com.example.apputviklingmappe2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    static String TABLE_VENNER = "Venner";
    static String KEY_ID = "_ID";
    static String KEY_NAME = "Navn";
    static String KEY_PH_NO = "Telefon";
    static int DATABASE_VERSION = 8;
    static String DATABASE_NAME = "Mappe_2_tabeller";
    static String TABLE_RESTAURANTER = "Restauranter";
    static String RES_KEY_ID = "_ID";
    static String RES_KEY_NAME = "Navn";
    static String RES_KEY_ADRESS = "Adresse";
    static String RES_KEY_PH_NO = "Telefon";
    static String RES_KEY_TYPE = "Type";
    static String TABLE_BESTILLINGER = "Bestillinger";
    static String BES_KEY_ID = "_ID";
    static String BES_VENN = "Venner";
    static String BES_RES = "Restaurant";
    static String BES_TIME = "Tidspunkt";


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLEVenn = "CREATE TABLE " + TABLE_VENNER + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT," + KEY_PH_NO + " TEXT" + ")";
        String CREATE_TABLERestaurant = "CREATE TABLE " + TABLE_RESTAURANTER + "(" + RES_KEY_ID + " INTEGER PRIMARY KEY, " + RES_KEY_NAME + " TEXT," + RES_KEY_ADRESS + " TEXT," + RES_KEY_PH_NO + " TEXT," + RES_KEY_TYPE + " TEXT" + ")";
        String CREATE_TABLEBestillinger = "CREATE TABLE " + TABLE_BESTILLINGER + "(" + BES_KEY_ID + " INTEGER  , " + BES_RES + " TEXT," + BES_VENN + " INTEGER ," + BES_TIME + " TEXT, " + "PRIMARY KEY ("+BES_KEY_ID+", "+BES_VENN+"));";
        Log.d("SQL", CREATE_TABLEVenn);
        Log.d("SQL", CREATE_TABLERestaurant);
        Log.d("SQL", CREATE_TABLEBestillinger);
        db.execSQL(CREATE_TABLEVenn);
        db.execSQL(CREATE_TABLERestaurant);
        db.execSQL(CREATE_TABLEBestillinger);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VENNER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANTER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BESTILLINGER);
        onCreate(db);
    }

    public void addVenn(Venn venn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, venn.getNavn());
        values.put(KEY_PH_NO, venn.getTelefon());
        db.insert(TABLE_VENNER, null, values);
        db.close();
    }

    public void addRestaurant(Restaurant restaurant) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RES_KEY_NAME, restaurant.getNavn());
        values.put(RES_KEY_ADRESS, restaurant.getAdresse());
        values.put(RES_KEY_PH_NO, restaurant.getTelefon());
        values.put(RES_KEY_TYPE, restaurant.getType());
        db.insert(TABLE_RESTAURANTER, null, values);
        db.close();
    }

    public void addBestilling(Bestilling bestilling) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BES_KEY_ID, bestilling.get_ID());
        values.put(BES_RES, bestilling.getRestaurant().getNavn());
        values.put(BES_VENN, bestilling.getVenn().get_ID());
        values.put(BES_TIME, bestilling.getTime());
        db.insert(TABLE_BESTILLINGER, null, values);
        db.close();
    }

    public int findNumberofuniqueBestillinger() {
        String sql = "SELECT MAX(_ID) as BES_KEY_ID FROM Bestillinger";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        int maxid = cursor.getInt(cursor.getColumnIndexOrThrow("BES_KEY_ID"));
        cursor.close();
        db.close();
        return maxid;
    }
    public List<Venn> findAllVenner() {
        List<Venn> vennList = new ArrayList<Venn>();
        String selectQuery = "SELECT * FROM " + TABLE_VENNER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Venn venn = new Venn();
                venn.set_ID(cursor.getLong(0));
                venn.setNavn(cursor.getString(1));
                venn.setTelefon(cursor.getString(2));
                vennList.add(venn);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return vennList;
    }

    public List<Restaurant> findAllRestauranter() {
        List<Restaurant> restaurantList = new ArrayList<Restaurant>();
        String selectQuery = "SELECT * FROM " + TABLE_RESTAURANTER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Restaurant restaurant = new Restaurant();
                restaurant.set_ID(cursor.getLong(0));
                restaurant.setNavn(cursor.getString(1));
                restaurant.setAdresse(cursor.getString(2));
                restaurant.setTelefon(cursor.getString(3));
                restaurant.setType(cursor.getString(4));
                restaurantList.add(restaurant);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return restaurantList;
    }

    public List<Bestilling> findAllBestillinger() {
        List<Bestilling> bestillingList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_BESTILLINGER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Bestilling bestilling = new Bestilling();
                bestilling.set_ID(cursor.getLong(0));
                Restaurant restaurant = new Restaurant();
                restaurant.setNavn(cursor.getString(1));
                bestilling.setRestaurant(restaurant);
                Venn venn = findVenn(cursor.getLong(2));
                bestilling.setVenn(venn);
                bestilling.setTime(cursor.getString(3));
                bestillingList.add(bestilling);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return bestillingList;
    }

    public void deleteVenn(Long in_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_VENNER, KEY_ID + " =? ",

                new String[]{Long.toString(in_id)});
        db.close();
    }

    public void deleteRestaurant(Long in_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RESTAURANTER, RES_KEY_ID + " =? ",

                new String[]{Long.toString(in_id)});
        db.close();
    }

    public void deleteBestilling(Long bes_id, Long venn_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BESTILLINGER, BES_KEY_ID + " =? AND " + KEY_ID + "=?",

                new String[]{Long.toString(bes_id), Long.toString(venn_id)});
        db.close();
    }

    public int updateVenn(Venn venn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, venn.getNavn());
        values.put(KEY_PH_NO, venn.getTelefon());
        int changed = db.update(TABLE_VENNER, values, KEY_ID + "= ?",
                new String[]{String.valueOf(venn.get_ID())});
        db.close();
        return changed;
    }

    public int updateRestaurant(Restaurant restaurant) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RES_KEY_NAME, restaurant.getNavn());
        values.put(RES_KEY_ADRESS, restaurant.getAdresse());
        values.put(RES_KEY_PH_NO, restaurant.getTelefon());
        values.put(RES_KEY_TYPE, restaurant.getType());
        int changed = db.update(TABLE_RESTAURANTER, values, RES_KEY_ID + "= ?",
                new String[]{String.valueOf(restaurant.get_ID())});
        db.close();
        return changed;
    }

    public Venn findVenn(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_VENNER, new String[]{
                        KEY_ID, KEY_NAME, KEY_PH_NO}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        Venn venn = new
                Venn(Long.parseLong(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        cursor.close();
        db.close();
        return venn;
    }

    public Restaurant findRestaurant(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RESTAURANTER, new String[]{
                        RES_KEY_ID, RES_KEY_NAME, RES_KEY_ADRESS, RES_KEY_PH_NO, RES_KEY_TYPE}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        Restaurant restaurant = new
                Restaurant(Long.parseLong(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4));
        cursor.close();
        db.close();
        return restaurant;
    }
}

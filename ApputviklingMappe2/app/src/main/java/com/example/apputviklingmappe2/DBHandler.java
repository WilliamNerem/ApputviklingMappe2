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
    static int DATABASE_VERSION = 1;
    static String DATABASE_NAME = "Mappe_2_tabeller";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_VENNER + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT," + KEY_PH_NO + " TEXT" + ")";
        Log.d("SQL", CREATE_TABLE);
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VENNER);
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

    public void deleteVenn(Long in_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_VENNER, KEY_ID + " =? ",

                new String[]{Long.toString(in_id)});
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

    public Venn findVenn(int id) {
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
}

package com.example.apputviklingmappe2;

public class Bestilling {
    long _ID;
    Restaurant restaurant;
    Venn venn;
    String time;

    public Bestilling() {}

    public Bestilling(Restaurant restaurant, Venn venn, String time) {
        this.restaurant = restaurant;
        this.venn = venn;
        this.time = time;
    }

    public Bestilling(long _ID, Restaurant restaurant, Venn venn, String time) {
        this._ID = _ID;
        this.restaurant = restaurant;
        this.venn = venn;
        this.time = time;
    }

    public long get_ID() {
        return _ID;
    }

    public void set_ID(long _ID) {
        this._ID = _ID;
    }

    public Venn getVenn() {
        return venn;
    }

    public void setVenn(Venn venn) {
        this.venn = venn;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

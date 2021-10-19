package com.example.apputviklingmappe2;

public class Restaurant {
    long _ID;
    String navn;
    String adresse;
    String telefon;
    String type;

    public Restaurant() {}

    public Restaurant(String navn, String adresse, String telefon, String type) {
        this.navn = navn;
        this.adresse = adresse;
        this.telefon = telefon;
        this.type = type;
    }

    public Restaurant(long _ID, String navn, String adresse, String telefon, String type) {
        this._ID = _ID;
        this.navn = navn;
        this.adresse = adresse;
        this.telefon = telefon;
        this.type = type;
    }

    public long get_ID() {
        return _ID;
    }

    public void set_ID(long _ID) {
        this._ID = _ID;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

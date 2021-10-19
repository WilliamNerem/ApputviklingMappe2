package com.example.apputviklingmappe2;

public class Venn {
    long _ID;
    String navn;
    String telefon;

    public Venn() {}

    public Venn(String navn, String telefon) {
        this.navn = navn;
        this.telefon = telefon;
    }

    public Venn(long _ID, String navn, String telefon) {
        this._ID = _ID;
        this.navn = navn;
        this.telefon = telefon;
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

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
}

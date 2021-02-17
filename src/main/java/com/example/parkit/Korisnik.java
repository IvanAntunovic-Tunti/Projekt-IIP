package com.example.parkit;

public class Korisnik {
    public String Ime;
    public String Prezime;
    public String Adresa;

    public String getIme() {
        return Ime;
    }

    public void setIme(String ime) {
        Ime = ime;
    }

    public String getPrezime() {
        return Prezime;
    }

    public void setPrezime(String prezime) {
        Prezime = prezime;
    }

    public String getAdresa() {
        return Adresa;
    }

    public void setAdresa(String adresa) {
        Adresa = adresa;
    }

    public String getDatum_rodjenja() {
        return Datum_rodjenja;
    }

    public void setDatum_rodjenja(String datum_rodjenja) {
        Datum_rodjenja = datum_rodjenja;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String Datum_rodjenja;
    public String username;
    public String lozinka;


    public Korisnik() {
    }
}

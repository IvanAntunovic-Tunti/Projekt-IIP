package com.example.parkit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper  extends SQLiteOpenHelper {

    public DbHelper( Context context ) {
        super(context,"Projekt_ParkIT.db",null,1  );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table User(id integer primary key autoincrement,username TEXT UNIQUE,lozinka TEXT,ime TEXT, prezime TEXT,adresa_stanovanja TEXT,datum_rodjenja TEXT,pravo INT)");
        db.execSQL("create table Parking(id integer primary key autoincrement,username TEXT, registracija TEXT,broj_sati INT,ukupna_cijena REAL,vrijeme_pocetka TEXT,vrijeme_zavrsetka TEXT,zona TEXT )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Boolean insert_korisnike(String ime, String prezime, String adresa,String datum,String username,String lozinka){

        SQLiteDatabase DB= this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username",username);
        cv.put("lozinka",lozinka);
        cv.put("ime",ime);
        cv.put("prezime",prezime);
        cv.put("adresa_stanovanja",adresa);
        cv.put("datum_rodjenja",datum);
        cv.put("pravo",0);
        long result=DB.insert("User",null,cv);
        if (result==1) return false;

        else return true;
    }

    public Boolean insert_kartu(String username, String registracija, int broj_sati, float   ukupna_cijena, String vrijeme_pocetka, String vrijeme_zavrsetka,String Zona){

        SQLiteDatabase DB= this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username",username);
        cv.put("registracija",registracija);
        cv.put("broj_sati",broj_sati);
        cv.put("ukupna_cijena",ukupna_cijena);
        cv.put("vrijeme_pocetka", vrijeme_pocetka);
        cv.put("vrijeme_zavrsetka", vrijeme_zavrsetka);
        cv.put("Zona", Zona);

        long result=DB.insert("Parking",null,cv);
        if (result==1) return false;

        else return true;

    }






    public Boolean sign_in_korisnik(String username, String lozinka) {

        SQLiteDatabase DB = this.getWritableDatabase();


        Cursor brojac = DB.rawQuery("SELECT * FROM User where username=? and lozinka =? and pravo =0", new String[]{username, lozinka});
        if (brojac.getCount() == 1) {

            return true;
        }
        else return  false;

    }

    public String ucitaj_registracija(String username) {

        SQLiteDatabase DB = this.getWritableDatabase();
        String Registracija = "";

        Cursor c = DB.rawQuery("SELECT Registracija FROM Parking where username=?", new String[]{username});
        if (c.moveToLast()){
                Registracija = c.getString(0);
                return Registracija;
        }
        c.close();
        DB.close();

        return Registracija;
    }



    public String ucitaj_zonu(String username) {

        SQLiteDatabase DB = this.getWritableDatabase();
        String Zona = "";

        Cursor c = DB.rawQuery("SELECT Zona FROM Parking where username=?", new String[]{username});
        if (c.moveToLast()){
            Zona = c.getString(0);
            return Zona;
        }
        c.close();
        DB.close();

        return Zona;
    }

    public String ucitaj_cijenu(String username) {

        SQLiteDatabase DB = this.getWritableDatabase();
        String cijena = "";

        Cursor c = DB.rawQuery("SELECT ukupna_cijena FROM Parking where username=?", new String[]{username});
        if (c.moveToLast()){
            cijena = c.getString(0);
            return cijena;
        }
        c.close();
        DB.close();

        return cijena;
    }

    public String ucitaj_datum_zavrsetka(String username) {

        SQLiteDatabase DB = this.getWritableDatabase();
        String vrijeme_zavrsetka = "";

        Cursor c = DB.rawQuery("SELECT vrijeme_zavrsetka FROM Parking where username=?", new String[]{username});
        if (c.moveToLast()){
            vrijeme_zavrsetka = c.getString(0);
            return vrijeme_zavrsetka;
        }
        c.close();
        DB.close();

        return vrijeme_zavrsetka;
    }

    public String provjeri_parking(String registracija) {

        SQLiteDatabase DB = this.getWritableDatabase();
        String vrijeme_zavrsetka = "";

        Cursor c = DB.rawQuery("SELECT vrijeme_zavrsetka FROM Parking where registracija=?", new String[]{registracija});
        if (c.moveToLast()){
            vrijeme_zavrsetka = c.getString(0);
            if(vrijeme_zavrsetka == null) return  "/";
            else
            return vrijeme_zavrsetka;
        }
        c.close();
        DB.close();

        return vrijeme_zavrsetka;
    }

    public String ucitaj_ID(String username) {

        SQLiteDatabase DB = this.getWritableDatabase();
        String ID = "";

        Cursor c = DB.rawQuery("SELECT ID  FROM Parking where username=?", new String[]{username});
        if (c.moveToLast()){
            ID = c.getString(0);
            return ID;
        }
        c.close();
        DB.close();

        return ID;
    }

    public Boolean azuriraj_parking(String ukupna_cijena_, String zavrsno_vrijeme_,String ID){
        SQLiteDatabase DB = this.getWritableDatabase();
        int azurirana_cijena= Integer.parseInt(ukupna_cijena_);
       Cursor a= DB.rawQuery("UPDATE Parking set ukupna_cijena="+azurirana_cijena+",vrijeme_zavrsetka=? where ID=?",new String[]{zavrsno_vrijeme_,ID});
        if (a.moveToLast()) {
            DB.close(); return true;
        }
        else {
            DB.close();
            return false;
        }

    }



    public Boolean sign_in_kontrolor(String username, String lozinka){

        SQLiteDatabase DB= this.getWritableDatabase();


        Cursor brojac_kontrolor = DB.rawQuery("SELECT * FROM User where username=? and lozinka=? ",new String[]{username,lozinka});

        if(brojac_kontrolor.getCount()>0) {


            return true;
        }
        else  return false;
    }
}

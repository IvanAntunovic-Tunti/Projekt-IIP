package com.example.parkit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Integer.parseInt;

public class Azuriraj_Parking extends AppCompatActivity {
    String username;
    DbHelper DB;
    EditText Broj_sati,Cijena,Registracija,satiTxt;
    Button Azuriraj;
    String registracija,cijena;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd:HH:mm");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username= getIntent().getStringExtra("username");
        setContentView(R.layout.activity_azuriraj__parking);
        try {
            provjeri_datum();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Registracija=(EditText) findViewById(R.id.RegistracijaTxT);
        Cijena=(EditText) findViewById(R.id.cijena_);
        satiTxt = (EditText) findViewById(R.id.satiTXT);
        DB = new DbHelper(this);
        registracija = DB.ucitaj_registracija(username);
        cijena = DB.ucitaj_cijenu(username);
        Registracija.setText(registracija);
        Cijena.setText(cijena);
        Azuriraj=(Button)findViewById(R.id.Azuriraj);

    }


    public void Azuriraj_parking(View view) throws ParseException {
        DB = new DbHelper(this);
        preracunaj_nadodanu(this.getCurrentFocus());
        int sati= parseInt(String.valueOf(satiTxt.getText()));
        Date nadodaj = provjeri_datum();
        Calendar novi_zavrsni = Calendar.getInstance();;
        novi_zavrsni.setTime(nadodaj);
        novi_zavrsni.add(Calendar.HOUR_OF_DAY, sati);
        String ID= DB.ucitaj_ID(username);
        Date zavrsni=novi_zavrsni.getTime();
        String zavrsni_datum_String= sdf.format(zavrsni);
        DB.azuriraj_parking(Cijena.getText().toString(),zavrsni_datum_String,ID);
    }

    public void preracunaj_nadodanu(View view) {

        Broj_sati=(EditText)findViewById(R.id.satiTXT);
        Cijena=(EditText) findViewById(R.id.cijena_);
        String Zona=DB.ucitaj_zonu(username);
        int sati=Integer.parseInt(String.valueOf(Broj_sati.getText()));
        int cijena_nadodana;
        if(Zona=="1") cijena_nadodana=5*sati;
        else if  (Zona=="2") cijena_nadodana=5*2*sati;
        else cijena_nadodana=5*3*sati;
        int cijena_promjena=Integer.parseInt(String.valueOf(cijena))+cijena_nadodana;

        Cijena.setText(String.valueOf(cijena_promjena));
        Azuriraj.setEnabled(true);

    }
    public Date provjeri_datum() throws ParseException {
        DB = new DbHelper(this);

        String datum_zavrsetka = DB.ucitaj_datum_zavrsetka(username);
        String currentDateandTime = sdf.format(new Date());
        Date zavrsno= sdf.parse(datum_zavrsetka);
        Date pocetno= sdf.parse(currentDateandTime);
        if (zavrsno.before(pocetno))
            {
                Toast.makeText(Azuriraj_Parking.this, "Parking vam je već istekao", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), IzbornikKorisnik.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }

        return zavrsno;
    }

    public void Nazad_izbornik_korisnik(View view) {
        Intent intent = new Intent(getBaseContext(), IzbornikKorisnik.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
}

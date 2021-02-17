package com.example.parkit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Integer.parseInt;

public class Napravi_kartu extends AppCompatActivity {
    String username;
    private String array_spinner[];
    EditText broj_sati,cijena;
    RadioButton prvi,drugi,treci;
    float cijena_;
    int sati;
    EditText Registracija;
    DbHelper DB;
    Date datum_konacni;
    String currentDateandTime;
    String zavrsni_String;
    String Zona;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username= getIntent().getStringExtra("username");
        setContentView(R.layout.activity_napravi_kartu);
        String registracija = getIntent().getStringExtra("registracija");
        Registracija=(EditText) findViewById(R.id.registracija);
        Registracija.setText(registracija);
        prvi=(RadioButton)findViewById(R.id.prva);
        drugi=(RadioButton)findViewById(R.id.druga);
        treci=(RadioButton)findViewById(R.id.treca);

    }

    public void Uslikaj_regu(View view) {

        Intent intent = new Intent(getBaseContext(), Uslikaj_reg.class);
        intent.putExtra("username", username.toString());
        startActivity(intent);

    }

    public void izracunaj_vrijeme(){

        broj_sati=(EditText)findViewById(R.id.broj);
        sati= parseInt(String.valueOf(broj_sati.getText())); // convert sate
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd:HH:mm");
        currentDateandTime = sdf.format(new Date());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, sati);
        Date zavrsni=new Date();
        zavrsni = cal.getTime();
         zavrsni_String= sdf.format(zavrsni);
    }

    public void Spremi(View view) {

        DB = new DbHelper(this);

       izracunaj_vrijeme();

        Boolean spremi = DB.insert_kartu(
                username,
                Registracija.getText().toString(),
                sati,
                cijena_,
                currentDateandTime,
                zavrsni_String,
                Zona);

        if(spremi==true) {
            Toast.makeText(Napravi_kartu.this, "Uspjesno ste se napravili kartu", Toast.LENGTH_SHORT).show();
        }
        else  Toast.makeText(Napravi_kartu.this, "Greska ", Toast.LENGTH_SHORT).show();
    }

    public void preracunaj(View view) {
        broj_sati=(EditText)findViewById(R.id.broj);
        sati= parseInt(String.valueOf(broj_sati.getText()));
        if(prvi.isChecked() == true) {cijena_=5*sati; Zona="1";}
        else if  (drugi.isChecked() == true) {cijena_=5*2*sati;Zona ="2";}
        else {cijena_=5*3*sati; Zona="3";}
        cijena=findViewById(R.id.cijena);
        String cijena_txt= String.valueOf(cijena_);
        cijena.setText(cijena_txt);
    }

}
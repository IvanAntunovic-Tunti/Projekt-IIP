package com.example.parkit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class IzbornikKorisnik extends AppCompatActivity {
    String username;
    Button Napravi_kartu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = getIntent().getStringExtra("username");
        setContentView(R.layout.activity_izbornik_korisnik);
    }


    public void napravi_kartu(View view) {

        Intent intent = new Intent(getBaseContext(), Napravi_kartu.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    public void Azuriraj_parking_form(View view) {
        Intent intent = new Intent(getBaseContext(), Azuriraj_Parking.class);
        intent.putExtra("username", username);
        startActivity(intent);


    }

    public void Izlaz(View view) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }
}
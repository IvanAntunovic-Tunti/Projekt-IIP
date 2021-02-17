package com.example.parkit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class IzbornikKorisnik extends AppCompatActivity {
    TextView Username;
    Button Napravi_kartu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String username = getIntent().getStringExtra("username");
        setContentView(R.layout.activity_izbornik_korisnik);

        Username=(TextView) findViewById(R.id.textView3);
        Username.setText(username);

        Toast.makeText(IzbornikKorisnik.this, "Di si civil,pizdo mala " , Toast.LENGTH_SHORT).show();

    }


    public void napravi_kartu(View view) {

        Intent intent = new Intent(getBaseContext(), Napravi_kartu.class);
        intent.putExtra("username", Username.getText().toString());
        startActivity(intent);
    }

    public void Azuriraj_parking_form(View view) {
        Intent intent = new Intent(getBaseContext(), Azuriraj_Parking.class);
        intent.putExtra("username", Username.getText().toString());
        startActivity(intent);


    }

    public void Izlaz(View view) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }
}
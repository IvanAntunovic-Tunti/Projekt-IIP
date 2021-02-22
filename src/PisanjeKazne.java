package com.example.parkit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PisanjeKazne extends AppCompatActivity {
    String registracija;
    EditText Registracija,Cijena;
    RadioButton prva,druga,treca;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registracija= getIntent().getStringExtra("Registracija");
        setContentView(R.layout.activity_pisanje_kazne);
        Cijena=(EditText)findViewById(R.id.cijena_kazne);
        Registracija=(EditText)findViewById(R.id.Registracijatxt);
        Registracija.setText(registracija);

    }


    public void OdaberiZonu(View view) {
        prva=(RadioButton)findViewById(R.id.Prva);
        druga=(RadioButton)findViewById(R.id.Druga);
        treca=(RadioButton)findViewById(R.id.Treca);

        if(prva.isChecked()) Cijena.setText("120");
        else if (druga.isChecked())Cijena.setText("240");
        else Cijena.setText("360");
    }


    public void IspisiKaznu(View view) {
        Toast.makeText(PisanjeKazne.this, "ISPIS KAZNE ZA  REGISTRACIJU "+registracija, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getBaseContext(), IzbornikKontrolor.class);
        startActivity(intent);
    }
}
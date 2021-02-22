package com.example.parkit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class Registracija<RealDatabase> extends AppCompatActivity {
    private EditText TXTAdresa,txtIme,TxtPrezime,TxtDatumRodenja,Username;
    private EditText Lozinka;
    private Button  Reg_save_btn;
    DbHelper DB;
    Korisnik korisnik= new Korisnik();
    String datum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registracija);

        txtIme=(EditText)findViewById(R.id.txtIme);
        TxtPrezime=(EditText)findViewById(R.id.TxtPrezime);
        TXTAdresa=(EditText)findViewById(R.id.TxTAdresa);
        Username=(EditText)findViewById(R.id.Username);
        Lozinka=(EditText)findViewById(R.id.lozinka);
        Reg_save_btn=(Button) findViewById(R.id.Reg_save_btn);


    }

    public void Spremi_Registraciju(View view) {

        DB = new DbHelper(this);
        Boolean spremi = DB.insert_korisnike(
                txtIme.getText().toString(),
                TxtPrezime.getText().toString(),
                TXTAdresa.getText().toString(),
                Username.getText().toString(),
                Lozinka.getText().toString());

        if(spremi==true) {
            Toast.makeText(Registracija.this, "Uspjesno ste se registirali " + txtIme.getText() + " !", Toast.LENGTH_SHORT).show();
        }
        else  Toast.makeText(Registracija.this, "Greska ", Toast.LENGTH_SHORT).show();
    }
}
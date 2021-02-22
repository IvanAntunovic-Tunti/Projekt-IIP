package com.example.parkit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private Button RegistracijaBtn;
    private EditText Username,Lozinka;
    DbHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Otvori_Registraciju(View view) {
        Intent intent = new Intent(this, Registracija.class);
        startActivity(intent);
    }

    public void Otvori_menu(View view){
        Username=(EditText)findViewById(R.id.Username);
        Lozinka=(EditText)findViewById(R.id.lozinka);
        int flag=0;
        DB = new DbHelper(this);
        Boolean prijava = DB.sign_in_korisnik(Username.getText().toString(),Lozinka.getText().toString());
        if(prijava==true) {
            flag=1;
            Intent intent = new Intent(getBaseContext(), IzbornikKorisnik.class);
            intent.putExtra("username", Username.getText().toString());
            startActivity(intent);
        }
        else {
            Boolean prijava_kontrolor = DB.sign_in_kontrolor(Username.getText().toString(), Lozinka.getText().toString());
            if (prijava_kontrolor == true) {
                flag = 1;
                Intent intent = new Intent(this, IzbornikKontrolor.class);
                startActivity(intent);
            }
            if (flag == 0) {
                Toast.makeText(this, "Netocno korisnicko ime ili lozinka", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
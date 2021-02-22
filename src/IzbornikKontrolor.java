package com.example.parkit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class IzbornikKontrolor extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_izbornik_kontrolor);

    }


    public void Kontrola(View view) {
        Intent intent = new Intent(this, KontrolaParkinga.class);
        startActivity(intent);

    }

    public void Natrag(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
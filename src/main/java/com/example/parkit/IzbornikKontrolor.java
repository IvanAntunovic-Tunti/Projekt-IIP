package com.example.parkit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class IzbornikKontrolor extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_izbornik_kontrolor);
        Toast.makeText(this, "Di si kontroloru, kume " , Toast.LENGTH_SHORT).show();
    }


    public void Kontrola(View view) {
        Intent intent = new Intent(this, KontrolaParkinga.class);
        startActivity(intent);

    }
}
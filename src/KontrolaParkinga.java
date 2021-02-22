package com.example.parkit;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KontrolaParkinga extends AppCompatActivity {

    SurfaceView cameraView;
    TextView Registracija,Potvrda;
    CameraSource cameraSource;
    DbHelper DB = new DbHelper(this);
    Button Kazna;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd:HH:mm");
    final int RequestCameraPermissionID = 1001;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
            break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontrola_parkinga);
        cameraView = (SurfaceView) findViewById(R.id.camera_field);
        Registracija = (TextView) findViewById(R.id.Registracija);
        Potvrda= (TextView) findViewById(R.id.potvrda);
        Kazna=(Button)findViewById(R.id.NapisiKaznubtn);
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!textRecognizer.isOperational()) {
            Log.w("Uslikaj_reg", "Ne radi");

        } else {

            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build();

            cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override

                public void surfaceCreated(SurfaceHolder surfaceHolder) {

                    try {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                            ActivityCompat.requestPermissions(KontrolaParkinga.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    RequestCameraPermissionID);
                            return;
                        }
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

                }

                @Override
                public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                    cameraSource.stop();
                }
            });

            textRecognizer.setProcessor(new Detector.Processor<TextBlock>()  {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {

                    final SparseArray<TextBlock> rega = detections.getDetectedItems();

                    if(rega.size()!=0){

                        Registracija.post(new Runnable() {
                            @Override
                            public void run() {
                                StringBuilder stringBuilder = new StringBuilder();
                                for(int i =0;i<rega.size();++i)
                                {
                                    TextBlock item = rega.valueAt(i);
                                    stringBuilder.append(item.getValue());
                                    stringBuilder.append("\n");
                                }
                                Registracija.setText(stringBuilder.toString());
                                String datum_zavrsetka = DB.provjeri_parking(Registracija.getText().toString());
                                String currentDateandTime = sdf.format(new Date());
                                Date zavrsno= null;
                                try {
                                    zavrsno = sdf.parse(datum_zavrsetka);
                                } catch (ParseException e) {
                                    Potvrda.setText(" Parking ne postoji ili je registracija nepravilno unešena");
                                    Potvrda.setTextColor(Color.RED);
                                    Kazna.setEnabled(true);
                                }
                                Date pocetno= null;
                                try {
                                    pocetno = sdf.parse(currentDateandTime);
                                } catch (ParseException e) {

                                }
                                    if (zavrsno== null)
                                        {
                                            Potvrda.setText(" Parking ne postoji");
                                            Potvrda.setTextColor(Color.RED);
                                            Kazna.setEnabled(true);
                                        }
                                    else if(zavrsno.before(pocetno)) {
                                        Potvrda.setText(" Parking je istekao");
                                        Potvrda.setTextColor(Color.RED);
                                        Kazna.setEnabled(true);
                                    }

                                    else {
                                        Potvrda.setText(" Parking je važeći");
                                        Potvrda.setTextColor(Color.GREEN);
                                        Kazna.setEnabled(false);

                                        }
                            }
                        });
                    }
                }
            });
        }
    }


    public void NapisiKaznu(View view) {

        Intent intent = new Intent(getBaseContext(), PisanjeKazne.class);
        intent.putExtra("Registracija", Registracija.getText().toString());
        startActivity(intent);
    }

    public void Nazad(View view) {
        Intent intent = new Intent( this,IzbornikKontrolor.class);
        startActivity(intent);
    }
}
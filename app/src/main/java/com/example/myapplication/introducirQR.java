package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class introducirQR extends AppCompatActivity {

    AppCompatButton btn_info;
    AppCompatButton btn_scan;
    TextView txt_entrada;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introducir_qr);
        Intent i = new Intent(this, informacion.class);

        findViewById(R.id.btn_volver2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btn_info = findViewById(R.id.btn_info);
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.putExtra("code2", txt_entrada.getText().toString());
                startActivity(i);

                startActivity(new Intent(introducirQR.this, informacion.class));
                overridePendingTransition(R.anim.slide_l1, R.anim.slide_l2);
            }
        });
        btn_scan = (AppCompatButton) findViewById(R.id.btn_scan);
        txt_entrada = (TextView) findViewById(R.id.txt_entrada);

        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrador = new IntentIntegrator(introducirQR.this);
                integrador.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrador.setPrompt("Escanee el QR");
                integrador.setCameraId(0);
                integrador.setBeepEnabled(true);
                integrador.setBarcodeImageEnabled(true);
                integrador.initiateScan();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_r1, R.anim.slide_r2);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if(result != null){
            if(result.getContents() ==null){
                Toast.makeText(this, "Escaneo cancelado", Toast.LENGTH_SHORT).show();
            }else{
             txt_entrada.setText(result.getContents());
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}
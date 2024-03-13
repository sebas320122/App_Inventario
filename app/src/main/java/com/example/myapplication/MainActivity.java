package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    AppCompatButton btn_gqr, btn_iqr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_gqr = findViewById(R.id.btn_gqr);
        btn_gqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,generarQR.class));
                overridePendingTransition(R.anim.slide_l1, R.anim.slide_l2);
            }
        });

        btn_iqr = findViewById(R.id.btn_iqr);
        btn_iqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, introducirQR.class));
                overridePendingTransition(R.anim.slide_l1, R.anim.slide_l2);
            }
        });


    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
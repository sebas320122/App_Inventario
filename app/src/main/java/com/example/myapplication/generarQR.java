package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class generarQR extends AppCompatActivity {
    public static final int CAMERA_PERMISSION_CODE = 100;
    AppCompatButton btn_guqr;
    EditText txt_codigo, txt_descripcion,txt_existencia;

    ImageView imgQR2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generarqr);
        txt_codigo = (EditText) findViewById(R.id.txt_codigo);
        txt_descripcion = (EditText) findViewById(R.id.txt_descripcion);
        txt_existencia = (EditText) findViewById(R.id.txt_existencia);


        imgQR2 = (ImageView) findViewById(R.id.imgQR2);

        Intent i = new Intent(this, guardarQR.class);
        findViewById(R.id.btn_volver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btn_guqr = findViewById(R.id.btn_save);
        btn_guqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.putExtra("code", txt_codigo.getText().toString());
                startActivity(i);

                AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(generarQR.this,"administracion",null,1);
                SQLiteDatabase myapplication = admin.getWritableDatabase();

                String codigo = txt_codigo.getText().toString();
                String descripcion = txt_descripcion.getText().toString();
                String existencia = txt_existencia.getText().toString();

                if(!codigo.isEmpty() && !descripcion.isEmpty() && !existencia.isEmpty()){
                    ContentValues registro = new ContentValues();

                    registro.put("codigo",codigo);
                    registro.put("descripcion",descripcion);
                    registro.put("existencia",existencia);

                    myapplication.insert("contenedores",null,registro);

                    myapplication.close();

                    txt_codigo.setText("");
                    txt_descripcion.setText("");
                    txt_existencia.setText("");

                    Toast.makeText(generarQR.this,"Registro exitoso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(generarQR.this, guardarQR.class));
                    overridePendingTransition(R.anim.slide_l1, R.anim.slide_l2);
                }else{
                    Toast.makeText(generarQR.this,"Debes llenar todos los campos", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_r1, R.anim.slide_r2);
    }


    //Base de datos
    public void Registrar(View view){

    }


}
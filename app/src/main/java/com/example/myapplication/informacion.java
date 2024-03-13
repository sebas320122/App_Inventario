package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.GestureDetectorCompat;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class informacion extends AppCompatActivity {

    AppCompatButton btn_update,btn_menos,btn_mas;
    EditText txt_codigo2,txt_descripcion2;
    TextView txt_existencia2;
    String codigo2;


    private GestureDetectorCompat mGestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);
        btn_update = (AppCompatButton) findViewById(R.id.btn_update);
        btn_menos = (AppCompatButton) findViewById(R.id.btn_menos);
        btn_mas = (AppCompatButton) findViewById(R.id.btn_mas);

        codigo2 = getIntent().getStringExtra("code2");

        txt_codigo2 = (EditText) findViewById(R.id.txt_codigo2);
        txt_descripcion2 = (EditText) findViewById(R.id.txt_descripcion2);
        txt_existencia2 = (TextView) findViewById(R.id.txt_existencia2);
        txt_codigo2.setText(codigo2);

        btn_menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = Integer.parseInt(txt_existencia2.getText().toString());
                num= num-1;
                txt_existencia2.setText(String.valueOf(num));
            }
        });
        btn_mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = Integer.parseInt(txt_existencia2.getText().toString());
                num= num+1;
                txt_existencia2.setText(String.valueOf(num));
            }
        });


        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase BaseDeDatabase = admin.getWritableDatabase();

        String codigo = txt_codigo2.getText().toString();


        if(!codigo.isEmpty()){
            Cursor fila = BaseDeDatabase.rawQuery
                    ("select descripcion,existencia from contenedores where codigo =" + codigo,null);
            if(fila.moveToFirst()){
                txt_descripcion2.setText(fila.getString(0));
                txt_existencia2.setText(fila.getString(1));
                BaseDeDatabase.close();
            }else{
                Toast.makeText(this,"No existe el contenedor",Toast.LENGTH_SHORT).show();
                BaseDeDatabase.close();
            }
        }else{
            onBackPressed2();
        }
        mGestureDetector = new GestureDetectorCompat(this,new GestureListener());

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(informacion.this,"administracion",null,1);
                SQLiteDatabase BasedeDatabase = admin.getWritableDatabase();

                String codigo = codigo2;
                String descripcion = txt_descripcion2.getText().toString();
                String existencia = txt_existencia2.getText().toString();

                if(!descripcion.isEmpty() && !existencia.isEmpty()){

                    ContentValues registro = new ContentValues();
                    registro.put("codigo",codigo);
                    registro.put("descripcion",descripcion);
                    registro.put("existencia",existencia);

                    int cantidad = BasedeDatabase.update("contenedores",registro,"codigo="+codigo,null);

                    BasedeDatabase.close();

                    if(cantidad == 1){
                        Toast.makeText(informacion.this,"Contenedor modificado correctamente",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(informacion.this,"El contenedor existe", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(informacion.this,"Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_r1, R.anim.slide_r2);
    }
    public void onBackPressed2() {
        super.onBackPressed();
    }
    private class GestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onDown(MotionEvent e) {
            startActivity(new Intent(informacion.this, MainActivity.class));
            overridePendingTransition(R.anim.slide_l1, R.anim.slide_l2);
            return super.onDown(e);

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

}
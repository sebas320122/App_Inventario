package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.GestureDetectorCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class guardarQR extends AppCompatActivity {

    AppCompatButton btn_save;
    String codigo;
    ImageView imgQR;
    Bitmap bitmap2;
    BitmapDrawable bitmapDrawable;
    private GestureDetectorCompat mGestureDetector2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardarqr);
        imgQR = findViewById(R.id.imgQR);
        codigo = getIntent().getStringExtra("code");
        try{
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(codigo, BarcodeFormat.QR_CODE, 750, 750);

            imgQR.setImageBitmap(bitmap);
        }catch (Exception e){
            e.printStackTrace();
            //Toast.makeText(this,"Generando QR",Toast.LENGTH_SHORT).show();
            onBackPressed2();
        }
        btn_save = (AppCompatButton) findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            bitmapDrawable = (BitmapDrawable) imgQR.getDrawable();
            bitmap2=bitmapDrawable.getBitmap();

            FileOutputStream fileOutputStream=null;

            File sdCard = Environment.getExternalStorageDirectory();
            File Directory = new File(sdCard.getAbsolutePath()+"/Download");
            Directory.mkdir();
            String filename = String.format("%d.jpg", System.currentTimeMillis());
            File outfile = new File(Directory,filename);

            Toast.makeText(guardarQR.this, "Se ha guardado el QR", Toast.LENGTH_SHORT).show();
            try{
                fileOutputStream = new FileOutputStream(outfile);
                bitmap2.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(Uri.fromFile(outfile));
                sendBroadcast(intent);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            }
        });
        mGestureDetector2 = new GestureDetectorCompat(this,new GestureListener());
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
            startActivity(new Intent(guardarQR.this, MainActivity.class));
            overridePendingTransition(R.anim.slide_l1, R.anim.slide_l2);
            return super.onDown(e);

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector2.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
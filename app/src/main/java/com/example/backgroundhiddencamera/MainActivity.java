package com.example.backgroundhiddencamera;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    File Captured_Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView=findViewById(R.id.imageView);
        //Show picture in imageView
        Show_Clicked_Picture();
        //permission's
        checkDrawOverlayPermission(MainActivity.this);
        //on button click listener
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //for overlay permission
                Toast.makeText(MainActivity.this,"Now close the app",Toast.LENGTH_LONG).show();
                if(Build.VERSION.SDK_INT >25){
                    startForegroundService(new Intent(MainActivity.this, GetBackCoreService.class));
                }else{
                    startService(new Intent(MainActivity.this, GetBackCoreService.class));
                }
            }
        });
        //Delete Image from Internal Storage
        findViewById(R.id.imageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(Environment.getExternalStorageDirectory().getPath() + "/test.jpg");
                file.delete();
                imageView.setImageResource(R.drawable.dummy);
            }
        });
    }

    public void checkDrawOverlayPermission(Context context) {
        // check if we already  have permission to draw over other apps
        if (Settings.canDrawOverlays(context)) {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA},
                        102);
            }
        } else {
            // if not construct intent to request permission
            final Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            // request permission via start activity for result
            startActivityForResult(intent, 101);
        }
    }

    public void Show_Clicked_Picture(){
        Captured_Image = new File(Environment.getExternalStorageDirectory().getPath() + "/test.jpg");
        if (Captured_Image.exists()){
            Toast.makeText(this,"h",Toast.LENGTH_LONG).show();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap bitmap = BitmapFactory.decodeFile(Captured_Image.getAbsolutePath(), options);
            imageView.setImageBitmap(bitmap);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            // if so check once again if we have permission
            if (Settings.canDrawOverlays(this)) {
                // continue here - permission was granted
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            102);
                }
            }else {
            }
        }
    }
}
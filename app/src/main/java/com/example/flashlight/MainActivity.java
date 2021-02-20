package com.example.flashlight;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageButton toggleButton;
    boolean hasCameraFlash=false;
    boolean flashOn=false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    toggleButton=findViewById(R.id.imageBtn);
    //Device support camera flash or not
    hasCameraFlash=getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    toggleButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (hasCameraFlash){
                if (flashOn){
                    flashOn=false;
                    toggleButton.setImageResource(R.drawable.off);
                    try {
                        flashLightOff();
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    flashOn=true;
                    toggleButton.setImageResource(R.drawable.on);
                    try {
                        flashLightOn();
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

            }else {
                Toast.makeText(MainActivity.this,"No flash availabale on your device",Toast.LENGTH_LONG).show();
            }
        }



        private void flashLightOn() throws CameraAccessException {
            CameraManager cameraManager= null;
            cameraManager = (CameraManager)getSystemService(Context.CAMERA_SERVICE);
            String cameraId = cameraManager.getCameraIdList()[0];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraId,true);
            }
            Toast.makeText(MainActivity.this,"FlashLight is ON",Toast.LENGTH_SHORT).show();

        }
        private void flashLightOff() throws CameraAccessException {
            CameraManager cameraManager= null;
            cameraManager = (CameraManager)getSystemService(Context.CAMERA_SERVICE);
            String cameraId = cameraManager.getCameraIdList()[0];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraId,false);
            }
            Toast.makeText(MainActivity.this,"FlashLight is OFF",Toast.LENGTH_SHORT).show();

        }
    });

    }
}
package com.example.flashlight;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flaglight.R;


public class MainActivity extends AppCompatActivity {

    private ImageButton toggleButton;
    private boolean isFlashlightOn = false;
    private CameraManager cameraManager;
    private String cameraId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggleButton = findViewById(R.id.toggleButton);

        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraId = cameraManager.getCameraIdList()[0]; // Use the first camera
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        updateButtonState();

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFlashlight();
                updateButtonState();
            }
        });
    }

    private void updateButtonState() {
        if (isFlashlightOn) {
            toggleButton.setImageResource(R.drawable.on);
            toggleButton.setContentDescription(getString(R.string.turn_off_flashlight));
        } else {
            toggleButton.setImageResource(R.drawable.off);
            toggleButton.setContentDescription(getString(R.string.turn_on_flashlight));
        }
    }

    private void toggleFlashlight() {
        try {
            if (isFlashlightOn) {
                cameraManager.setTorchMode(cameraId, false);
                isFlashlightOn = false;
            } else {
                cameraManager.setTorchMode(cameraId, true);
                isFlashlightOn = true;
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
}

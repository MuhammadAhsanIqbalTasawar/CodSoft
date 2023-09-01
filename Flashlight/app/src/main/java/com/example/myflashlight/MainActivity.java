package com.example.myflashlight;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class MainActivity extends AppCompatActivity {
    private boolean isFlashlightOn = false;
    private CameraManager cameraManager;
    private String cameraId;
    private ImageView imageView;
    private Button toggleButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        toggleButton = findViewById(R.id.toggleButton);
        AppCompatButton toggleButton = findViewById(R.id.toggleButton);

        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraId = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        updateButtonState();
            toggleButton.setTextColor(getResources().getColor(R.color.button_text_color));
        toggleButton.setOnClickListener(v -> {
            toggleFlashlight();
            updateButtonState();
            updateButtonText();
        });
    }

    private void updateButtonState() {
        if (isFlashlightOn) {
            imageView.setImageResource(R.drawable.on);
        } else {
            imageView.setImageResource(R.drawable.off);
          
        }
    }

    private void updateButtonText() {
        if (isFlashlightOn) {
            toggleButton.setText(R.string.on); // Set text to "On"
        } else {
            toggleButton.setText(R.string.off2); // Set text to "Off"
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

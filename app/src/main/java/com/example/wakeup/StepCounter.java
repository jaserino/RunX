package com.example.wakeup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StepCounter extends AppCompatActivity implements SensorEventListener {

    private TextView txt_steps;
    private SensorManager sensorManager;
    private Sensor mStepCounter;
    private boolean isCounterPresent;
    int stepCount = 0;

    Button mbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter);

        //granting permissions
        mbutton = findViewById(R.id.permission_button);
        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (getApplicationContext().checkSelfPermission(Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(StepCounter.this, "Step Counter Ready", Toast.LENGTH_SHORT).show();
                    }else{
                        requestPermissions(new String[] {Manifest.permission.ACTIVITY_RECOGNITION}, 1);
                    }

                }else{
                    Toast.makeText(StepCounter.this, "Step Counter Ready", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // making sure the screen stays lit
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        txt_steps = findViewById(R.id.txt_steps);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null)
        {
            mStepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isCounterPresent = true;
        }else {
            txt_steps.setText("ERROR: No Sensor Found. ");
            isCounterPresent = false;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor == mStepCounter){
            stepCount = (int) event.values[0];
            txt_steps.setText(String.valueOf(stepCount));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null)
            sensorManager.registerListener( this, mStepCounter, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null)
            sensorManager.unregisterListener( this, mStepCounter);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(StepCounter.this, "Step Counter Ready", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(StepCounter.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
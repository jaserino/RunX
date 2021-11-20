package com.example.wakeup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

    WebView webView;
    public String fileName = "myDescription.html";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAlarm = (Button) findViewById(R.id.btnAlarm);
        btnAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                goAlarm();
            }
        });

        Button btnTimer = (Button) findViewById(R.id.btnTimer);
        btnTimer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
            goTimer();
            }
        });

        webView = (WebView) findViewById(R.id.simpleWebView);
        // displaying content in WebView from html file that stored in assets folder
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/" + fileName);
    }

    private void goAlarm() {
        Intent intent = new Intent( MainMenu.this, StepCounter.class);
        this.startActivity(intent);
    }

    private void goTimer() {
        Intent intent = new Intent(MainMenu.this, TimerActivity.class);
        this.startActivity(intent);
    }
}

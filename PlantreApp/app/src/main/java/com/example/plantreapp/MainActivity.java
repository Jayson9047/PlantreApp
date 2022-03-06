package com.example.plantreapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.plantreapp.connection.ConnBtnActivity;
import com.example.plantreapp.myPlants.MyPlantsActivity;

/*Splash Screen*/

public class MainActivity extends AppCompatActivity {
    private int SPLASH_TIME = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        // hide actionbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // run the splash screen for 'SPLASH_TIME' milliseconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, ConnBtnActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME);
    }
}
package com.example.plantreapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button ButtonConnectionPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            if(!isTimerServiceRunning()) {
                Intent timerIntent = new Intent(this, TimerService.class);
                startForegroundService(timerIntent);
            }
            CharSequence name = "waterPlantChannel";
            String description = "Channel for watering the plants";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("waterPlantChannel", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        ButtonConnectionPage = findViewById(R.id.btnConnPage);

        ButtonConnectionPage.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ConnectionActivity.class);
            startActivity(intent);
        });
    }

    public Boolean isTimerServiceRunning(){
        @SuppressLint("ServiceCast") ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service: activityManager.getRunningServices(Integer.MAX_VALUE)){
            if(TimerService.class.getName().equals(service.service.getClassName())){
                return true;
            }
        }
        return false;
    }
}
package com.example.plantreapp;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;

import com.example.plantreapp.entities.Timer;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        // TODO Auto-generated method stub
        int channelID = 100;
        Bundle bundle = intent.getExtras();

        //change this to the actual plant name
        String contentText = bundle.getString("PlantName");

        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //add a way to open the plant page on click
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(context, "waterPlantChannel")
                .setSmallIcon(R.drawable.water_plant_icon)
                .setContentTitle("Watering")
                .setContentText(contentText)
                .setWhen(when)
                .setContentIntent(pendingIntent);
        notificationManager.notify(channelID, notifyBuilder.build());
    }
}

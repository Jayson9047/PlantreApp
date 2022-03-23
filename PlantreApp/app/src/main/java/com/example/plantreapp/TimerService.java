package com.example.plantreapp;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.plantreapp.entities.Plant;
import com.example.plantreapp.entities.Timer;
import com.example.plantreapp.myPlants.MyPlantsActivity;
import com.example.plantreapp.repository.PlantRepository;
import com.example.plantreapp.repository.TimerRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;

public class TimerService extends Service {
    public static final long HOUR = 3600*1000; // in milli-seconds.

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Get Data
        TimerRepository repoTimer = new TimerRepository(this);
        PlantRepository repoPlant = new PlantRepository(this);
        Timer[] timer = new Timer[1000];
        int[] plantUIDS = new int[1000];

        for (int x = 0; x < 1000; x++) {
            int finalX = x;
            repoPlant.findById(x, new Continuation<List<? extends Plant>>() {
                @NonNull
                @Override
                public CoroutineContext getContext() {
                    return EmptyCoroutineContext.INSTANCE;
                }

                @Override
                public void resumeWith(@NonNull Object o) {
                    List<Plant> plants = (List<Plant>) o;
                    for(Plant plant : plants) {
                        timer[finalX] = new Timer(null, Objects.requireNonNull(plant.getName()), plant.getUid(), plant.getStage(), plant.getSeedling_water_rate(), plant.getSeed_water_rate(), plant.getMature_water_rate(), new Date().toString(), new Date().toString(), new Date().toString());
                        if (plant.getUid() == timer[finalX].getPlantUID()) {
                            for (int j = 0; j < timer.length; j++) {
                                for (int k = j + 1; k < timer.length; k++) {
                                    if(timer[k] != null && timer[j] != null) {
                                        if (k != j && timer[k].getName().compareTo(timer[j].getName()) == 0) {
                                            timer[j] = null;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            });
        }

        for(int i = 0; i < 1000; i++) {
            if(timer[i] != null) {
                repoTimer.insert(timer[i], new Continuation<Unit>() {
                    @NonNull
                    @Override
                    public CoroutineContext getContext() {
                        return EmptyCoroutineContext.INSTANCE;
                    }

                    @Override
                    public void resumeWith(@NonNull Object o) {}
                });
                plantUIDS[i] = timer[i].getPlantUID();
            }
        }

        new Thread(
                () -> {
                    while(true){
                            // Getting All Timers on a single Plant - could be changed to log, journal, etc
                        for (int plantUID : plantUIDS) {
                            if (plantUID != 0) {
                                repoTimer.findByPlantUID(plantUID, new Continuation<List<? extends Timer>>() {
                                    @NonNull
                                    @Override
                                    public CoroutineContext getContext() {
                                        return EmptyCoroutineContext.INSTANCE;
                                    }

                                    @Override
                                    public void resumeWith(@NonNull Object o) {
                                        //Cast Object to value returned by continuation
                                        List<Timer> timers = (List<Timer>) o;
                                        for (Timer timer : timers) {
                                            //set the date format
                                            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");

                                            try {
                                                // Set the strings in a data object
                                                Date lastNotifyDate = formatter.parse(timer.getLastNotified());
                                                Date currentTimeDate = formatter.parse(timer.getCurrentTime());

                                                float waterRateStage;
                                                if (Objects.requireNonNull(timer.getStage()).compareTo("Seedling") == 0) {
                                                    waterRateStage = timer.getSeedling_water_rate();
                                                } else if (timer.getStage().compareTo("Seed") == 0) {
                                                    waterRateStage = timer.getSeed_water_rate();
                                                } else if (timer.getStage().compareTo("Mature") == 0) {
                                                    waterRateStage = timer.getMature_water_rate();
                                                } else {
                                                    waterRateStage = 1000f;
                                                }

                                                int waterRate = Math.round(waterRateStage * 1000);

                                                // add the watering rate, will be the actual plant one later
                                                assert lastNotifyDate != null;
                                                Date lastNotifyPlusRate = new Date(lastNotifyDate.getTime() + waterRate);

                                                // compare and see if it's ready to plant
                                                assert currentTimeDate != null;
                                                if (currentTimeDate.compareTo(lastNotifyPlusRate) >= 0) {
                                                    //Update the time because we did a notification
                                                    Timer tempTimer = new Timer(timer.getUid(), timer.getName(), timer.getPlantUID(), timer.getStage(), timer.getSeedling_water_rate(), timer.getSeed_water_rate(), timer.getMature_water_rate(), new Date().toString(), new Date().toString(), timer.getDateCreated());
                                                    repoTimer.update(tempTimer, new Continuation<Unit>() {
                                                        @NonNull
                                                        @Override
                                                        public CoroutineContext getContext() {
                                                            return EmptyCoroutineContext.INSTANCE; // Important to set - null crashes application
                                                        }

                                                        @Override
                                                        public void resumeWith(@NonNull Object o) {
                                                            Intent openPlantPage = new Intent(getApplicationContext(), MyPlantsActivity.class);
                                                            PendingIntent resultOpenPlantPage = PendingIntent.getActivity(getApplicationContext(), 1, openPlantPage, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

                                                            NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(getApplicationContext(), "waterPlantChannel")
                                                                    .setSmallIcon(R.drawable.water_plant_icon)
                                                                    .setContentTitle("Watering:")
                                                                    .setContentText(timer.getName())
                                                                    .setAutoCancel(true)
                                                                    .setContentIntent(resultOpenPlantPage);

                                                            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                                            int id = (int) System.currentTimeMillis();
                                                            notificationManager.notify(id, notifyBuilder.build());

                                                            /*





                                                            Put the water pump activation here








                                                            */

                                                        }
                                                    });
                                                } else {
                                                    //Update the time because we didn't send a notification
                                                    Timer tempTimer = new Timer(timer.getUid(), timer.getName(), timer.getPlantUID(), timer.getStage(), timer.getSeedling_water_rate(), timer.getSeed_water_rate(), timer.getMature_water_rate(), timer.getLastNotified(), new Date().toString(), timer.getDateCreated());
                                                    repoTimer.update(tempTimer, new Continuation<Unit>() {
                                                        @NonNull
                                                        @Override
                                                        public CoroutineContext getContext() {
                                                            return EmptyCoroutineContext.INSTANCE; // Important to set - null crashes application
                                                        }

                                                        @Override
                                                        public void resumeWith(@NonNull Object o) {
                                                        }
                                                    });
                                                }
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                });
                            }
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();

        //Create the timer service so it can keep running
        final String CHANNELID = "Timer Service";
        NotificationChannel channel;
        channel = new NotificationChannel(
                CHANNELID,
                CHANNELID,
                NotificationManager.IMPORTANCE_LOW
        );

        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        Notification.Builder notification = new Notification.Builder(this, CHANNELID)
                .setContentText("Timer Is Running")
                .setContentTitle("Timer Service")
                .setSmallIcon(R.drawable.ic_launcher_background);

        startForeground(1111, notification.build());
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

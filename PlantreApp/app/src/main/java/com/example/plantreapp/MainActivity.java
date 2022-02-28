package com.example.plantreapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.widget.Button;

import com.example.plantreapp.entities.Plant;
import com.example.plantreapp.entities.Timer;
import com.example.plantreapp.repository.PlantRepository;
import com.example.plantreapp.repository.TimerRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;

public class MainActivity extends AppCompatActivity {

    Button ButtonConnectionPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "waterPlantChannel";
            String description = "Channel for watering the plants";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("waterPlantChannel", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        // Get Data
        PlantRepository repoPlant = new PlantRepository(this);
        TimerRepository repoTimer = new TimerRepository(this);

        // Test Plant
        Plant testPlant = new Plant(null, "test", "test", null, "test description", "seed", 3, 3, 4, 4,5,6, 3,3,3);

        // Insert Plant - Insert a timer after the plant has been added.
        repoPlant.insert(testPlant, new Continuation<Unit>() {
            @NonNull
            @Override
            public CoroutineContext getContext() {
                return EmptyCoroutineContext.INSTANCE;
            }

            // No Return value is give by the db function - may need to change
            @Override
            public void resumeWith(@NonNull Object o) { // For the sake of testing add the timer after the async update.
                //Insert Test Timer on Plant
                Timer timer = new Timer(null, "", 1, (float) 10.9, new Date().toString(), new Date().toString());

                repoTimer.insert(timer, new Continuation<Unit>() {
                    @NonNull
                    @Override
                    public CoroutineContext getContext() {
                        return EmptyCoroutineContext.INSTANCE;
                    }

                    @Override
                    public void resumeWith(@NonNull Object o) {
                        //Important to watch which functions are called. Some repo functions return
                        // nothing... but you can still do stuff after a call has been completed
                    }
                });

            }
        });




        // Getting All Timers on a single Plant - could be changed to log, journal, etc.
        repoTimer.findByPlantUID(1, new Continuation<List<? extends Timer>>() {
            @NonNull
            @Override
            public CoroutineContext getContext() {
                return EmptyCoroutineContext.INSTANCE;
            }

            @Override
            public void resumeWith(@NonNull Object o) {
                //Cast Object to value returned by continuation
                List<Timer> timers = (List<Timer>) o;

                for (Timer timer: timers) {
                    //Do some sort of notification check for each timer
                    // send notification if check passes
                    System.out.println(timer.getLastNotified());

                    //Update the time because we did a notification
                    Timer tempTimer = new Timer(timer.getUid(), timer.getName(), timer.getPlantUID(), timer.getWaterRate() , new Date().toString(), timer.getDateCreated());
                    repoTimer.update(tempTimer, new Continuation<Unit>() {
                        @NonNull
                        @Override
                        public CoroutineContext getContext() {
                            return EmptyCoroutineContext.INSTANCE; // Important to set - null crashes application
                        }

                        @Override
                        public void resumeWith(@NonNull Object o) {
                            System.out.println("Update timer");
                        }
                    });
                }
            }
        });


        Calendar calendar = Calendar.getInstance();
        Intent intent1 = new Intent(this, AlarmReceiver.class);
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager)this.getSystemService(ALARM_SERVICE);
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 2000, pendingIntent);

        ButtonConnectionPage = findViewById(R.id.btnConnPage);

        ButtonConnectionPage.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ConnectionActivity.class);
            startActivity(intent);
        });
    }
}
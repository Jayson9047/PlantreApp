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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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
        final boolean[] sendNotification = {false};

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
                Timer timer = new Timer(null, "", 1, (float) 10.9, new Date().toString(), new Date().toString(), new Date().toString());

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

                    String lastNotify = timer.getLastNotified();
                    String currentTime = timer.getCurrentTime();

                    System.out.println("LAST NOTIFY: " + lastNotify);
                    System.out.println("CURRENT TIME: " + currentTime);

                    String subLastNotify = lastNotify.substring(11,19);
                    String subCurrentTime = currentTime.substring(11,19);

                    System.out.println("SUB LAST NOTIFY: " + subLastNotify);
                    System.out.println("SUB CURRENT TIME: " + subCurrentTime);

                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                    timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                    Date date1 = null;
                    try {
                        date1 = timeFormat.parse("4:00:00");
                        Date date2 = timeFormat.parse(subCurrentTime);
                        long sum = date1.getTime() + date2.getTime();

                        String date3 = timeFormat.format(new Date(sum));
                        System.out.println("The sum is "+ date3);

                        if(subLastNotify.compareTo(date3) == 1){
                            //Update the time because we did a notification
                            Timer tempTimer = new Timer(timer.getUid(), timer.getName(), timer.getPlantUID(), timer.getWaterRate(), new Date().toString(), new Date().toString(), timer.getDateCreated());
                            repoTimer.update(tempTimer, new Continuation<Unit>() {
                                @NonNull
                                @Override
                                public CoroutineContext getContext() {
                                    return EmptyCoroutineContext.INSTANCE; // Important to set - null crashes application
                                }

                                @Override
                                public void resumeWith(@NonNull Object o) {
                                    System.out.println("Notification Sent");
                                    sendNotification[0] = true;
                                }
                            });
                        }else{
                            //Update the time because we did a notification
                            Timer tempTimer = new Timer(timer.getUid(), timer.getName(), timer.getPlantUID(), timer.getWaterRate(), new Date().toString(), timer.getCurrentTime(), timer.getDateCreated());
                            repoTimer.update(tempTimer, new Continuation<Unit>() {
                                @NonNull
                                @Override
                                public CoroutineContext getContext() {
                                    return EmptyCoroutineContext.INSTANCE; // Important to set - null crashes application
                                }

                                @Override
                                public void resumeWith(@NonNull Object o) {
                                    System.out.println("Updated timer, no notify");
                                    sendNotification[0] = false;
                                }
                            });
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        if(sendNotification[0] == true){
            Calendar calendar = Calendar.getInstance();
            Intent intent1 = new Intent(this, AlarmReceiver.class);
            @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager am = (AlarmManager)this.getSystemService(ALARM_SERVICE);
            am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 2000, pendingIntent);
        }

        ButtonConnectionPage = findViewById(R.id.btnConnPage);

        ButtonConnectionPage.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ConnectionActivity.class);
            startActivity(intent);
        });
    }
}
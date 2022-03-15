package com.example.plantreapp;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.format.DateUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.util.StringUtil;

import com.example.plantreapp.entities.Timer;
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


public class TimerService extends Service {
    public static final long HOUR = 3600*1000; // in milli-seconds.

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final boolean[] sendNotification = {false};
        // Get Data
        TimerRepository repoTimer = new TimerRepository(this);

        // No Return value is give by the db function - may need to change
        // Insert Test Timer on Plant
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

        new Thread(
                () -> {
                    while(true){

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

                                // Getting rid of some of the extra stuff we don't need, some println is // out, can get rid of them later
                                String tempSubLastNotify = timer.getLastNotified().substring(4);
                                String tempSubCurrentTime = timer.getCurrentTime().substring(4);

                             //   System.out.println("TEMP SUB LAST NOTIFY: " + tempSubLastNotify);
                             //   System.out.println("TEMP SUB CURRENT TIME: " + tempSubCurrentTime);

                                String subLastNotify = tempSubLastNotify.substring(0, tempSubLastNotify.length() - 9);
                                String subCurrentTime = tempSubCurrentTime.substring(0, tempSubCurrentTime.length() - 9);

                             //   System.out.println("SUB LAST NOTIFY: " + subLastNotify);
                             //   System.out.println("SUB CURRENT TIME: " + subCurrentTime);

                                //set the date format
                                SimpleDateFormat formatter = new SimpleDateFormat("MMM dd HH:mm:ss");

                                try {
                                    // Set the strings in a data object
                                    Date lastNotifyDate = formatter.parse(subLastNotify);
                                    Date currentTimeDate = formatter.parse(subCurrentTime);

                                  //  System.out.println("LAST DATE: " + lastNotifyDate);
                                   // System.out.println("CURRENT DATE: " + currentTimeDate);

                                    // add the watering rate, will be the actual plant one later
                                    Date lastNotifyPlusRate = new Date(lastNotifyDate.getTime() + 10000);
                                  //  System.out.println("SUM: " + lastNotifyPlusRate);

                                    // compare and see if it's ready to plant
                                    if(currentTimeDate.compareTo(lastNotifyPlusRate) == 1){
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
                                               // System.out.println("Notification Sent");
                                                sendNotification[0] = true;
                                            }
                                        });
                                    }else{
                                        //Update the time because we didn't send a notification
                                        Timer tempTimer = new Timer(timer.getUid(), timer.getName(), timer.getPlantUID(), timer.getWaterRate(), timer.getLastNotified(), new Date().toString(), timer.getDateCreated());
                                        repoTimer.update(tempTimer, new Continuation<Unit>() {
                                            @NonNull
                                            @Override
                                            public CoroutineContext getContext() {
                                                return EmptyCoroutineContext.INSTANCE; // Important to set - null crashes application
                                            }

                                            @Override
                                            public void resumeWith(@NonNull Object o) {
                                                //System.out.println("Updated timer, no notify");
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

                    //sends the actual notification with the eventual actual plant name and ability to click on it
                        if(sendNotification[0]){
                            Calendar calendar = Calendar.getInstance();
                            Intent intent1 = new Intent(this, AlarmReceiver.class);
                            @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                            AlarmManager am = (AlarmManager)this.getSystemService(ALARM_SERVICE);
                            am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1, pendingIntent);
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
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
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
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

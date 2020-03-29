package com.example.dbmarch11;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Timer;
import java.util.TimerTask;

public class Notifications extends Service {

    public static final String CHANNEL_ID = "Channel_ID";
    private Timer timer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //FUNCTION          : onCreate
    //PARAMETERS        : none
    //RETURNS           : void
    //DESCRIPTION       : Creates the notification channel (if needed) and launches the timer.
    //                    The timer is used to schedule random notifications every minute!
    @Override
    public void onCreate() {
        this.createNotificationChannel();
        this.startTimer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    //FUNCTION          : onDestroy
    //PARAMETERS        : none
    //RETURNS           : void
    //DESCRIPTION       : Lets the service die, stops the timer.
    @Override
    public void onDestroy() {
        this.stopTimer();
        super.onDestroy();
    }

    //FUNCTION          : startTimer
    //PARAMETERS        : none
    //RETURNS           : void
    //DESCRIPTION       : Starts the timer. The timer is used to display random notifications every
    //                      minute.
    // THIS CODE WAS STOLEN FROM IGOR -> NewsReader project.
    private void startTimer() {
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                doTheNotify();
            }
        };

        timer = new Timer(true);
        int delay = 1000 * 10;           // 10 seconds
        int interval = 1000 * 60;   // 1 minute
        timer.schedule(task, delay, interval);
    }

    //FUNCTION          : stopTimer
    //PARAMETERS        : none
    //RETURNS           : void
    //DESCRIPTION       : Stops the timer. The timer is used to display random notifications every
    //                      minute.
    // THIS CODE WAS STOLEN FROM IGOR -> NewsReader project.
    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    //FUNCTION          : doTheNotify
    //PARAMETERS        : none
    //RETURNS           : void
    //DESCRIPTION       : Produces the notification message.
    //                      The message contents are chosen randomly from a pre-defined set of strings.
    private void doTheNotify(){
        String title = "APP";
        String content = "NOTIFY";

        //Build a notification with Icon and Content
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notify_icon_foreground)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        //Use a notification manager to display the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
    }

    //FUNCTION          : createNotificationChannel
    //PARAMETERS        : none
    //RETURNS           : void
    //DESCRIPTION       : Uses some dark voodoo magic to create a notification channel.
    //                      Notification channels are only needed (and can only be created) past Android 8
    //                      to protect against this, this function checks the running ver of Android.
    //                      Notification channels are basically a tube for the notifications to go through before
    //                      being displayed. Because Android dev isn't confusing enough Google decides to add more shit like this.
    // Like much of my work this was STOLEN FROM -> https://developer.android.com/training/notify-user/build-notification
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}

package com.example.qlbdt.fOther;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class Notification extends Application {

    public static final String CHANNEL_ID = "CHANNEL_1";

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Channel service example", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);

            if (notificationManager != null){
                notificationManager.createNotificationChannel(channel);
            }

        }
    }
}

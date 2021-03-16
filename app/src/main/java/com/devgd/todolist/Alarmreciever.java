package com.devgd.todolist;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.devgd.todolist.Notificationchannel.CHANNEL_1_ID;

public class Alarmreciever extends BroadcastReceiver {


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        String task=intent.getStringExtra("task_desc");
        String date=intent.getStringExtra("task_due");
        Notification notification=new NotificationCompat.Builder(context,CHANNEL_1_ID).setContentTitle("alarm")
                .setColor(context.getResources().getColor(R.color.card_bg))
                .setSmallIcon(R.drawable.ic_baseline_star_orange)
                .setContentText(date)
                .setContentTitle(task)
                .build();

        NotificationManagerCompat managerCompat =NotificationManagerCompat.from(context);
        managerCompat.notify(1,notification);
                    }
}

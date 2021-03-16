package com.devgd.todolist;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Calendar;

public class Alarmservices {
    Context context;
    AlarmManager alarmManager;
    Alarmservices(Context context){
        this.context=context;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

    }

    public void setalarm(Calendar c,int id,String task_desc,String task_due){
        Intent alarm = new Intent(context, Alarmreciever.class);
        alarm.putExtra("task_desc",task_desc);
        alarm.putExtra("task_due",task_due);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, alarm, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

    }

    public void cancelalaram(int id){
        Toast.makeText(context, String.valueOf(id), Toast.LENGTH_SHORT).show();
        Intent alarm = new Intent(context, Alarmreciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, alarm, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.cancel(pendingIntent);



    }
}

package com.devgd.todolist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static  TaskViewModel taskViewModel;
    static List<TodoTable> widgetlist = new ArrayList<>();
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int UPDATE_NOTE_REQUEST = 2;
    int lisize=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //task recycleview
        RecyclerView recyclerView = findViewById(R.id.todolistrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final Adapter adapter = new Adapter(this);
        recyclerView.setAdapter(adapter);

        //view model live data
        taskViewModel=new ViewModelProvider(this).get(TaskViewModel.class);

        taskViewModel.getAllTasks().observe(this,new Observer<List<TodoTable>>() {
            @Override
            public void onChanged(List<TodoTable> todoTables) {
                Toast.makeText(MainActivity.this, "observer", Toast.LENGTH_SHORT).show();
                widgetlist=todoTables;
                 adapter.setTask(todoTables);
                lisize=todoTables.size();
                AppWidgetManager appWidgetManager=AppWidgetManager.getInstance(MainActivity.this);
                Context context = getApplicationContext();
                ComponentName name = new ComponentName(context, NewAppWidget.class);
                int [] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(name);
                appWidgetManager.notifyAppWidgetViewDataChanged(ids,R.id.listview);
//                NewAppWidget newAppWidget = new NewAppWidget();
//                newAppWidget.onUpdate(context,appWidgetManager,ids);



            }
        });

        adapter.onItemClicked(new Adapter.onItemClickListner() {
            @Override
            public void itemClick(TodoTable todoTable) {
                Intent updateintent = new Intent(getApplicationContext(),AddTask.class);
                updateintent.putExtra("task",todoTable.getTask());
                updateintent.putExtra("priority",todoTable.getPriority());
                updateintent.putExtra("date",todoTable.getDate());
                updateintent.putExtra("id",todoTable.getId());
                updateintent.putExtra("year",todoTable.getYear());
                updateintent.putExtra("month",todoTable.getMonth());
                updateintent.putExtra("day",todoTable.getDay());
                startActivityForResult(updateintent,UPDATE_NOTE_REQUEST);


            }
        });



    }

    static public List<TodoTable> getwidgetlist(){
        return widgetlist;

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ADD_NOTE_REQUEST && resultCode==RESULT_OK){
            String tasks=data.getStringExtra("task");
            String priority=data.getStringExtra("priority");
            String date=data.getStringExtra("date");
            int year=data.getIntExtra("year",0);
            int month = data.getIntExtra("month",0);
            int day=data.getIntExtra("day",0);

            TodoTable task = new TodoTable(tasks,priority,date,0,year,month,day);
            taskViewModel.insert(task);

            //getting id

            int id=lisize+1;

            

            //Toast.makeText(this, String.valueOf(id), Toast.LENGTH_SHORT).show();


            //alarm
            if(!data.getStringExtra("date").equals("no due date")) {

//                Intent alarm = new Intent(MainActivity.this, Alarmreciever.class);
//                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 1, alarm, 0);
//                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 1);
                calendar.set(Calendar.MINUTE, 10);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);

//
//                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                Alarmservices alarmservices = new Alarmservices(MainActivity.this);
                alarmservices.setalarm(calendar,id,tasks,date);
            }

            Log.i("add",String.valueOf(day));
        }
        else if(requestCode==UPDATE_NOTE_REQUEST && resultCode==RESULT_OK){
            String tasks=data.getStringExtra("task");
            String priority=data.getStringExtra("priority");
            String date=data.getStringExtra("date");
            int id = data.getIntExtra("id",0);
            int year=data.getIntExtra("year",0);
            int month = data.getIntExtra("month",0);
            int day=data.getIntExtra("day",0);
            TodoTable task = new TodoTable(tasks,priority,date,0,year,month,day);
            task.setId(id);
            taskViewModel.update(task);
            //set alarm
            if(!data.getStringExtra("date").equals("no due date")){
                Calendar c=Calendar.getInstance();

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 15);
                calendar.set(Calendar.MINUTE, 50);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                if(calendar.after(c)){
                    Log.i("alarmcheck","workking");
                    Alarmservices alarmservices = new Alarmservices(MainActivity.this);
                    alarmservices.cancelalaram(id);

                    alarmservices.setalarm(calendar,id,data.getStringExtra("task"),data.getStringExtra("date"));
                }


            }


          //  Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
            Log.i("update",String.valueOf(day));

        }
    }

    public void openAdd(View view){
        Intent intent = new Intent(this,AddTask.class);
        startActivityForResult(intent,ADD_NOTE_REQUEST);
    }

   static public void update(TodoTable todoTable){
        taskViewModel.update(todoTable);
    }


}
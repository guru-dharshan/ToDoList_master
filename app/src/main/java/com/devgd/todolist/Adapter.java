package com.devgd.todolist;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Adapter extends  RecyclerView.Adapter<Adapter.NoteHolder> {
    private List<TodoTable> allTask = new ArrayList<>();
    private onItemClickListner listner;
    Context context;
    public Adapter(Context context){
        this.context=context;
    }
    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.taskrecycle, parent, false);
        return new NoteHolder(itemView);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
       TodoTable todoTable = allTask.get(position);
        //Toast.makeText(context, "check", Toast.LENGTH_SHORT).show();

       holder.date.setText(todoTable.getDate());
       holder.task.setText(todoTable.getTask());
       //holder.checkBox.setChecked(false);
       String priority = allTask.get(position).getPriority();
       holder.checkBox.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(holder.checkBox.isChecked()){
                   todoTable.setCheckk(1);
                   if(!todoTable.getDate().equals("no due date")) {
                   int id = todoTable.getId();
                   Alarmservices alarmservices = new Alarmservices(context);
                  alarmservices.cancelalaram(id);
              }
              MainActivity.update(todoTable);
               }
               else {
                   todoTable.setCheckk(0);
                  MainActivity.update(todoTable);
                   if(!todoTable.getDate().equals("no due date")){
                       Calendar c=Calendar.getInstance();
                       int id = todoTable.getId();

                       Calendar calendar = Calendar.getInstance();
                       calendar.set(Calendar.HOUR_OF_DAY, 15);
                       calendar.set(Calendar.MINUTE, 50);
                       calendar.set(Calendar.SECOND, 0);
                       calendar.set(Calendar.YEAR, todoTable.getYear());
                       calendar.set(Calendar.MONTH, todoTable.getMonth());
                       calendar.set(Calendar.DAY_OF_MONTH, todoTable.getDay());
                       if(calendar.after(c)){
                           Log.i("alarmcheck","workking");
                           Alarmservices alarmservices = new Alarmservices(context);
                           alarmservices.setalarm(calendar,id,todoTable.getTask(),todoTable.getDate());
                       }


                   }

               }
           }

       });

       //<** dont ever use setoncheckedchangelistner **>

//       holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
//           Log.i("onchange","1");
//           if(isChecked) {
//               todoTable.setCheckk(1);
//              if(!todoTable.getDate().equals("no due date")) {
//                   int id = todoTable.getId();
//                  Alarmservices alarmservices = new Alarmservices(context);
//                   alarmservices.cancelalaram(id);
//               }
//               MainActivity.update(todoTable);
//
//           }
//           else if(isChecked==false) {
//               todoTable.setCheckk(0);
//               MainActivity.update(todoTable);
//
//
//           }
//
//
//       });


       switch (priority){
           case "high":
               holder.priority.setImageResource(R.drawable.ic_baseline_star_red);
               holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.lightred));
               break;
           case "medium":
               holder.priority.setImageResource(R.drawable.ic_baseline_star_orange);
               holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.lightorange));
               break;
           case "low":
               holder.priority.setImageResource(R.drawable.ic_baseline_star_24);
               holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.lightyellow));
               break;
           default:
               Log.i("tag","priority error");

       }
        if(todoTable.getCheckk()==1){
            holder.checkBox.setChecked(true);
            holder.priority.setImageResource(R.drawable.ic_baseline_star_completed);
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.completed));
        }
        else {
            holder.checkBox.setChecked(false);
        }

    }

    public void setTask(List<TodoTable> allTask) {
        this.allTask=allTask;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return allTask.size();
    }

    class NoteHolder extends RecyclerView.ViewHolder {
       private ImageView priority;
       private TextView task;
       private TextView date;
       private CheckBox checkBox;
       private CardView cardView;
        public NoteHolder(View itemView) {
            super(itemView);
            priority=itemView.findViewById(R.id.starrecycle);
            task=itemView.findViewById(R.id.taskrecycle);
            date=itemView.findViewById(R.id.daterecycle);
            checkBox=itemView.findViewById(R.id.checkrecyele);
            cardView=itemView.findViewById(R.id.cardrecycle);

          //  Toast.makeText(context, "holder", Toast.LENGTH_SHORT).show();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int p = getAdapterPosition();
                    TodoTable task = allTask.get(p);
                    listner.itemClick(task);

                }
            });
        }
    }

    public interface onItemClickListner{
        public void itemClick(TodoTable todoTable);
    }

    public void onItemClicked(onItemClickListner listner){
        this.listner=listner;

    }
}
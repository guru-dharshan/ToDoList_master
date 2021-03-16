package com.devgd.todolist;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TodoTable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String task;
    private  String priority;
    private String date;
    private int checkk;
    private int year,month,day;

    public TodoTable(String task, String priority, String date,int checkk,int year, int month, int day) {
        this.task = task;
        this.priority = priority;
        this.date = date;
        this.checkk=checkk;
        this.year=year;
        this.month=month;
        this.day=day;
    }

    public int getId() {
        return id;
    }

    public String getTask() {
        return task;
    }

    public String getPriority() {
        return priority;
    }

    public String getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCheckk() {
        return checkk;
    }

    public void setCheckk(int checkk) {
        this.checkk = checkk;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}

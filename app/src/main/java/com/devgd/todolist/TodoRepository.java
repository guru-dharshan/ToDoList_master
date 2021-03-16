package com.devgd.todolist;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

public class TodoRepository {
    private static TaskDao taskDao;
    private LiveData<List<TodoTable>> allTask;
    List<TodoTable> alllist=new ArrayList<>();

    public void setlist(List<TodoTable> alllist1){

        this.alllist=alllist1;
        Log.i("setSize", String.valueOf(alllist.size()));
    }

//    public List<TodoTable> getlist(){
//        Log.i("getlist", String.valueOf(alllist.size()));
//        return alllist;
//    }

    public TodoRepository(Application application){
        TodoRoom todoRoom = TodoRoom.getInstance(application);
        taskDao=todoRoom.taskDao();
        allTask=taskDao.getAllTasks();
    }

    public void insert(TodoTable todoTable){
        new insertAsync().execute(todoTable);
    }
    public void update(TodoTable todoTable){
        new updateAsync(taskDao).execute(todoTable);
    }
    public void delete(TodoTable todoTable){
        new deletetAsync(taskDao).execute(todoTable);
    }
    public List<TodoTable> getAlllist(){
        new idAsync(this).execute();

       Log.i("return", "yess"+alllist.size());
       return alllist;
    }
    public LiveData<List<TodoTable>> getAllTask() {
        return allTask;
    }


    public static  class insertAsync extends AsyncTask<TodoTable,Void,Void>{


        @Override
        protected Void doInBackground(TodoTable... todoTables) {
            taskDao.insert(todoTables[0]);
            return null;
        }
    }
    public static  class idAsync extends AsyncTask<Void, Void, List<TodoTable>> {
        TodoRepository activity;
        List<TodoTable> alllistasync=new ArrayList<>();
        public idAsync(TodoRepository activity){
            this.activity=activity;
        }
        @Override
        protected List<TodoTable> doInBackground(Void... voids) {
            Log.i("doinbg","yess");
            alllistasync=taskDao.getAlllist();
           Log.i("listsize", String.valueOf(alllistasync.size()));
           activity.setlist(alllistasync);
            return alllistasync;
        }

        @Override
        protected void onPostExecute(List<TodoTable> todoTables) {
            super.onPostExecute(todoTables);
            //Log.i("onpost",String.valueOf(todoTables.size()));
           // activity.setlist(todoTables);


        }
    }
    public static  class deletetAsync extends AsyncTask<TodoTable,Void,Void>{
        private TaskDao taskDao;
        public deletetAsync(TaskDao taskDao){
            this.taskDao=taskDao;
        }

        @Override
        protected Void doInBackground(TodoTable... todoTables) {
            taskDao.delete(todoTables[0]);
            return null;
        }
    }

    public static  class updateAsync extends AsyncTask<TodoTable,Void,Void>{
        private TaskDao taskDao;
        public updateAsync(TaskDao taskDao){
            this.taskDao=taskDao;
        }

        @Override
        protected Void doInBackground(TodoTable... todoTables) {
            taskDao.update(todoTables[0]);
            return null;
        }
    }

}

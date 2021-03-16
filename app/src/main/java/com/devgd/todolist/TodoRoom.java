package com.devgd.todolist;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.PrimaryKey;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = TodoTable.class,version = 1)
public abstract class TodoRoom extends RoomDatabase {
    private static TodoRoom instance;
    public abstract TaskDao taskDao();


    public static synchronized TodoRoom getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),TodoRoom.class,"TodoRoom")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomcallback)
                    .build();
        }
        return instance;
    }


    private static RoomDatabase.Callback roomcallback=new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new AddAsync(instance).execute();
        }
    };

    private static class AddAsync extends AsyncTask<Void,Void,Void>{
        TaskDao taskDao;
        public AddAsync(TodoRoom db)
                {
          taskDao=db.taskDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {

            taskDao.insert(new TodoTable("default","high","23/2/2323",0,0,0,0));

            return null;
        }
    }

}

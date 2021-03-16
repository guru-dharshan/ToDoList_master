package com.devgd.todolist;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private LiveData<List<TodoTable>> allTasks;
    private TodoRepository todoRepository;
    private List<TodoTable> allList = new ArrayList<>();

    public TaskViewModel(@NonNull Application application) {
        super(application);
        todoRepository=new TodoRepository(application);
        allTasks=todoRepository.getAllTask();


    }

    public void insert(TodoTable todoTable){
        todoRepository.insert(todoTable);
    }
    public void update(TodoTable todoTable){
        todoRepository.update(todoTable);
    }
    public void delete(TodoTable todoTable){
        todoRepository.delete(todoTable);
    }


    public LiveData<List<TodoTable>> getAllTasks(){
        return allTasks;
    }
    public List<TodoTable> getAllList(){
        allList=todoRepository.getAlllist();
        Log.i("viewmodel", String.valueOf(allList.size()));
        return allList;
    }

}

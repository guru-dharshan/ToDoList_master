package com.devgd.todolist;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insert(TodoTable todoTable);

    @Delete
    void delete(TodoTable todoTable);

    @Update
    void update(TodoTable todoTable);

    @Query("SELECT * FROM TodoTable ORDER BY checkk")
    LiveData<List<TodoTable>> getAllTasks();

    @Query("SELECT * FROM TodoTable")
    List<TodoTable> getAlllist();


}

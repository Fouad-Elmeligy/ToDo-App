package com.example.todoapp.DataBaseTasks.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.DataBaseTasks.TasksDM.TaskDM
import java.time.LocalDate
import java.util.Date

@Dao
interface TaskDao {
    @Insert
    fun TaskInsert(task: TaskDM)

    @Update
    fun TaskUpdate(task: TaskDM)

    @Delete
    fun TaskDelete(task: TaskDM)
    @Delete
    fun deleteAllTasks(task: List<TaskDM>)

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): LiveData<List<TaskDM>>


    @Query("SELEct * From tasks Where date=:date")
    fun getTasksByDate(date: LocalDate?): List<TaskDM>
}
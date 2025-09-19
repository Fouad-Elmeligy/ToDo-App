package com.example.todoapp.DataBaseTasks

import androidx.room.Database
import androidx.room.Room
import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todoapp.DataBaseTasks.DAO.TaskDao
import com.example.todoapp.DataBaseTasks.TasksDM.TaskDM
import com.example.todoapp.TypeConverter.TasksTypeConverter

@TypeConverters(value = [TasksTypeConverter::class])
@Database(entities = [TaskDM::class], version = 1)
abstract class TasksDataBase: RoomDatabase (){
    abstract fun getTaskDao(): TaskDao
    companion object{
        @Volatile
       private var INSTANSE: TasksDataBase?=null
        fun getInstanse(context: Context): TasksDataBase{
            if(INSTANSE==null)
                INSTANSE= Room.databaseBuilder(context, TasksDataBase::class.java,"TaskDataBase")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration(true)
                    .build()
            return INSTANSE!!
        }
    }

}
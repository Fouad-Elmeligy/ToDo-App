package com.example.todoapp.DataBaseTasks.TasksDM

import androidx.room.*
import java.time.LocalDate
import java.util.Date

@Entity(tableName = "Tasks")
data class TaskDM(
@PrimaryKey(autoGenerate = true)
    var id:Int?=null,
    var title: String? =null,
    var description: String? =null,
    var date: LocalDate?=null,
    var isCompleted: Boolean =false,

)




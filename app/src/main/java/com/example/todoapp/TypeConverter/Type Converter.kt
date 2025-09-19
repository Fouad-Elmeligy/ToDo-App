package com.example.todoapp.TypeConverter

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
class TasksTypeConverter{
    @TypeConverter
    fun convertDateToLong(date: LocalDate): Long{
        return date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }
    @TypeConverter
    fun convertLongToDate(date: Long): LocalDate{
        return LocalDate.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault())
    }
}
package com.example.todoapp.Fragments.CallBacks

import com.example.todoapp.DataBaseTasks.TasksDM.TaskDM

interface OnTaskClickListener {
    fun onTaskClick(task: TaskDM,position: Int)
}
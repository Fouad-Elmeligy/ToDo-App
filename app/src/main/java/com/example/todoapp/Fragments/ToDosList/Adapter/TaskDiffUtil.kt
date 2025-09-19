package com.example.todoapp.Fragments.ToDosList.Adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.todoapp.DataBaseTasks.TasksDM.TaskDM

class TaskDiffUtil(private val oldList: List<TaskDM>
,private val newList: List<TaskDM>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
       return newList.size
    }

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
       return oldList[oldItemPosition].id==newList[newItemPosition].id
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldList[oldItemPosition]==newList[newItemPosition]
    }
}
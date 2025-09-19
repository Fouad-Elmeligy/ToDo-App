package com.example.todoapp.Fragments.ToDosList.Adapter

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

import com.example.todoapp.DataBaseTasks.TasksDM.TaskDM
import com.example.todoapp.DataBaseTasks.TasksDataBase
import com.example.todoapp.R
import com.example.todoapp.databinding.ItemTaskBinding

class TasksAdapter(private var tasks: List<TaskDM>) :
    RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }
    fun setNewTasksList(tasks: List<TaskDM>){
        this.tasks=tasks
        notifyDataSetChanged()
    }

    fun updateTasks(newTasks: List<TaskDM>) {
        val diffUtil = TaskDiffUtil(tasks, newTasks)
        val difResult = DiffUtil.calculateDiff(diffUtil)
        tasks = newTasks
        difResult.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(
        holder: TaskViewHolder,
        position: Int
    ) {
        val task = tasks[position]

        // Always reset the UI state first
        resetItemUI(holder)

        // Set the correct state based on task data
        updateItemState(holder, task)


        // Set click listener
        holder.binding.checkImage.setOnClickListener {
            toggleTaskCompletion(task, position)
        }

        holder.bind(task)
    }

    private fun resetItemUI(holder: TaskViewHolder) {
        // Reset to default state
        holder.binding.checkText.visibility = View.GONE
        holder.binding.taskTitle.setTextColor(
            ContextCompat.getColor(holder.itemView.context, R.color.sky_blue)
        )
        holder.binding.verticalViewLine.background = ColorDrawable(
            ContextCompat.getColor(holder.itemView.context, R.color.sky_blue)
        )
    }

    private fun updateItemState(holder: TaskViewHolder, task: TaskDM) {
        if (task.isCompleted) {
            holder.binding.checkImage.setImageDrawable(null)
            holder.binding.checkText.visibility = View.VISIBLE
            holder.binding.taskTitle.setTextColor(
                ContextCompat.getColor(holder.itemView.context, R.color.green)
            )
            holder.binding.verticalViewLine.background = ColorDrawable(
                ContextCompat.getColor(holder.itemView.context, R.color.green)
            )
        } else {
            holder.binding.checkImage.setImageResource(R.drawable.icon_check)
        }
    }

    private fun toggleTaskCompletion(task: TaskDM, position: Int) {
        task.isCompleted = true
        notifyItemChanged(position)
    }


    override fun getItemCount(): Int {
        return tasks.size
    }

    class TaskViewHolder(val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TaskDM) {
            binding.taskTitle.text = item.title
            binding.timeText.text = item.description.toString()


        }
    }
}
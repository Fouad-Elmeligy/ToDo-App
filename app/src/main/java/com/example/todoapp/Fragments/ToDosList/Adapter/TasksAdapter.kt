package com.example.todoapp.Fragments.ToDosList.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.DataBaseTasks.TasksDM.TaskDM
import com.example.todoapp.Fragments.CallBacks.OnTaskClickListener
import com.example.todoapp.R
import com.example.todoapp.databinding.ItemTaskBinding

class TasksAdapter(private var tasks: List<TaskDM>) :
    RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {
    var onDeleteClickListener: OnTaskClickListener? = null
    var onCheckClickListener: OnTaskClickListener? = null
    var onTaskItemClickListener : OnTaskClickListener?=null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    fun setNewTasksList(tasks: List<TaskDM>) {
        this.tasks = tasks
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

        holder.binding.icDelete.setOnClickListener {
            onDeleteClickListener?.onTaskClick(task, position)
        }
        holder.binding.checkImage.setOnClickListener {
            onCheckClickListener?.onTaskClick(task, position)
        }
        holder.binding.itemTask.setOnClickListener {
            onTaskItemClickListener?.onTaskClick(task,position)
        }
        holder.bind(task)
    }



    override fun getItemCount(): Int {
        return tasks.size
    }

    class TaskViewHolder(val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TaskDM) {
            binding.taskTitle.text = item.title
            binding.timeText.text = item.date.toString()
            if (item.isCompleted == true) {
                val greenColor=ResourcesCompat.getColor(
                    binding.root.resources,
                    R.color.green,
                    null
                )
                binding.verticalViewLine.setBackgroundColor(greenColor)
                binding.checkText.visibility = View.VISIBLE
                binding.checkImage.visibility=View.INVISIBLE
                binding.taskTitle.setTextColor(greenColor)

            }else{
                val skyBlueColor=ResourcesCompat.getColor(
                    binding.root.resources,
                    R.color.sky_blue,
                    null
                )
                binding.verticalViewLine.setBackgroundColor(skyBlueColor)
                binding.checkText.visibility = View.INVISIBLE
                binding.checkImage.visibility= View.VISIBLE
                binding.taskTitle.setTextColor(skyBlueColor)
            }


        }
    }
}
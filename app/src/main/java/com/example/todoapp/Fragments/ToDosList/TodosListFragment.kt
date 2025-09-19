package com.example.todoapp.Fragments.ToDosList


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.todoapp.Fragments.ToDosList.Adapter.TasksAdapter
import com.example.todoapp.Fragments.ToDosList.Adapter.WeekDayHeaderViewHolder
import com.example.todoapp.Fragments.ToDosList.Adapter.WeekDayViewHolder
import com.example.todoapp.DataBaseTasks.TasksDataBase
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentTodosListBinding
import com.example.todoapp.databinding.ItemWeekDayBinding
import com.example.todoapp.databinding.ItemWeekHeaderBinding
import com.kizitonwose.calendar.core.Week
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.view.WeekDayBinder
import com.kizitonwose.calendar.view.WeekHeaderFooterBinder
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale


class TodosListFragment : Fragment() {

    private lateinit var binding: FragmentTodosListBinding
    private var selectedDate: LocalDate? = null
    private lateinit var adapter: TasksAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodosListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TasksAdapter(listOf())
        getTasksByDate()
        binding.tasksRecyclerView.adapter = adapter

        initCalenderView()


    }


    fun initCalenderView() {

        binding.weekCalenderView.weekHeaderBinder =
            object : WeekHeaderFooterBinder<WeekDayHeaderViewHolder> {
                override fun create(view: View): WeekDayHeaderViewHolder {
                    val binding = ItemWeekHeaderBinding.bind(view)
                    return WeekDayHeaderViewHolder(binding)
                }

                override fun bind(
                    container: WeekDayHeaderViewHolder,
                    data: Week
                ) {
                    container.binding.dayOfMonthTextView.text = data.days.get(0).date.month.name
                }

            }
        binding.weekCalenderView.dayBinder = object : WeekDayBinder<WeekDayViewHolder> {
            override fun create(view: View): WeekDayViewHolder {
                val binding = ItemWeekDayBinding.bind(view)
                return WeekDayViewHolder(binding)
            }

            override fun bind(
                container: WeekDayViewHolder,
                data: WeekDay
            ) {
                val weekDayTextView = container.binding.dayOfWeekTextView
                val monthDayTextView = container.binding.dayOfMonthTextView
                weekDayTextView.text =
                    data.date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                monthDayTextView.text = "${data.date.dayOfMonth}"

                if (selectedDate?.compareTo(data.date) == 0) {
                    weekDayTextView.setTextColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.sky_blue,
                            null
                        )
                    )
                    monthDayTextView.setTextColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.sky_blue,
                            null
                        )
                    )
                } else {
                    weekDayTextView.setTextColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.item_week_day_color,
                            null
                        )
                    )
                    monthDayTextView.setTextColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.item_week_day_color,
                            null
                        )
                    )
                }
                container.binding.root.setOnClickListener {
                    val tempDate = selectedDate
                    selectedDate = data.date
                    binding.weekCalenderView.notifyDayChanged(data)
                    if (tempDate != null)
                        binding.weekCalenderView.notifyWeekChanged(tempDate)
                    getTasksByDate()
                }
            }


        }
        val currentDate = LocalDate.now()
        val currentMonth = YearMonth.now()
        val startDate = currentMonth.minusMonths(12).atStartOfMonth() // Adjust as needed
        val endDate = currentMonth.plusMonths(12).atEndOfMonth() // Adjust as needed
        val firstDayOfWeek = firstDayOfWeekFromLocale() // Available from the library
        binding.weekCalenderView.setup(startDate, endDate, firstDayOfWeek)
        binding.weekCalenderView.scrollToWeek(currentDate)
    }

    fun getAllTasks() {
        TasksDataBase.getInstanse(requireContext()).getTaskDao().getAllTasks()
            .observe(viewLifecycleOwner) {
                adapter.setNewTasksList(it)

            }
    }

    fun getTasksByDate() {
        if (selectedDate != null)
            TasksDataBase.getInstanse(requireContext()).getTaskDao().getAllTasks()
                .observe(viewLifecycleOwner) { tasks ->
                    var newtasks = tasks.filter { task ->
                        selectedDate?.year == task.date?.year
                                && selectedDate?.month == task.date?.month
                                && selectedDate?.dayOfMonth == task.date?.dayOfMonth

                    }
                    adapter.setNewTasksList(newtasks)
                }
    }
}




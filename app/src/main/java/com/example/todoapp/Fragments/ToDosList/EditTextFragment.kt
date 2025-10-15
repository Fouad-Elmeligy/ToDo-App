package com.example.todoapp.Fragments.ToDosList

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import com.example.todoapp.DataBaseTasks.TasksDM.TaskDM
import com.example.todoapp.DataBaseTasks.TasksDataBase
import com.example.todoapp.databinding.FragmentEditTaskBinding
import com.example.todoapp.utils.getFormattedDate
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Calendar
import java.util.Locale


class EditTextFragment(private var task: TaskDM, private var position: Int) :
    BottomSheetDialogFragment() {
    private lateinit var binding: FragmentEditTaskBinding
    private val DATE_FORMATTER
            : DateTimeFormatter =
        DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(Locale.US)
    var selectedDate = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindData()
        binding.saveChangesButton.setOnClickListener {
            if (isValid()) {
                task = task.copy(
                    title = binding.titleText.text.toString(),
                    description = binding.detailsText.text.toString(),
                    date = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                )
            }
            TasksDataBase.getInstanse(requireContext()).getTaskDao().TaskUpdate(task)
            dismiss()
        }
        initDate()
    }

    private fun bindData() {
        binding.titleText.setText(task.title.toString())
        binding.detailsText.setText(task.description.toString())
        binding.selectedDate.setText(task.date?.format(DATE_FORMATTER))

    }

    fun initDate() {

        selectedDate[Calendar.YEAR] = task.date?.year?.toInt()!!
        selectedDate[Calendar.MONTH] = task.date?.monthValue?.toInt()?.minus(1)!!
        selectedDate[Calendar.DAY_OF_MONTH] = task.date?.dayOfMonth?.toInt()!!
        binding.selectedDate.text = selectedDate.getFormattedDate()

        binding.selectedDate.setOnClickListener {

            val picker = DatePickerDialog(
                requireContext(),
                object : DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(
                        view: DatePicker?,
                        year: Int,
                        month: Int,
                        dayOfMonth: Int
                    ) {

                        selectedDate[Calendar.YEAR] = year
                        selectedDate[Calendar.MONTH] = month
                        selectedDate[Calendar.DAY_OF_MONTH] = dayOfMonth

                        binding.selectedDate.text = selectedDate.getFormattedDate()
                    }

                },
                task.date?.year?.toInt()!!,
                task.date?.monthValue?.toInt()?.minus(1)!!,
                task.date?.dayOfMonth?.toInt()!!
            )
            picker.show()
        }

    }

    fun isValid(): Boolean {
        var isValid = true
        if (binding.titleText.text.isEmpty() || binding.titleText.text.isBlank()) {
            binding.titleInputLayout.error = "Enter Valid Title"
            Log.e("isValid fun", "in titletext ")
            isValid = false
        } else binding.titleInputLayout.error = null
        if (binding.detailsText.text.isEmpty() || binding.detailsText.text.isBlank()) {
            binding.detailsInputLayout.error = "Enter Valid Details"
            isValid = false
        } else binding.detailsInputLayout.error = null
        return isValid
    }
}
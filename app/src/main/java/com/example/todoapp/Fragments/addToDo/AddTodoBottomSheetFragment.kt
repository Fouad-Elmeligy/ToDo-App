package com.example.todoapp.Fragments.addToDo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import com.example.todoapp.DataBaseTasks.TasksDM.TaskDM
import com.example.todoapp.DataBaseTasks.TasksDataBase
import com.example.todoapp.Fragments.CallBack.OnTaskAddedClick
import com.example.todoapp.databinding.FragmentAddTodoBinding
import com.example.todoapp.utils.getFormattedDate
import com.example.todoapp.utils.getFormattedTime
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.ZoneId
import java.util.Calendar

class AddTodoBottomSheetFragment (private val onTaskAddedClick: OnTaskAddedClick?=null): BottomSheetDialogFragment() {
    private val selectedDate= Calendar.getInstance()
    private var binding: FragmentAddTodoBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTodoBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        bindDate()
        bindTime()
    }

    private fun bindTime() {
        binding?.selectedTime?.text=selectedDate.getFormattedTime()
    }

    private fun bindDate() {
        binding?.selectedDate?.text= selectedDate.getFormattedDate()
    }

    private fun initListeners() {
        binding?.selectedDate?.setOnClickListener {
            val picker = DatePickerDialog(requireContext(),object : DatePickerDialog.OnDateSetListener{
                override fun onDateSet(
                    view: DatePicker?,
                    year: Int,
                    month: Int,
                    dayOfMonth: Int
                ) {
                    selectedDate[Calendar.YEAR]=year
                    selectedDate[Calendar.MONDAY]=month
                    selectedDate[Calendar.DAY_OF_MONTH]=dayOfMonth
                    bindDate()

                }

            },selectedDate[Calendar.YEAR],selectedDate[Calendar.MONDAY],selectedDate[Calendar.DAY_OF_MONTH])
            picker.show()
        }

        binding?.addTodo?.setOnClickListener {
            if(isValidForm()){
                TasksDataBase.getInstanse(requireContext()).getTaskDao().TaskInsert(TaskDM(
                    title = binding?.titleTextInputLayout?.editText?.text.toString(),
                    description = binding?.descriptionTextInputLayout?.editText?.text.toString(),
                    date = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                ))
                onTaskAddedClick?.onTaskAdd()


            }


        }
        binding?.selectedTime?.setOnClickListener  {
            val picker= TimePickerDialog(requireContext(),object : TimePickerDialog.OnTimeSetListener{
                override fun onTimeSet(
                    view: TimePicker?,
                    hourOfDay: Int,
                    minute: Int
                ) {
                    selectedDate[Calendar.HOUR_OF_DAY]=hourOfDay
                    selectedDate[Calendar.MINUTE]=minute
                    bindTime()
                }
            },selectedDate[Calendar.HOUR_OF_DAY],selectedDate[Calendar.MINUTE],false)
            picker.show()
        }
    }

    fun isValidForm(): Boolean {
        var isValid = true
        if (binding?.titleTextInputLayout?.editText?.text.toString().isEmpty() ||binding?.titleTextInputLayout?.editText?.text.toString().isBlank()) {
            binding?.titleTextInputLayout?.error = "Please enter valid title"
            isValid = false
        } else {
            binding?.titleTextInputLayout?.error = null
        }
        if (binding?.descriptionTextInputLayout?.editText?.text.toString().isEmpty() || binding?.descriptionTextInputLayout?.editText?.text.toString().isBlank()) {
            binding?.descriptionTextInputLayout?.error = "Please enter valid Description"
            isValid = false
        } else
            binding?.descriptionTextInputLayout?.error = null

        return isValid
    }

}
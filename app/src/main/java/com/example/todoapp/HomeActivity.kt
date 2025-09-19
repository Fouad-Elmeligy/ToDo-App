package com.example.todoapp
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.todoapp.Fragments.Settings.SettingsFragment
import com.example.todoapp.Fragments.ToDosList.TodosListFragment
import com.example.todoapp.Fragments.addToDo.AddTodoBottomSheetFragment
import com.example.todoapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()

        // إظهار Fragment الأولاني
        if (savedInstanceState == null) {
            showFragments(TodosListFragment())
        }
    }

    private fun initViews() {
        binding.bottomNav.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.navigation_tasks -> {
                    showFragments(TodosListFragment())
                }
                R.id.navigation_settings -> {
                    showFragments(SettingsFragment())
                }

            }
            return@setOnItemSelectedListener true
        }
        binding.fabBottom.setOnClickListener {
            val fragment = AddTodoBottomSheetFragment()
            fragment.show(supportFragmentManager, "AddTodoBottomSheet")
        }


        // تحديد الـ default selection
        binding.bottomNav.selectedItemId = R.id.navigation_tasks
    }

    private fun showFragments(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
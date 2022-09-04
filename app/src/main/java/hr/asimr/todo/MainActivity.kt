package hr.asimr.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialog
import hr.asimr.todo.adapters.TaskAdapter
import hr.asimr.todo.databinding.ActivityMainBinding
import hr.asimr.todo.databinding.DialogAddTaskBinding
import hr.asimr.todo.models.TaskList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var tasksAdapter: TaskAdapter
    private lateinit var taskList: TaskList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initList()
        initListeners()
        initTasksRecycler()
    }

    private fun initList() {
        taskList = TaskList(this)
    }

    private fun initTasksRecycler() {
        tasksAdapter = TaskAdapter(
            taskList,
            onClickChangeTaskDoneStatus = { task -> task.done = !task.done },
            onLongClickDelete = { task -> taskList.remove(task) }
        )
    }

    private fun initListeners() {
        initButtonListeners()
    }

    private fun initButtonListeners() {
        binding.btnAdd.setOnClickListener {
            val dialog = BottomSheetDialog(this)
            val bottomSheetBinding = DialogAddTaskBinding.inflate(layoutInflater)

            dialog.setContentView(bottomSheetBinding.root)

            bottomSheetBinding.btnAddTaskDetails.setOnClickListener {
                bottomSheetBinding.etTask.text?.clear()
            }

            dialog.show()
        }
    }
}